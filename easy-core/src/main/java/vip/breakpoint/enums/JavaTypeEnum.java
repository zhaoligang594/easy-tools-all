package vip.breakpoint.enums;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Java 类型的对照
 *
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public enum JavaTypeEnum {
    /**
     * Integer
     */
    INTEGER(Integer.class, 1),
    /**
     * Byte
     */
    BYTE(Byte.class, 2),
    /**
     * Short
     */
    SHORT(Short.class, 3),
    /**
     * Long
     */
    LONG(Long.class, 4),
    /**
     * Double
     */
    DOUBLE(Double.class, 5),
    /**
     * Float
     */
    FLOAT(Float.class, 6),
    /**
     * Boolean
     */
    BOOLEAN(Boolean.class, 7),
    /**
     * Character
     */
    CHARACTER(Character.class, 8),
    /**
     * String
     */
    STRING(String.class, 9),
    /**
     * map
     */
    MAP(Map.class, 10),
    /**
     * list
     */
    LIST(List.class, 11),
    ;

    private final Class<?> type;
    private final Integer code;

    private static final Map<Class<?>, JavaTypeEnum> map = new ConcurrentHashMap<>();

    static {
        for (JavaTypeEnum typeEnum : values()) {
            map.put(typeEnum.type, typeEnum);
        }
    }

    JavaTypeEnum(Class<?> type, Integer code) {
        this.type = type;
        this.code = code;
    }

    public Class<?> getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 获取类型
     */
    public static JavaTypeEnum getByClazz(Class<?> type) {
        return map.getOrDefault(type, STRING);
    }
}
