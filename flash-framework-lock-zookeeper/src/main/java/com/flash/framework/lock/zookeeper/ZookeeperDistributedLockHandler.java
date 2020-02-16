package com.flash.framework.lock.zookeeper;

import com.flash.framework.lock.core.DistributedLockHandler;
import com.flash.framework.lock.core.RedLockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zhurg
 * @date 2019/9/23 - 下午2:30
 */
@Slf4j
public class ZookeeperDistributedLockHandler extends DistributedLockHandler<ZookeeperLock> {

    @Override
    public boolean getLock(ZookeeperLock lock, long timeout, long tryInterval, long lockExporeTime) {
        try {
            if (Objects.isNull(lock) || StringUtils.isBlank(lock.getName())) {
                throw new RedLockException("[Flash Framework] lock.name can not be null");
            }
            return lock.getLock().acquire(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("[Flash Framework] get redLock failed,cause:", e);
            return false;
        }
    }

    @Override
    public void releaseLock(ZookeeperLock lock) {
        if (Objects.nonNull(lock) && Objects.nonNull(lock.getLock())) {
            try {
                lock.getLock().release();
            } catch (Exception e) {
                log.error("[Flash Framework] ZookeeperLock {} release lock failed,cause: ", lock.getName(), e);
            }
        }
    }
}