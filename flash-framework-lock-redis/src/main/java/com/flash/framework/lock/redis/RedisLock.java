package com.flash.framework.lock.redis;

import com.flash.framework.lock.core.BaseLock;
import lombok.Data;

/**
 * @author zhurg
 * @date 2018/11/6 - 下午6:24
 */
@Data
public class RedisLock extends BaseLock {

    private static final long serialVersionUID = -4504247972794445739L;

    public RedisLock(String name) {
        super(name);
    }

    /**
     * 锁值
     */
    private Object value;
}