package vip.breakpoint.utils;

import vip.breakpoint.annotation.MaskField;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mask every things
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/30
 * 欢迎关注公众号:代码废柴
 */
public class MaskUtils {

    private static final Logger log = WebLogFactory.getLogger(MaskUtils.class);

    public static String maskPhone(String phoneNumber) {
        if (EasyStringUtils.isNotBlank(phoneNumber)) {
            return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phoneNumber;
    }

    public static String maskEmail(String email) {
        if (EasyStringUtils.isNotBlank(email)) {
            int idx = email.indexOf("@");
            String prefix = email.substring(0, idx);
            String suffix = email.substring(idx + 1);
            if (prefix.length() > 4) {
                return email.replaceAll("(\\w+)\\w{3}(\\w)@(\\w+)", "$1***$2@$3");
            } else if (prefix.length() > 0) {
                return String.format("%s***%s%s", prefix.charAt(0), prefix.charAt(prefix.length() - 1), suffix);
            } else {
                return String.format("***%s", suffix);
            }
        }
        return email;
    }

    public static String maskIdCard(String idCard) {
        if (EasyStringUtils.isNotBlank(idCard)) {
            return idCard.replaceAll("(?<=\\w{6})\\w(?=\\w{6})", "*");
        }
        return idCard;
    }

    public static <T> T mask(T t) {
        Class<?> clazz = t.getClass();
        List<Field> fields = ReflectUtils.getFieldsFromClazz(clazz);
        for (Field field : fields) {
            MaskField maskField = field.getAnnotation(MaskField.class);
            if (null != maskField) {
                Object targetValue = null;
                try {
                    Object value = ReflectUtils.getFieldValueFromObj(field, t);
                    switch (maskField.value()) {
                        case EMAIL: {
                            targetValue = maskEmail(String.valueOf(value));
                            break;
                        }
                        case PHONE_NUMBER: {
                            targetValue = maskPhone(String.valueOf(value));
                            break;
                        }
                        case ID_CARD: {
                            targetValue = maskIdCard(String.valueOf(value));
                            break;
                        }
                        default: {
                            targetValue = value;
                        }
                    }
                    if (null != targetValue) {
                        ReflectUtils.setFieldValue2Object(field, t, targetValue);
                    }
                } catch (IllegalAccessException e) {
                    log.error("MASK PROCESS OCCUR ERROR", e);
                }
            }
        }
        return t;
    }

    public static <T> List<T> maskList(List<T> list) {
        List<T> ret = new ArrayList<>();
        for (T t : list) {
            ret.add(mask(t));
        }
        return ret;
    }

    public static <K, V> Map<K, V> maskMapValue(Map<K, V> map) {
        Map<K, V> ret = new HashMap<>();
        for (Map.Entry<K, V> e : map.entrySet()) {
            ret.put(e.getKey(), mask(e.getValue()));
        }
        return ret;
    }
}
