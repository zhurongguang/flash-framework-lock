package com.flash.framework.lock.redis;

import com.flash.framework.lock.core.DistributedLockHandler;
import com.flash.framework.lock.core.LockCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhurg
 * @date 2019/9/23 - 上午11:25
 */
@Configuration
public class RedisLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(DistributedLockHandler.class)
    public DistributedLockHandler redLockHandler() {
        return new RedisDistributedLockHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LockCreator.class)
    public LockCreator lockCreator() {
        return new RedisLockCreator();
    }
}