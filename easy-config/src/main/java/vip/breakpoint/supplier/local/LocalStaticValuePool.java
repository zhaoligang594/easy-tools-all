package vip.breakpoint.supplier.local;

import vip.breakpoint.supplier.value.ValueSupplier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储不变的量
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/07
 * 欢迎关注公众号:代码废柴
 */
public class LocalStaticValuePool {

    private static final Map<ValueSupplier<?, ?>, Object> supplier2ValueMap = new ConcurrentHashMap<>();

    public static <T, C> void addValueToLocal(ValueSupplier<T, C> supplier, Object value) {
        supplier2ValueMap.put(supplier, value);
    }

    @SuppressWarnings("unchecked")
    public static <T, C> T getValue(ValueSupplier<T, C> supplier) {
        return (T) supplier2ValueMap.get(supplier);
    }
}
