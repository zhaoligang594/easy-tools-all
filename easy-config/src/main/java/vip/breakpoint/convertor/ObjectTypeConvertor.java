package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.utils.ObjectMapperUtils;

import java.util.List;

/**
 * Short 转换器
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class ObjectTypeConvertor<C> implements TypeConvertor<String, C> {

    private final Class<C> valueClazz;

    public ObjectTypeConvertor(Class<C> valueClazz) {
        this.valueClazz = valueClazz;
    }

    @Override
    public C doConvert(String s) throws Exception {
        return ObjectMapperUtils.getObject(s, valueClazz);
    }
}
