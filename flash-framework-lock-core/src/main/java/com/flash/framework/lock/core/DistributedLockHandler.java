package com.flash.framework.lock.core;

/**
 * @author zhurg
 * @date 2018/11/6 - 下午6:26
 */
public abstract class DistributedLockHandler<L extends BaseLock> {

    /**
     * 单个业务持有锁的时间 30s
     */
    protected static final long LOCK_EXPIRE = 30 * 1000L;

    /**
     * 默认200ms尝试一次
     */
    protected static final long LOCK_TRY_INTERVAL = 200L;

    /**
     * 获取锁超时时间 默认5s
     */
    protected static final long LOCK_TRY_TIMEOUT = 5000L;

    /**
     * 尝试获取全局锁
     *
     * @param lock
     * @return true 获取成功  false 获取失败
     */
    public boolean tryLock(L lock) {
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock
     * @param lockExporeTime 锁超时时间
     * @return true 获取成功  false 获取失败
     */
    public boolean tryLock(L lock, long lockExporeTime) {
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, lockExporeTime);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock
     * @param timeout     获取锁超时时间 ms
     * @param tryInterval 多少毫秒尝试获取一次
     * @return true 获取成功  false 获取失败
     */
    public boolean tryLock(L lock, long timeout, long tryInterval) {
        return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock
     * @param timeout        获取锁超时时间 ms
     * @param tryInterval    多少毫秒尝试获取一次
     * @param lockExporeTime 锁的过期时间
     * @return true 获取成功  false 获取失败
     */
    public boolean tryLock(L lock, long timeout, long tryInterval,
                           long lockExporeTime) {
        return getLock(lock, timeout, tryInterval, lockExporeTime);
    }

    /**
     * 操作redis获取全局锁
     *
     * @param lock
     * @param timeout        获取锁超时时间 ms
     * @param tryInterval    多少毫秒尝试获取一次
     * @param lockExporeTime 锁的过期时间
     * @return true 获取成功  false 获取失败
     */
    public abstract boolean getLock(L lock, long timeout, long tryInterval,
                                    long lockExporeTime);

    /**
     * 释放锁
     *
     * @param lock
     */
    public abstract void releaseLock(L lock);
}
