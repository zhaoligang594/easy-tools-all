package vip.breakpoint.demo;

import vip.breakpoint.supplier.value.IntValueSupplier;

/**
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public enum IntConfigEnum implements IntValueSupplier {
    TEST("test", 10, "测试");

    private final String valueKey;
    private final Integer defaultValue;
    private final String desc;

    IntConfigEnum(String valueKey, Integer defaultValue, String desc) {
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
