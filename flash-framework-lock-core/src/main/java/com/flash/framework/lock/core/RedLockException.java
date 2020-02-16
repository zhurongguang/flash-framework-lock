package com.flash.framework.lock.core;

/**
 * @author zhurg
 * @date 2018/11/7 - 上午10:49
 */
public class RedLockException extends RuntimeException {

    private static final long serialVersionUID = -2137017455846880273L;

    public RedLockException(String message) {
        super(message);
    }

    public RedLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedLockException(Throwable cause) {
        super(cause);
    }
}
