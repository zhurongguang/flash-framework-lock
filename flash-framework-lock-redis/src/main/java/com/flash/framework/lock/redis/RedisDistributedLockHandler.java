package com.flash.framework.lock.redis;

import com.alibaba.fastjson.JSON;
import com.flash.framework.lock.core.DistributedLockHandler;
import com.flash.framework.lock.core.RedLockException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Objects;

/**
 * @author zhurg
 * @date 2018/11/6 - 下午6:29
 */
@Slf4j
public class RedisDistributedLockHandler extends DistributedLockHandler<RedisLock> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 使用脚本执行redis命令，在一定程度上保证了原子性
     */
    private static final RedisScript<Boolean> SETNX_AND_EXPIRE_SCRIPT;

    private static final RedisScript<Boolean> DEL_GET_SCRIPT;

    static {
        final StringBuilder sb = new StringBuilder();
        sb.append("if (redis.pcall('setnx', KEYS[1] , ARGV[1]) == 1) then\n");
        sb.append("\tredis.pcall('expire', KEYS[1] , tonumber(ARGV[2]))\n");
        sb.append("\treturn true\n");
        sb.append("else\n");
        sb.append("\treturn false\n");
        sb.append("end");
        SETNX_AND_EXPIRE_SCRIPT = new RedisScript<Boolean>() {

            @Override
            public Class<Boolean> getResultType() {
                return Boolean.class;
            }

            @Override
            public String getScriptAsString() {
                return sb.toString();
            }

            @Override
            public String getSha1() {
                return DigestUtils.sha1DigestAsHex(sb.toString());
            }
        };
        final StringBuilder sb1 = new StringBuilder();
        sb1.append("if (redis.pcall('get', KEYS[1]) == ARGV[1]) then\n");
        sb1.append("\tredis.pcall('del', KEYS[1])\n");
        sb1.append("\treturn true\n");
        sb1.append("else\n");
        sb1.append("\treturn false\n");
        sb1.append("end");
        DEL_GET_SCRIPT = new RedisScript<Boolean>() {

            @Override
            public Class<Boolean> getResultType() {
                return Boolean.class;
            }

            @Override
            public String getScriptAsString() {
                return sb1.toString();
            }

            @Override
            public String getSha1() {
                return DigestUtils.sha1DigestAsHex(sb1.toString());
            }
        };
    }

    @Override
    public boolean getLock(RedisLock lock, long timeout, long tryInterval, long lockExporeTime) {
        try {
            if (Objects.isNull(lock) || StringUtils.isBlank(lock.getName()) || Objects.isNull(lock.getValue())) {
                throw new RedLockException("[Flash Framework] lock.name and lock.value can not be null");
            }
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime <= timeout) {
                boolean result = redisTemplate.execute(SETNX_AND_EXPIRE_SCRIPT,
                        Lists.newArrayList(lock.getName()), lock.getValue().toString(), String.valueOf(lockExporeTime / 1000));
                if (!result) {
                    Thread.sleep(tryInterval);
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("[Flash Framework] get redLock failed,cause:", e);
            return false;
        }
        return false;
    }

    @Override
    public void releaseLock(RedisLock lock) {
        if (Objects.nonNull(lock) && StringUtils.isNotBlank(lock.getName())) {
            Boolean r = redisTemplate.execute(DEL_GET_SCRIPT, Lists.newArrayList(lock.getName()),
                    lock.getValue());
            if (!r) {
                log.error("[Flash Framework] RedisLock {} release lock failed", JSON.toJSONString(lock));
            }
        }
    }
}
