package com.flash.framework.lock.redis;

import com.flash.framework.lock.core.Lock;
import org.springframework.stereotype.Component;

/**
 * @author zhrug
 * @date 2019/10/10 - 下午2:07
 */
@Component
public class LockService {

    @Lock(prefix = "lock1_", name = "#id")
    public String lock1(Long id) {
        System.out.println("lock1_" + id);
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}