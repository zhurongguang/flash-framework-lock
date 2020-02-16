package com.flash.framework.lock.zookeeper;

import com.flash.framework.lock.core.LockCreator;
import com.flash.framework.zookeeper.factory.ZkClientFactory;

/**
 * @author zhurg
 * @date 2019/9/24 - 下午4:04
 */
public class ZookeeperLockCreator implements LockCreator<ZookeeperLock> {

    private final ZkClientFactory zkClientFactory;

    public ZookeeperLockCreator(ZkClientFactory zkClientFactory) {
        this.zkClientFactory = zkClientFactory;
    }

    @Override
    public ZookeeperLock createLock(String lockName, Object lockValue) {
        ZookeeperLock zookeeperLock = new ZookeeperLock(lockName);
        zookeeperLock.initLock(zkClientFactory.get());
        return zookeeperLock;
    }
}
