package vip.breakpoint.utils;

import vip.breakpoint.convertor.StringTypeConvertor;
import vip.breakpoint.convertor.*;
import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.enums.JavaTypeEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * define the java type convertor
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public abstract class TypeConvertorUtils {

    private static final Map<JavaTypeEnum, TypeConvertor<?, ?>> clazz2ConvertorMap = new ConcurrentHashMap<>();

    static {
        clazz2ConvertorMap.put(JavaTypeEnum.BOOLEAN, new BooleanTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.CHARACTER, new CharacterTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.BYTE, new ByteTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.SHORT, new ShortTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.INTEGER, new IntTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.LONG, new LongTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.FLOAT, new FloatTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.DOUBLE, new DoubletTypeConvertor());
        clazz2ConvertorMap.put(JavaTypeEnum.STRING, new StringTypeConvertor());
    }

    /**
     * 获取转换器
     */
    @SuppressWarnings("unchecked")
    public static <T, R> TypeConvertor<T, R> getTypeConvertor(JavaTypeEnum javaTypeEnum) {
        return (TypeConvertor<T, R>) clazz2ConvertorMap.getOrDefault(javaTypeEnum, new StringTypeConvertor());
    }
}
