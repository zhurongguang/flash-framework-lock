package com.flash.framework.lock.redisson;

import com.flash.framework.lock.core.LockCreator;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhurg
 * @date 2019/10/10 - 上午11:30
 */
public class RedissonLockCreator implements LockCreator<RedissonLock> {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public RedissonLock createLock(String lockName, Object lockValue) {
        return new RedissonLock(lockName, redissonClient.getLock(lockName));
    }
}
