package vip.breakpoint.supplier;

import vip.breakpoint.function.EasySupplier;

/**
 * 值提供器
 *
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
public interface ValueSupplier<T> extends EasySupplier<T> {

    /**
     * 值的 key
     */
    String valueKey();

    /**
     * 值的类型
     */
    Class<T> valueClass();

    /**
     * namespace
     */
    String namespace();

}
