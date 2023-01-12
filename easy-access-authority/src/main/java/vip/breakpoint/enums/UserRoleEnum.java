package vip.breakpoint.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户角色
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/02
 * 欢迎关注公众号:代码废柴
 */
public enum UserRoleEnum {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(10, "超级管理员"),
    /**
     * 管理员
     */
    ADMIN(5, "管理员"),
    /**
     * 普通人
     */
    NORMAL(0, "普通人"),
    ;


    private final Integer code;
    private final String desc;

    private static final Map<Integer, UserRoleEnum> code2EnumMap = new HashMap<>();

    static {
        for (UserRoleEnum e : values()) {
            code2EnumMap.put(e.code, e);
        }
    }

    UserRoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UserRoleEnum getEnumByCode(Integer code) {
        if (null != code) {
            return code2EnumMap.getOrDefault(code, NORMAL);
        }
        return NORMAL;
    }
}
