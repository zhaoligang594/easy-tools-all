package vip.breakpoint.utils;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 根据代理对象 获取原来的对象
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/29
 * 欢迎关注公众号:代码废柴
 */
public class AopTargetUtils {

    // 获取代理的真正的对象 代理对象有可能会有多层的代理 这个时候 需要获取最开始的对象
    public static Object getOriginTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getOriginTarget(getJdkDynamicProxyTargetObject(proxy));
        } else { //cglib
            return getOriginTarget(getCglibProxyTargetObject(proxy));
        }
    }


    // 获取代理的真正的对象
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            return getCglibProxyTargetObject(proxy);
        }
    }

    // CGLIB 代理对象获取真实的对象
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        Object dynamicAdvisedInterceptor = ReflectUtils.getFieldValueFromObj(h, proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }

    @SuppressWarnings({"unchecked"})
    protected <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return (T) ((Advised) proxy).getTargetSource().getTarget();
        } else {
            return (T) proxy; // expected to be cglib proxy then, which is simply a specialized class
        }
    }

    public static Object getTargetFromEasyLogProxy(Object bean) {
        if (isProxyByEasyLog(bean)) {
            try {
                Field h = bean.getClass().getDeclaredField("CGLIB$CALLBACK_0");
                Object dynamicAdvisedInterceptor = ReflectUtils.getFieldValueFromObj(h, bean);
                List<Field> fieldsFromClazz = ReflectUtils.getFieldsFromClazz(dynamicAdvisedInterceptor.getClass());
                Field unProxyDelegate = fieldsFromClazz.stream()
                        .filter(i -> i.getName().equals("unProxyDelegate")).findFirst().orElse(null);
                if (null != unProxyDelegate) {
                    return ReflectUtils.getFieldValueFromObj(unProxyDelegate, dynamicAdvisedInterceptor);
                }
            } catch (Exception e) {
                // occur error
                return bean;
            }
        }
        return bean;
    }

    public static boolean isProxyByEasyLog(Object bean) {
        return bean.getClass().getName().contains(ClassUtils.CGLIB_CLASS_SEPARATOR);
    }
}
