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

    private static final Map<String, Set<SpringBeanWrapper>> backUpBeanMap = new HashMap<>();

    // add item to the valueKey2BeanMap
    public static void addSpringBeanWrapper(String valueKey, SpringBeanWrapper wrapper) {
        Set<SpringBeanWrapper> wrappers = valueKey2BeanMap.getOrDefault(valueKey, new HashSet<>());
        wrappers.add(wrapper);
        valueKey2BeanMap.put(valueKey, wrappers);
    }

    public static void addSpringBeanWrapper2BackUp(String valueKey, SpringBeanWrapper wrapper) {
        Set<SpringBeanWrapper> wrappers = backUpBeanMap.getOrDefault(valueKey, new HashSet<>());
        wrappers.add(wrapper);
        backUpBeanMap.put(valueKey, wrappers);
    }

    public static void addSpringBeanWrapper2BackUp(String valueKey, Set<SpringBeanWrapper> wrappers) {
        Set<SpringBeanWrapper> wrapperSet = backUpBeanMap.getOrDefault(valueKey, new HashSet<>());
        wrapperSet.addAll(wrappers);
        backUpBeanMap.put(valueKey, wrapperSet);
    }

    public static void addSpringBeanWrapper(String valueKey, Set<SpringBeanWrapper> springBeanWrappers) {
        Set<SpringBeanWrapper> wrappers = valueKey2BeanMap.getOrDefault(valueKey, new HashSet<>());
        wrappers.addAll(springBeanWrappers);
        valueKey2BeanMap.put(valueKey, wrappers);
    }

    @NonNull
    public static Set<SpringBeanWrapper> getSpringBeanWrapperByKey(String valueKey) {
        return valueKey2BeanMap.getOrDefault(valueKey, new HashSet<>());
    }

    @NonNull
    public static Map<String, Set<SpringBeanWrapper>> getBackUpBeanMap() {
        return backUpBeanMap;
    }
}
