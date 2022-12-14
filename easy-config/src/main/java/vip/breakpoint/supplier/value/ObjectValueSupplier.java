package vip.breakpoint.supplier.value;

import vip.breakpoint.supplier.ValueSupplierFactory;

/**
 * Long 类型获取
 *
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public interface ObjectValueSupplier<T> extends ValueSupplier<T, T> {

    @Override
    default Class<T> valueClass() {
        //throw new OptNotSupportException("this have to set somethings");
        return null;
    }

    @Override
    default T get() {
        // 获取
        return ValueSupplierFactory.get(this, valueClass());
    }
}
