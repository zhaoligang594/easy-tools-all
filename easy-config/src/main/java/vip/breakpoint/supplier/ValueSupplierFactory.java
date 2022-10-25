package vip.breakpoint.supplier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import vip.breakpoint.enums.JavaTypeEnum;
import vip.breakpoint.exception.OptNotSupportException;
import vip.breakpoint.supplier.base.ContextProperties;
import vip.breakpoint.supplier.value.ValueSupplier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

    static {
        // 转换为格式化的json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //修改日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取值
     *
     * @param supplier 类型提供器
     * @param <T>      返回的类型
     * @return 返回特定的值
     */
    @SuppressWarnings("unchecked")
    public static <T, C> T get(ValueSupplier<T, C> supplier, Class<T> clazz) {
        String ret = ContextProperties.getContextVal(supplier);
        try {
            if (null != clazz && isPrimitiveType(clazz)) {
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
                // mot the primitive type
                Class<?> retClazz = supplier.getDefaultValue().getClass();
                Class<C> innerClazz = supplier.valueClass();
                if (Map.class.isAssignableFrom(retClazz)) {
                    Map<String, C> tempMap = objectMapper.readValue(ret, new TypeReference<Map<String, C>>() {
                        // nothing to do
                    });
                    Map<String, C> retMap = new HashMap<>();
                    for (Map.Entry<String, C> entry : tempMap.entrySet()) {
                        retMap.put(entry.getKey(),
                                getObject(objectMapper.writeValueAsString(entry.getValue()), innerClazz));
                    }
                    return (T) retMap;
                } else if (List.class.isAssignableFrom(retClazz)) {
                    List<C> cs = objectMapper.readValue(ret, new TypeReference<List<C>>() {
                        // nothing to do
                    });
                    List<C> retList = new ArrayList<>();
                    for (C c : cs) {
                        retList.add(getObject(objectMapper.writeValueAsString(c), innerClazz));
                    }
                    return (T) retList;
                } else {
                    return (T) getObject(ret, retClazz);//objectMapper.readValue(ret, retClazz);
                }
            }
        } catch (OptNotSupportException e2) {
            throw e2;
        } catch (Exception e) {
            return supplier.getDefaultValue();
        }
    }

    private static <C> C getObject(String text, Class<C> clazz) throws Exception {
        return (C) objectMapper.readValue(text, clazz);
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
