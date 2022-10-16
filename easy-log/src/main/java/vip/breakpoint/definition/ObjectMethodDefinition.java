package vip.breakpoint.definition;

import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public class ObjectMethodDefinition {

    // : fixBug 出现异常的bug
    private final Map<String, WebLogging> methodMap = new ConcurrentHashMap<String, WebLogging>();
    private final Map<Class<?>, WebLogging> webLoggingMap = new ConcurrentHashMap<Class<?>, WebLogging>();
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

    public boolean isHaveMethod(String methodName) {
        return methodMap.containsKey(methodName);
    }

    public boolean isShouldProxy() {
        return methodMap.size() > 0;
    }

    private void putMethod(WebLogging webLogging, String... methods) {
        for (String method : methods) {
            methodMap.put(method, webLogging);
        }
    }

    public void addWebLogging(Class<?> clazz, WebLogging webLogging) {
        webLoggingMap.put(clazz, webLogging);
        if (isContainAll(webLogging)) {
            List<String> methodsParams = new ArrayList<>();
            for (Method method : clazz.getMethods()) {
                if (!excludeMethod.contains(method.getName())) {
                    methodsParams.add(method.getName());
                }
            }
            this.putMethod(webLogging, methodsParams.toArray(new String[0]));
        } else {
            this.putMethod(webLogging, webLogging.methods());
        }
    }

    private boolean isContainAll(WebLogging webLogging) {
        for (String method : webLogging.methods()) {
            if ("*".equals(method.trim())) return true;
        }
        return false;
    }

    public WebLogging getWebLoggingByMethod(String methodName) {
        return methodMap.get(methodName);
    }
}
