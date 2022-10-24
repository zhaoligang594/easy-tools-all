package vip.breakpoint.supplier.value;

import vip.breakpoint.supplier.ValueSupplierFactory;

/**
 * int 类型获取
 *
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public interface IntValueSupplier extends ValueSupplier<Integer, Integer> {

    @Override
    default Class<Integer> valueClass() {
        return Integer.class;
    }

    @Override
    default Integer get() {
        // 获取
        return ValueSupplierFactory.get(this, Integer.class);
    }
}
