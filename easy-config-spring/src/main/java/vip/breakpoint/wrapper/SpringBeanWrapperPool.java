package vip.breakpoint.wrapper;

import org.springframework.lang.NonNull;

import java.util.*;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/29
 * 欢迎关注公众号:代码废柴
 */
public class SpringBeanWrapperPool {

    private static final Map<String, Set<SpringBeanWrapper>> valueKey2BeanMap = new HashMap<>();

    // add item to the valueKey2BeanMap
    public static void addSpringBeanWrapper(String valueKey, SpringBeanWrapper wrapper) {
        Set<SpringBeanWrapper> wrappers = valueKey2BeanMap.getOrDefault(valueKey, new HashSet<>());
        wrappers.add(wrapper);
        valueKey2BeanMap.put(valueKey, wrappers);
    }

    @NonNull
    public static Set<SpringBeanWrapper> getSpringBeanWrapperByKey(String valueKey) {
        return valueKey2BeanMap.getOrDefault(valueKey, new HashSet<>());
    }
}
