package com.flash.framework.lock.zookeeper;

import com.flash.framework.lock.core.DistributedLockHandler;
import com.flash.framework.lock.core.LockCreator;
import com.flash.framework.zookeeper.factory.ZkClientFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhurg
 * @date 2019/9/24 - 下午4:07
 */
@Configuration
@ConditionalOnProperty(prefix = "zk", name = "lock.enable", havingValue = "true")
public class ZookeeperLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(DistributedLockHandler.class)
    public DistributedLockHandler distributedLockHandler() {
        return new ZookeeperDistributedLockHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LockCreator.class)
    @ConditionalOnBean(ZkClientFactory.class)
    public LockCreator lockCreator(ZkClientFactory zkClientFactory) {
        return new ZookeeperLockCreator(zkClientFactory);
    }
}