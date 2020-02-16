package com.flash.framework.lock.redisson;

import com.flash.framework.lock.core.DistributedLockHandler;
import com.flash.framework.lock.core.RedLockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redisson
 *
 * @author zhurg
 * @date 2019/10/9 - 下午6:31
 */
@Slf4j
public class RedissonDistributedLockHandler extends DistributedLockHandler<RedissonLock> {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean getLock(RedissonLock lock, long timeout, long tryInterval, long lockExporeTime) {
        if (Objects.isNull(lock) || StringUtils.isBlank(lock.getName())) {
            throw new RedLockException("[Flash Framework] lock.name can not be null");
        }
        try {
            return lock.getRLock().tryLock(timeout, lockExporeTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("[Flash Framework] RedissonLock {} lock failed,cause:", lock.getName(), e);
            return false;
        }
    }

    @Override
    public void releaseLock(RedissonLock lock) {
        if (Objects.nonNull(lock) && Objects.nonNull(lock.getRLock())) {
            try {
                lock.getRLock().unlock();
            } catch (Exception e) {
                log.error("[Flash Framework] RedissonLock {} release lock failed,cause: ", lock.getName(), e);
            }
        }
    }
}
