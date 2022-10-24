package vip.breakpoint.function;

import java.util.function.Supplier;

/**
 * Supplier's extends
 *
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
@FunctionalInterface
public interface EasySupplier<T> extends Supplier<T> {

    /**
     * get value
     */
    T get();
}
