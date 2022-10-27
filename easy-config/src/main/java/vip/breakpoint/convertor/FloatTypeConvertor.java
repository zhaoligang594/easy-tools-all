package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * Float 转换器
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class FloatTypeConvertor implements TypeConvertor<String, Float> {

    @Override
    public Float doConvert(String s) throws Exception {
        return Float.valueOf(s);
    }
}
