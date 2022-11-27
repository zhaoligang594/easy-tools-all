package vip.breakpoint.config;

import vip.breakpoint.supplier.value.IntValueSupplier;

/**
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public enum IntegerConfigEnum implements IntValueSupplier {
    /**
     * 验证码缓存大小
     */
    VERIFY_CODE_CACHE_SIZE("verify.code.cache.size", 1000, "验证码缓存大小") {
        @Override
        public boolean isStatic() {
            return true;
        }
    },
    VERIFY_CODE_LENGTH("verify.code.length", 4, "验证码长度") {
        @Override
        public boolean isStatic() {
            return true;
        }
    },
    ;

    private final String valueKey;
    private final Integer defaultValue;
    private final String desc;

    IntegerConfigEnum(String valueKey, Integer defaultValue, String desc) {
        this.valueKey = valueKey;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    @Override
    public String valueKey() {
        return this.valueKey;
    }

    @Override
    public Integer getDefaultValue() {
        return this.defaultValue;
    }
}
