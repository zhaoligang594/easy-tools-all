package vip.breakpoint.supplier.base;

import com.alibaba.fastjson2.JSONObject;
import org.yaml.snakeyaml.Yaml;
import vip.breakpoint.annotation.MParam;
import vip.breakpoint.engine.ConfigFileMonitorEngine;
import vip.breakpoint.enums.FileTypeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.supplier.value.ValueSupplier;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.FileUtils;

import java.io.*;
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

    /**
     * 1
     * 获取这个类的 class loader 获取文件流 加载一些配置
     */
    private static final ClassLoader classLoader = PropertiesContextPool.class.getClassLoader();

    /**
     * 日志的操作
     */
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

    // 解析json的参数
    public static void refreshValueJSON(String valueKey, @MParam("获取到配置文件的地址") String absolutePath) {
        // 读取文件的内容
        String value = null;
        try {
            log.info("config file location：{}", absolutePath);
            value = FileUtils.getStringFromFile(absolutePath);
            if (null == value || "".equals(value)) value = FileUtils.getStringFromFile(absolutePath);
        } catch (Exception e) {
            value = FileUtils.getStringFromFile(absolutePath);
        }
        if (null != value) {
            SystemContextInfo.putPropertyInfo(valueKey, value);
        }
    }


    // 刷新 Properties 文件的属性
    public static synchronized void refreshValueProperties(@MParam("获取到配置文件的地址") String absolutePath) {
        // 定义文件流属性
        InputStream inputStream = null;
        try {
            log.info("config file location:{}", absolutePath);
            // 获取配置的文件
            inputStream = classLoader.getResourceAsStream(absolutePath);
            // 定义
            Properties properties = new Properties();
            // 加载文件流里面的属性
            properties.load(inputStream);
            // 设置配置文件的属性值
            setVal(properties);
        } catch (Exception e) {
            try {
                // 出现异常 说明 没有那个配置文件 这个时候采用 二进制 文件流的方式 获取文件的配置
                inputStream = new FileInputStream(absolutePath);
                Properties properties = new Properties();
                properties.load(inputStream);
                // 设置配置文件的属性值
                setVal(properties);
            } catch (FileNotFoundException e1) {
                log.warn("文件 " + absolutePath + " 没有找到");
            } catch (Exception e1) {
                log.error("启动时候，发生严重错误 [{}]", e.getMessage(), e);
                throw new IllegalArgumentException("启动时候，发生严重错误" + e.getMessage());
            }
        } finally {
            if (null != inputStream) {
                try {
                    // 关闭文件流
                    inputStream.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    // 设置 配置信息 到 系统上下文的 环境中
    private static synchronized void setVal(Properties properties) {
        // 设置值的操作
        properties.forEach((key, value) -> {
            SystemContextInfo.putPropertyInfo((String) key, (String) value);
        });
    }

    // 刷新 Yaml 文件的属性
    public static synchronized void refreshValueYaml(@MParam("获取到配置文件的地址") String absolutePath) {
        // 定义文件流属性
        InputStream inputStream = null;
        try {
            log.info("config file location:{}", absolutePath);
            // 获取配置的文件
            inputStream = classLoader.getResourceAsStream(absolutePath);
            // 设置值的操作
            Map<String, Object> valueMap = new Yaml().load(inputStream);
            // 设置值
            setYamlValues(valueMap, "");
        } catch (Exception e) {
            try {
                // 出现异常 说明 没有那个配置文件 这个时候采用 二进制 文件流的方式 获取文件的配置
                inputStream = new FileInputStream(absolutePath);
                // 设置值的操作
                Map<String, Object> valueMap = new Yaml().load(inputStream);
                // 设置值
                setYamlValues(valueMap, "");
            } catch (FileNotFoundException e1) {
                log.warn("文件 " + absolutePath + " 没有找到");
            } catch (Exception e1) {
                log.error("启动时候，发生严重错误 [{}]", e.getMessage(), e);
                throw new IllegalArgumentException("启动时候，发生严重错误" + e.getMessage());
            }
        } finally {
            if (null != inputStream) {
                try {
                    // 关闭文件流
                    inputStream.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    // 设置新的值
    @SuppressWarnings("unchecked")
    private static synchronized void setYamlValues(@MParam("数值信息") Map<String, Object> valueMap,
                                                   @MParam("前缀信息") String prefix) {
        if (null != valueMap) {
            final String format = EasyStringUtils.isNotBlank(prefix) ? "%s.%s" : "%s%s";
            // 循环遍历设置新值
            valueMap.forEach((key, value) -> {
                if (isPrimitiveVal(value)) {
                    String valueKey = String.format(format, prefix, key);
                    SystemContextInfo.putPropertyInfo(valueKey, String.valueOf(value));
                } else if (value instanceof Map) {
                    // @SuppressWarnings("unchecked")
                    setYamlValues((Map<String, Object>) value, String.format(format, prefix, key));
                } else if (value instanceof List) {
                    String valueKey = String.format(format, prefix, key);
                    SystemContextInfo.putPropertyInfo(valueKey, JSONObject.toJSONString(value));
                }
            });
        }
    }

    // 判断是否属于基本数据类型
    private static boolean isPrimitiveVal(Object val) {
        return null == val
                || val instanceof Byte
                || val instanceof Character
                || val instanceof Short
                || val instanceof Integer
                || val instanceof Boolean
                || val instanceof Long
                || val instanceof Float
                || val instanceof Double
                || val instanceof String;
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
    }

    public static void refreshConfigFile(File configFile) {
        if (configFile.getName().endsWith(FileTypeEnum.PROPERTIES.getFileType())) {
            refreshValueProperties(configFile.getAbsolutePath());
        } else if (configFile.getName().endsWith(FileTypeEnum.JSON.getFileType())) {
            int idx = configFile.getName().lastIndexOf(FileTypeEnum.JSON.getFileType());
            String valueKey = configFile.getName().substring(0, idx);
            refreshValueJSON(valueKey, configFile.getAbsolutePath());
        } else if (configFile.getName().endsWith(FileTypeEnum.YAML.getFileType())) {
            refreshValueYaml(configFile.getAbsolutePath());
        }
    }

    // 初始化配置
    public static void init(List<File> monitorCandidateFiles) {
        if (null != monitorCandidateFiles) {
            monitorCandidateFiles.forEach(PropertiesContextPool::refreshConfigFile);
        }
    }
}
