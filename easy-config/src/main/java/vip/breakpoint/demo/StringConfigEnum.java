package vip.breakpoint.demo;

import vip.breakpoint.supplier.value.StringValueSupplier;

/**
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public enum StringConfigEnum implements StringValueSupplier {
    TEST("test", "默認值", "测试"),
    TEST_NAME("test.name", "默認值", "测试");

    private final String valueKey;
    private final String defaultValue;
    private final String desc;

    StringConfigEnum(String valueKey, String defaultValue, String desc) {
        this.valueKey = valueKey;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    @Override
    public String valueKey() {
        return this.valueKey;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
