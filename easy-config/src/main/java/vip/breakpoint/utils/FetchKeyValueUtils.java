package vip.breakpoint.utils;

import com.alibaba.fastjson2.JSONObject;
import org.yaml.snakeyaml.Yaml;
import vip.breakpoint.annotation.MParam;
import vip.breakpoint.bean.StreamVo;
import vip.breakpoint.enums.FileTypeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/29
 * 欢迎关注公众号:代码废柴
 */
public class FetchKeyValueUtils {

    private static final ClassLoader classLoader = FetchKeyValueUtils.class.getClassLoader();

    private static final Logger log = WebLogFactory.getLogger(FetchKeyValueUtils.class);

    // 解析json的参数
    private static Map<String, String> getKey2ValueMapFromJSONFile(String valueKey,
                                                                   @MParam("获取到配置文件的地址") String absolutePath) {
        Map<String, String> retMap = new HashMap<>();
        String value;
        try {
            log.info("config file location：{}", absolutePath);
            value = FileUtils.getStringFromFile(absolutePath);
            if (null == value || "".equals(value)) value = FileUtils.getStringFromFile(absolutePath);
        } catch (Exception e) {
            value = FileUtils.getStringFromFile(absolutePath);
        }
        if (null != value) {
            retMap.put(valueKey, value);
        }
        return retMap;
    }    // 解析json的参数

    private static Map<String, String> getKey2ValueMapFromJSONFile(String valueKey,
                                                                   InputStream inputStream) {
        Map<String, String> retMap = new HashMap<>();
        String value = null;
        try {
            value = FileUtils.getStringFromFile(inputStream);
        } catch (Exception e) {
            value = FileUtils.getStringFromFile(inputStream);
        }
        if (null != value) {
            retMap.put(valueKey, value);
        }
        return retMap;
    }

    private static synchronized InputStream getInputStreamFromFileSystem(@MParam("获取到配置文件的地址") String absolutePath)
            throws FileNotFoundException {
        // 出现异常 说明 没有那个配置文件 这个时候采用 二进制 文件流的方式 获取文件的配置
        return new FileInputStream(absolutePath);
    }

    private static synchronized InputStream getInputStreamByClassLoader(@MParam("获取到配置文件的地址") String absolutePath)
            throws FileNotFoundException {
        // 出现异常 说明 没有那个配置文件 这个时候采用 二进制 文件流的方式 获取文件的配置
        return classLoader.getResourceAsStream(absolutePath);
    }


    // get the config values from properties
    private static synchronized Map<String, String> getKey2ValueMapFromPropertiesFile(
            @MParam("获取到配置文件的地址") InputStream inputStream) {
        Map<String, String> retMap = new HashMap<>();
        try {
            // 定义
            Properties properties = new Properties();
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // 加载文件流里面的属性
            // 设置配置文件的属性值
            properties.forEach((key, value) -> {
                retMap.put((String) key, (String) value);
            });
        } catch (Exception e) {
            // omit...
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
        return retMap;
    }

    private static synchronized Map<String, String> getKey2ValueMapFromPropertiesFile(
            @MParam("获取到配置文件的地址") String absolutePath) {
        Map<String, String> retMap = new HashMap<>();
        // 定义文件流属性
        InputStream inputStream = null;
        try {
            log.info("config file location:{}", absolutePath);
            // 获取配置的文件
            inputStream = getInputStreamFromFileSystem(absolutePath);
            // 定义
            Properties properties = new Properties();
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // 加载文件流里面的属性
            // 设置配置文件的属性值
            properties.forEach((key, value) -> {
                retMap.put((String) key, (String) value);
            });
        } catch (Exception e) {
            log.error("文件 " + absolutePath + " 没有找到");
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
        return retMap;
    }

    private static synchronized Map<String, String> getKey2ValueMapFromYamlFile(
            @MParam("获取到配置文件的地址") String absolutePath) {
        Map<String, String> retMap = new HashMap<>();
        // 定义文件流属性
        InputStream inputStream = null;
        try {
            log.info("config file location:{}", absolutePath);
            // 获取配置的文件
            inputStream = getInputStreamFromFileSystem(absolutePath);
            // 设置值的操作
            Map<String, Object> valueMap = new Yaml().load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // 设置值
            retMap.putAll(getYamlValues(valueMap, ""));
        } catch (Exception e) {
            log.warn("文件 " + absolutePath + " 没有找到");
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
        return retMap;
    }

    private static synchronized Map<String, String> getKey2ValueMapFromYamlFile(InputStream inputStream) {
        Map<String, String> retMap = new HashMap<>();
        try {
            // 设置值的操作
            Map<String, Object> valueMap = new Yaml().load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // 设置值
            retMap.putAll(getYamlValues(valueMap, ""));
        } catch (Exception e) {
            // omit...
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
        return retMap;
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

    @SuppressWarnings("unchecked")
    private static synchronized Map<String, String> getYamlValues(@MParam("数值信息") Map<String, Object> valueMap,
                                                                  @MParam("前缀信息") String prefix) {
        Map<String, String> ret = new HashMap<>();
        if (null != valueMap) {
            final String format = EasyStringUtils.isNotBlank(prefix) ? "%s.%s" : "%s%s";
            // 循环遍历设置新值
            valueMap.forEach((key, value) -> {
                if (isPrimitiveVal(value)) {
                    String valueKey = String.format(format, prefix, key);
                    ret.put(valueKey, String.valueOf(value));
                } else if (value instanceof Map) {
                    // @SuppressWarnings("unchecked")
                    Map<String, String> yamlValues =
                            getYamlValues((Map<String, Object>) value, String.format(format, prefix, key));
                    ret.putAll(yamlValues);
                } else if (value instanceof List) {
                    String valueKey = String.format(format, prefix, key);
                    ret.put(valueKey, JSONObject.toJSONString(value));
                }
            });
        }
        return ret;
    }

    public static Map<String, String> getKey2ValueMap(File configFile) {
        if (configFile.getName().endsWith(FileTypeEnum.PROPERTIES.getFileType())) {
            return getKey2ValueMapFromPropertiesFile(configFile.getAbsolutePath());
        } else if (configFile.getName().endsWith(FileTypeEnum.JSON.getFileType())) {
            int idx = configFile.getName().lastIndexOf(FileTypeEnum.JSON.getFileType());
            String valueKey = configFile.getName().substring(0, idx);
            return getKey2ValueMapFromJSONFile(valueKey, configFile.getAbsolutePath());
        } else if (configFile.getName().endsWith(FileTypeEnum.YAML.getFileType())) {
            return getKey2ValueMapFromYamlFile(configFile.getAbsolutePath());
        }
        return new HashMap<>();
    }

    public static Map<String, String> getKey2ValueMap(StreamVo streamVo) {
        switch (streamVo.getTypeEnum()) {
            case PROPERTIES: {
                return getKey2ValueMapFromPropertiesFile(streamVo.getInputStream());
            }
            case YAML: {
                return getKey2ValueMapFromYamlFile(streamVo.getInputStream());
            }
            case JSON: {
                int idx = streamVo.getFileName().lastIndexOf(FileTypeEnum.JSON.getFileType());
                String valueKey = streamVo.getFileName().substring(0, idx);
                return getKey2ValueMapFromJSONFile(valueKey, streamVo.getInputStream());
            }
            default:
                return new HashMap<>();
        }
    }
}
