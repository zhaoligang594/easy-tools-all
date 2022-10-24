package vip.breakpoint.supplier.value;

import vip.breakpoint.function.EasySupplier;

/**
 * 值提供器
 *
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
public interface ValueSupplier<T,C> extends EasySupplier<T> {
    /**
     * 值的 key
     */
    String valueKey();

    // 获取默认的方法
    T getDefaultValue();
    /**
     * 值的类型
     */
    Class<C> valueClass();

}
