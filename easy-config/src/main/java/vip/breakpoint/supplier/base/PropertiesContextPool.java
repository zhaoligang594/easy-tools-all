package vip.breakpoint.supplier.base;

import vip.breakpoint.engine.ConfigFileMonitorEngine;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.supplier.value.ValueSupplier;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.FetchKeyValueUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * 系统的上下文环境
 * 支持 jvm的参数配置 但是值无法修改以及变更
 * // 获取上下文的环境
 * // 根据下面的代码 配置数据的优先级分别为
 * // 3种配置等效
 *
 * 例如：
 * jvm 配置： -Ddemand.project.cache.size=1000
 * properties 配置：demand.project.cache.size=200
 * yaml 配置：
 *          demand:
 *            project:
 *              cache:
 *                size: 200
 * </pre>
 *
 * @author breakpoint/赵先生
 * create on 2021/01/20
 * @see #getContextVal 获取上下文具体的代码逻辑
 */
public final class PropertiesContextPool {

    private PropertiesContextPool() {/*reject new object*/}

    private static final Logger log = WebLogFactory.getLogger(PropertiesContextPool.class);


    static {
        // 刷新当前的值
        refreshValues();
        try {
            Class.forName("org.springframework.context.ApplicationContext");
        } catch (ClassNotFoundException e) {
            log.info("use the default config!");
            ConfigFileMonitorEngine.startFileMonitorEngine(null);
        }
    }

    // 系统信息
    // 属性存储的值
    // 存储整个系统的上下文属性 以及 系统的配置
    private static class SystemContextInfo {
        /**
         * 系统的所有的配置的存储信息
         */
        private static final Map<String, String> valueKey2Config = new ConcurrentHashMap<>();
        // 系统环境变量
        private static final Map<String, String> systemConfig = new HashMap<>();

        // 获取操作的值的操作
        private static synchronized String getContextVal(String valueKey) {
            // 获取配置 基本上都是 用 -Dxxx 配置的参数
            String res = systemConfig.get(valueKey);
            // 获取值的操作
            if (null == res) {
                res = valueKey2Config.get(valueKey);
            }
            // 不满足的时候 直接返回 null
            if (EasyStringUtils.isBlank(res)) return null;
            // 默认的情况下 配置信息都是 String的形式
            return res;
        }

        // 设置系统的属性
        private static void putSystemInfo(Properties properties) {
            properties.forEach((key, value) -> {
                systemConfig.put((String) key, (String) value);
            });
        }

        /**
         * 设置用户的属性
         *
         * @param val      值
         * @param valueKey key
         */
        private static void putPropertyInfo(String valueKey, String val) {
            // 设置新值
            valueKey2Config.put(valueKey, val);
        }

    }

    // 更新系统的配置文件
    private static synchronized void refreshValues() {
        // 设置系统的数值
        SystemContextInfo.putSystemInfo(System.getProperties());
    }

    /**
     * 获取上下文的环境 变量值
     *
     * @param valueSupplier 值提供器
     * @param <T>           类型
     * @return value
     */
    public static synchronized <T, C> String getContextVal(ValueSupplier<T, C> valueSupplier) {
        return SystemContextInfo.getContextVal(valueSupplier.valueKey());
    }

    // 获取系统的信息
    public static synchronized Map<Object, Object> getSystemInfo() {
        // 返回系统的配置信息
        Map<Object, Object> res = new HashMap<>();
        res.put("systemConfig", SystemContextInfo.systemConfig);
        res.put("configValues", SystemContextInfo.valueKey2Config);
        return res;
    }    // 获取系统的信息

    public static synchronized Map<String, String> getConfigValuesMap() {
        return SystemContextInfo.valueKey2Config;
    }

    public static void refreshConfigFile(File configFile) {
        Map<String, String> key2ValueMap = FetchKeyValueUtils.getKey2ValueMap(configFile);
        if (null != key2ValueMap) {
            for (Map.Entry<String, String> e : key2ValueMap.entrySet()) {
                SystemContextInfo.putPropertyInfo(e.getKey(), e.getValue());
            }
        }
    }

    public static void refreshConfigValue(Map<String, String> key2ValueMap) {
        if (null != key2ValueMap) {
            for (Map.Entry<String, String> e : key2ValueMap.entrySet()) {
                SystemContextInfo.putPropertyInfo(e.getKey(), e.getValue());
            }
        }
    }

    // 初始化配置
    public static void init(List<File> monitorCandidateFiles) {
        if (null != monitorCandidateFiles) {
            monitorCandidateFiles.forEach(PropertiesContextPool::refreshConfigFile);
        }
    }
}
