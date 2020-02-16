package com.flash.framework.lock.redisson;

import com.flash.framework.lock.core.DistributedLockHandler;
import com.flash.framework.lock.core.LockCreator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhurg
 * @date 2019/10/9 - 下午6:10
 */
@Configuration
public class RedissonLockConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(@Value("${redisson.config.path:redisson.json}") String resource) throws Exception {
        Config config = null;
        if (resource.toLowerCase().endsWith("json")) {
            config = Config.fromJSON(RedissonLockConfiguration.class.getClassLoader().getResource(resource));
        } else if (resource.toLowerCase().endsWith("yml")) {
            config = Config.fromYAML(RedissonLockConfiguration.class.getClassLoader().getResource(resource));
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean(DistributedLockHandler.class)
    public DistributedLockHandler distributedLockHandler() {
        return new RedissonDistributedLockHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LockCreator.class)
    public LockCreator lockCreator() {
        return new RedissonLockCreator();
    }
}