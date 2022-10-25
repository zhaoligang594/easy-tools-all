package vip.breakpoint.demo;

import vip.breakpoint.demo.bean.TestUser;
import vip.breakpoint.supplier.value.ListValueSupplier;

import java.util.Arrays;
import java.util.List;

/**
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/24
 * 欢迎关注公众号:代码废柴
 */
public enum StringListEnum implements ListValueSupplier<TestUser> {

    TEST("test", Arrays.asList(new TestUser()), "测试");

    private final String valueKey;
    private final List<TestUser> defaultValue;
    private final String desc;

    StringListEnum(String valueKey, List<TestUser> defaultValue, String desc) {
        this.valueKey = valueKey;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }


    @Override
    public String valueKey() {
        return this.valueKey;
    }

    @Override
    public List<TestUser> getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public Class<TestUser> valueClass() {
        return TestUser.class;
    }
}
