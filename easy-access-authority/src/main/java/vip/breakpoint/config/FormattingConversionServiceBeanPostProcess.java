package vip.breakpoint.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.lang.NonNull;
import vip.breakpoint.converter.DateConverter;

/**
 * 日期的定义转换的操作
 * bean 的 后置处理器
 *
 * @author breakpoint/zlgtop@163.com
 * create on 2022/11/07
 */
public class FormattingConversionServiceBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof FormattingConversionService) {
            // #issue 设置后置处理器 处理我 日期不能转换的问题
            /*
                    例如： 在一个方法得分接口上 如何 直接写参数 @RequestParam("nowTime") Date nowTime
                    是不可以正常的进行解析的 这个时候 我们要自己定对一个日期的转换器 使得我们的转换能够正常的运行
             */
            FormattingConversionService service = (FormattingConversionService) bean;
            // 定义自定义的转换类 专门转换日期的类型
            service.addConverter(new DateConverter());
        }
        return bean;
    }
}
