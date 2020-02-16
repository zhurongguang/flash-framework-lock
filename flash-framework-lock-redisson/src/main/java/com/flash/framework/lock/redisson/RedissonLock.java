package com.flash.framework.lock.redisson;

import com.flash.framework.lock.core.BaseLock;
import lombok.Data;
import org.redisson.api.RLock;

/**
 * @author zhurg
 * @date 2019/10/9 - 下午6:30
 */
@Data
public class RedissonLock extends BaseLock {

    private final RLock rLock;

    public RedissonLock(String name, RLock lock) {
        super(name);
        this.rLock = lock;
    }


}
