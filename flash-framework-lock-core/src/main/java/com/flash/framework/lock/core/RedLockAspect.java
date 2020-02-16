package com.flash.framework.lock.core;

import com.flash.framework.commons.utils.AopUtils;
import com.flash.framework.commons.utils.SpelParser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zhurg
 * @date 2018/11/7 - 上午9:10
 */
@Aspect
@Component
public class RedLockAspect {

    @Autowired
    private DistributedLockHandler distributedLockHandler;

    @Autowired
    private LockCreator lockCreator;

    @Around("@annotation(com.flash.framework.lock.core.Lock)")
    public Object tryLock(ProceedingJoinPoint joinPoint) {
        Method method = AopUtils.getMethod(joinPoint);

        Lock lock = AnnotationUtils.findAnnotation(method, Lock.class);
        if (StringUtils.isBlank(lock.name())) {
            throw new RedLockException("[Flash Framework] Annotation @Lock name can not be null");
        }

        Object lockName = SpelParser.getValue(lock.name(), method, joinPoint.getArgs());
        if (Objects.isNull(lockName)) {
            throw new RedLockException("[Flash Framework] Annotation @Lock name can not be null");
        }

        Object lockValue = null;
        if (StringUtils.isNotBlank(lock.value())) {
            lockValue = SpelParser.getValue(lock.value(), method, joinPoint.getArgs());
        }

        BaseLock baseLock = lockCreator.createLock((StringUtils.isNotBlank(lock.prefix()) ? lock.prefix() : "") + lockName.toString(), Objects.isNull(lockValue) ? UUID.randomUUID().toString().replaceAll("-", "") : lockValue);

        try {
            if (distributedLockHandler.getLock(baseLock, lock.timeout(), lock.interval(), lock.expire())) {
                return joinPoint.proceed();
            }
            throw new RedLockException(baseLock.getName() + " get lock fail!");
        } catch (Throwable e) {
            throw new RedLockException(e);
        } finally {
            distributedLockHandler.releaseLock(baseLock);
        }
    }
}
