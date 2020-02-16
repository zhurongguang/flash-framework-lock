package com.flash.framework.lock.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhurg
 * @date 2019/9/23 - 下午3:20
 */
@Data
public class BaseLock implements Serializable {

    private static final long serialVersionUID = -4135583281732072959L;
    /**
     * 锁名称
     */
    private final String name;

    public BaseLock(String name) {
        this.name = name;
    }
}