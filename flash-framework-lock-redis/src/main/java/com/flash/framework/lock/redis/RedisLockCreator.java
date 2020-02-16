package com.flash.framework.lock.redis;

import com.flash.framework.lock.core.LockCreator;

/**
 * @author zhurg
 * @date 2019/9/24 - 下午2:48
 */
public class RedisLockCreator implements LockCreator<RedisLock> {

    @Override
    public RedisLock createLock(String lockName, Object lockValue) {
        RedisLock redisLock = new RedisLock(lockName);
        redisLock.setValue(lockValue);
        return redisLock;
    }
}