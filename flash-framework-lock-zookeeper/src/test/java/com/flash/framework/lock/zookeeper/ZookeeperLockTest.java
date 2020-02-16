package com.flash.framework.lock.zookeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhurg
 * @date 2019/10/10 - 下午1:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZookeeperLockApplication.class)
public class ZookeeperLockTest {

    @Autowired
    private LockService lockService;

    @Test
    public void testSimpleLock() {
        System.out.println(lockService.lock1(1L));
    }
}