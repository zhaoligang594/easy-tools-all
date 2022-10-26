package vip.breakpoint.supplier;

import vip.breakpoint.convertor.ListTypeConvertor;
import vip.breakpoint.convertor.MapTypeConvertor;
import vip.breakpoint.convertor.ObjectTypeConvertor;
import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.enums.JavaTypeEnum;
import vip.breakpoint.exception.OptNotSupportException;
import vip.breakpoint.supplier.base.PropertiesContextPool;
import vip.breakpoint.supplier.value.ValueSupplier;
import vip.breakpoint.utils.TypeConvertorUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
public abstract class ValueSupplierFactory {

    /**
     * 获取值
     *
     * @param supplier 类型提供器
     * @param <T>      返回的类型
     * @return 返回特定的值
     */
    @SuppressWarnings("unchecked")
    public static <T, C> T get(ValueSupplier<T, C> supplier, Class<T> clazz) {
        String ret = PropertiesContextPool.getContextVal(supplier);
        try {
            if (null != clazz && isPrimitiveType(clazz)) {
                TypeConvertor<String, T> typeConvertor =
                        TypeConvertorUtils.getTypeConvertor(JavaTypeEnum.getByClazz(clazz));
                return typeConvertor.doConvert(ret);
            } else {
                // mot the primitive type
                if (null == supplier.getDefaultValue()) {
                    throw new OptNotSupportException("the default value is not null for this config");
                }
                Class<?> retClazz = supplier.getDefaultValue().getClass();
                // get the real class for collection
                Class<C> innerClazz = supplier.valueClass();
                if (Map.class.isAssignableFrom(retClazz)) {
                    return (T) new MapTypeConvertor<>(innerClazz).doConvert(ret);
                } else if (List.class.isAssignableFrom(retClazz)) {
                    return (T) new ListTypeConvertor<>(innerClazz).doConvert(ret);
                } else {
                    return (T) new ObjectTypeConvertor<>(retClazz).doConvert(ret);
                }
            }
        } catch (OptNotSupportException e2) {
            throw e2;
        } catch (Exception e) {
            return supplier.getDefaultValue();
        }
    }


    /**
     * 是否为基本的数据类型
     *
     * @param clazz 类型
     * @param <T>   类型
     * @return true or false
     */
    private static <T> boolean isPrimitiveType(Class<T> clazz) {
        return clazz == Integer.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Long.class
                || clazz == Double.class
                || clazz == Float.class
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == String.class;
    }
}
