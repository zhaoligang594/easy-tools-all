package vip.breakpoint.demo;

import vip.breakpoint.demo.bean.TestUser;
import vip.breakpoint.supplier.value.ObjectValueSupplier;

/**
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/24
 * 欢迎关注公众号:代码废柴
 */
public enum ObjectConfigEnum implements ObjectValueSupplier<TestUser> {

    TEST("test_json", new TestUser(), "测试");

    private final String valueKey;
    private final TestUser defaultValue;
    private final String desc;

    ObjectConfigEnum(String valueKey, TestUser defaultValue, String desc) {
        this.valueKey = valueKey;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    @Override
    public String valueKey() {
        return this.valueKey;
    }

    @Override
    public TestUser getDefaultValue() {
        return this.defaultValue;
    }
}
