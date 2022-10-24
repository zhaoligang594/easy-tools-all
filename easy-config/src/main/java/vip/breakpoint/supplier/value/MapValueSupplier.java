package vip.breakpoint.supplier.value;

import vip.breakpoint.exception.OptNotSupportException;
import vip.breakpoint.supplier.ValueSupplierFactory;

import java.util.Map;

/**
 * Long 类型获取
 *
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public interface MapValueSupplier<T> extends ValueSupplier<Map<String, T>, T> {

    @Override
    default Class<T> valueClass() {
        throw new OptNotSupportException("this have to set somethings");
    }

    @Override
    default Map<String, T> get() {
        // 获取
        return ValueSupplierFactory.get(this, null);
    }
}
