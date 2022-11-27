package vip.breakpoint.supplier;

import vip.breakpoint.convertor.ListTypeConvertor;
import vip.breakpoint.convertor.MapTypeConvertor;
import vip.breakpoint.convertor.ObjectTypeConvertor;
import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.enums.JavaTypeEnum;
import vip.breakpoint.exception.OptNotSupportException;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.supplier.base.PropertiesContextPool;
import vip.breakpoint.supplier.local.LocalStaticValuePool;
import vip.breakpoint.supplier.value.ValueSupplier;
import vip.breakpoint.utils.JavaTypeUtils;
import vip.breakpoint.utils.ObjectsUtils;
import vip.breakpoint.utils.TypeConvertorUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
public abstract class ValueSupplierFactory {

    private static final Logger log = WebLogFactory.getLogger(ValueSupplierFactory.class);

    /**
     * 获取值
     *
     * @param supplier 类型提供器
     * @param <T>      返回的类型
     * @param clazz    类型
     * @return 返回特定的值
     */
    public static <T, C> T get(ValueSupplier<T, C> supplier, Class<T> clazz) {
        T retValue = null;
        if (supplier.isStatic()) {
            retValue = LocalStaticValuePool.getValue(supplier);
        }
        if (null == retValue) {
            synchronized (ValueSupplierFactory.class) {
                if (supplier.isStatic()) {
                    retValue = LocalStaticValuePool.getValue(supplier);
                }
                if (null == retValue) {
                    retValue = getRealRetValue(supplier, clazz);
                    if (supplier.isStatic()) {
                        LocalStaticValuePool.addValueToLocal(supplier, retValue);
                    }
                }
            }
        }
        return retValue;
    }

    @SuppressWarnings("unchecked")
    public static <T, C> T getRealRetValue(ValueSupplier<T, C> supplier, Class<T> clazz) {
        String ret = PropertiesContextPool.getContextVal(supplier);
        try {
            if (null != clazz && JavaTypeUtils.isPrimitiveType(clazz)) {
                TypeConvertor<String, T> typeConvertor =
                        TypeConvertorUtils.getTypeConvertor(JavaTypeEnum.getByClazz(clazz));
                return ObjectsUtils.defaultIfNull(typeConvertor.doConvert(ret), supplier.getDefaultValue());
            } else {
                // mot the primitive type
                if (null == supplier.getDefaultValue()) {
                    throw new OptNotSupportException("the default value is not null for this config");
                }
                Class<?> retClazz = supplier.getDefaultValue().getClass();
                // get the real class for collection
                Class<C> innerClazz = supplier.valueClass();
                if (Map.class.isAssignableFrom(retClazz)) {
                    return ObjectsUtils.defaultIfNull((T) new MapTypeConvertor<>(innerClazz).doConvert(ret),
                            supplier.getDefaultValue());
                } else if (List.class.isAssignableFrom(retClazz)) {
                    return ObjectsUtils.defaultIfNull((T) new ListTypeConvertor<>(innerClazz).doConvert(ret),
                            supplier.getDefaultValue());
                } else {
                    return ObjectsUtils.defaultIfNull((T) new ObjectTypeConvertor<>(retClazz).doConvert(ret),
                            supplier.getDefaultValue());
                }
            }
        } catch (OptNotSupportException e2) {
            throw e2;
        } catch (Exception e) {
            log.warn("use the default value");
            return supplier.getDefaultValue();
        }
    }
}
