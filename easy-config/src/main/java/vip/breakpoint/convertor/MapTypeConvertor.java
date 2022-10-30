package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.utils.ObjectMapperUtils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Map 转换器
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class MapTypeConvertor<C> implements TypeConvertor<String, Map<String, C>> {

    private final Type valueClazz;

    public MapTypeConvertor(Type valueClazz) {
        this.valueClazz = valueClazz;
    }

    @Override
    public Map<String, C> doConvert(String s) throws Exception {
        return ObjectMapperUtils.getMap(s, valueClazz);
    }
}
