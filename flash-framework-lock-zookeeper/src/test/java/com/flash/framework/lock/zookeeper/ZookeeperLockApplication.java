package com.flash.framework.lock.zookeeper;

import com.flash.framework.zookeeper.autoconfigure.ZkConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

/**
 * @author zhurg
 * @date 2019/10/10 - 下午2:53
 */
@Import(ZkConfiguration.class)
@SpringBootApplication(scanBasePackages = "com.flash.framework.lock")
public class ZookeeperLockApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZookeeperLockApplication.class).web(WebApplicationType.NONE).run(args);
    }
}