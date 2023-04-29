package vip.breakpoint.definition;

import vip.breakpoint.annotion.EasyLog;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public class ObjectMethodDefinition {

    /**
     * 记录所有的方法的map
     */
    private final Map<Method, EasyLog> method2AnnMap = new ConcurrentHashMap<Method, EasyLog>();

    private static final Object object = new Object();
    private static final Set<String> excludeMethod = new HashSet<String>();

    static {
        excludeMethod.add("getClass");
        excludeMethod.add("hashCode");
        excludeMethod.add("equals");
        excludeMethod.add("clone");
        excludeMethod.add("toString");
        excludeMethod.add("notify");
        excludeMethod.add("notifyAll");
        excludeMethod.add("wait");
        excludeMethod.add("finalize");
    }

    public boolean isHaveMethod(Method method) {
        return null != method2AnnMap.get(method);
    }

    public boolean isShouldProxy() {
        return !method2AnnMap.isEmpty();
    }

    public void addCandidateMethods(Map<Method, EasyLog> method2AnnMap) {
        if (null != method2AnnMap) {
            this.method2AnnMap.putAll(method2AnnMap);
        }
    }

    public EasyLog getEasyLogByMethod(Method method) {
        return method2AnnMap.get(method);
    }
}
