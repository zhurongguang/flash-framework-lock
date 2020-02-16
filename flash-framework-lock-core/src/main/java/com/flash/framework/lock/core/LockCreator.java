package com.flash.framework.lock.core;

/**
 * Lock 创建接口
 *
 * @author zhurg
 * @date 2019/9/24 - 下午2:44
 */
public interface LockCreator<L extends BaseLock> {

    /**
     * 创建Lock
     *
     * @param lockName
     * @param lockValue
     * @return
     */
    L createLock(String lockName, Object lockValue);
}