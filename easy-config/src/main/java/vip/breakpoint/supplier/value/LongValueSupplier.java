package vip.breakpoint.supplier.value;

import vip.breakpoint.supplier.ValueSupplierFactory;

/**
 * Long 类型获取
 *
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public interface LongValueSupplier extends ValueSupplier<Long, Long> {

    @Override
    default Class<Long> valueClass() {
        return Long.class;
    }

    @Override
    default Long get() {
        // 获取
        return ValueSupplierFactory.get(this, Long.class);
    }
}
