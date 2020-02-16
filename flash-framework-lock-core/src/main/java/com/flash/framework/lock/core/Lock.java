package com.flash.framework.lock.core;

import java.lang.annotation.*;

/**
 * @author zhurg
 * @date 2018/11/6 - 下午9:32
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    /**
     * lock name prefix
     *
     * @return
     */
    String prefix() default "";

    /**
     * lock name
     *
     * @return
     */
    String name();

    /**
     * lock value
     *
     * @return
     */
    String value() default "";

    /**
     * lock expire time
     *
     * @return
     */
    long expire() default 30000L;

    /**
     * lock interval time
     *
     * @return
     */
    long interval() default 200L;

    /**
     * lock try timeout
     *
     * @return
     */
    long timeout() default 5000L;
}
