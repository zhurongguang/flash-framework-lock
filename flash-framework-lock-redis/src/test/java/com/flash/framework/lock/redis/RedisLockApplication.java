package com.flash.framework.lock.redis;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhurg
 * @date 2019/10/10 - 下午2:05
 */
@SpringBootApplication(scanBasePackages = "com.flash.framework.lock")
public class RedisLockApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RedisLockApplication.class).web(WebApplicationType.NONE).run(args);
    }
}