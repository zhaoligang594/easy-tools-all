package vip.breakpoint.supplier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import vip.breakpoint.enums.JavaTypeEnum;
import vip.breakpoint.supplier.base.ContextProperties;
import vip.breakpoint.supplier.value.ValueSupplier;

import java.util.List;
import java.util.Map;

/**
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
public abstract class ValueSupplierFactory {

    /**
     * 类型转换器
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取值
     *
     * @param supplier 类型提供器
     * @param <T>      返回的类型
     * @return 返回特定的值
     */
    @SuppressWarnings("unchecked")
    public static <T, C> T get(ValueSupplier<T, C> supplier, Class<?> clazz) {
        String ret = ContextProperties.getContextVal(supplier);
        //  有可能不会存在 类的情况
        if (null == clazz) clazz = supplier.valueClass();
        try {
            if (isPrimateType(clazz)) {
                switch (JavaTypeEnum.getByClazz(clazz)) {
                    case INTEGER:
                        return (T) Integer.valueOf(ret);
                    case BYTE:
                        return (T) Byte.valueOf(ret);
                    case SHORT:
                        return (T) Short.valueOf(ret);
                    case LONG:
                        return (T) Long.valueOf(ret);
                    case DOUBLE:
                        return (T) Double.valueOf(ret);
                    case FLOAT:
                        return (T) Float.valueOf(ret);
                    case CHARACTER:
                        return (T) (Character) ret.charAt(0);
                    case BOOLEAN:
                        return (T) Boolean.valueOf(ret);
                    default:
                        return (T) ret;
                }
            } else {
                // 非基础类型
                Class<?> aClass = supplier.getDefaultValue().getClass();
                if (aClass.isAssignableFrom(Map.class)) {
                    return (T) objectMapper.readValue(ret, new TypeReference<Map<String, C>>() {
                    });
                } else if (aClass.isAssignableFrom(List.class)) {
                    return (T) objectMapper.readValue(ret, new TypeReference<List<C>>() {
                    });
                } else {
                    return objectMapper.readValue(ret, new TypeReference<T>() {
                    });
                }
            }
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
    private static <T> boolean isPrimateType(Class<T> clazz) {
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
