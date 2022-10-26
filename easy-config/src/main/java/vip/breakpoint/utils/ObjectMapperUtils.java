package vip.breakpoint.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import vip.breakpoint.config.ConfigFileMonitorConfig;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json 数据的工具类
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public abstract class ObjectMapperUtils {

    /**
     * 日志的操作
     */
    private static final Logger log = WebLogFactory.getLogger(ConfigFileMonitorConfig.class);

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
     * 返回 map 实例
     */
    public static <C> Map<String, C> getMap(String jsonStr, Class<C> clazz) {
        Map<String, C> retMap = new HashMap<>();
        try {
            Map<String, C> tempMap = objectMapper.readValue(jsonStr, new TypeReference<Map<String, C>>() {
                // nothing to do
            });
            for (Map.Entry<String, C> entry : tempMap.entrySet()) {
                retMap.put(entry.getKey(),
                        getObject(objectMapper.writeValueAsString(entry.getValue()), clazz));
            }
        } catch (Exception e) {
            log.error("[getMap]", e);
        }
        return retMap;
    }

    /**
     * 获取 list 实例
     */
    public static <C> List<C> getList(String jsonStr, Class<C> clazz) {
        List<C> retList = new ArrayList<>();
        try {
            List<C> cs = objectMapper.readValue(jsonStr, new TypeReference<List<C>>() {
                // nothing to do
            });
            for (C c : cs) {
                retList.add(getObject(objectMapper.writeValueAsString(c), clazz));
            }
        } catch (Exception e) {
            log.error("[getList]", e);
        }
        return retList;
    }

    /**
     * 返回对象
     */
    public static <C> C getObject(String text, Class<C> clazz) throws Exception {
        return objectMapper.readValue(text, clazz);
    }
}
