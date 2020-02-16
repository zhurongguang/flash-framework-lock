package com.flash.framework.lock.redisson;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhurg
 * @date 2019/10/10 - 下午2:53
 */
@SpringBootApplication(scanBasePackages = "com.flash.framework.lock")
public class RedissonLockApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RedissonLockApplication.class).web(WebApplicationType.NONE).run(args);
    }
}