package vip.breakpoint.utils;

import vip.breakpoint.base.SnowAlgorithm;

import java.util.UUID;

/**
 * 生成ID的工具类
 */
public abstract class EasyIDUtils {

    private static final SnowAlgorithm s = new SnowAlgorithm(1L, 1L, 1L);

    // get key from snow ,type long
    public static long generateId() {
        return s.nextId();
    }

    // get key from snow
    public static String generateIdStr() {
        return String.valueOf(s.nextId());
    }

    // get key from UUID 36 bits
    public static String getUniqueID32Length() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // get key from UUID 32 bits
    public static String getUniqueID36Length() {
        return UUID.randomUUID().toString();
    }
}
