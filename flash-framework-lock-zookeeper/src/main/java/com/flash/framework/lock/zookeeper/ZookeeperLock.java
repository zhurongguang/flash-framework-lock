package com.flash.framework.lock.zookeeper;

import com.flash.framework.lock.core.BaseLock;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

/**
 * @author zhurg
 * @date 2019/9/23 - 下午3:21
 */
@Data
public class ZookeeperLock extends BaseLock {

    private static final long serialVersionUID = -5882788954224016253L;

    private InterProcessSemaphoreMutex lock;

    public ZookeeperLock(String name) {
        super(name);
    }

    public void initLock(CuratorFramework zkClient) {
        this.lock = new InterProcessSemaphoreMutex(zkClient, lockName());
    }

    protected String lockName() {
        if (getName().startsWith("/")) {
            return getName();
        } else {
            return "/" + getName();
        }
    }
}