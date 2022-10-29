package vip.breakpoint.listener;

import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.enums.JavaTypeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.JavaTypeUtils;
import vip.breakpoint.utils.ReflectUtils;
import vip.breakpoint.utils.TypeConvertorUtils;
import vip.breakpoint.wrapper.SpringBeanWrapper;
import vip.breakpoint.wrapper.SpringBeanWrapperPool;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * spring的配置
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class SpringContextFileChangeListener implements FileChangeListener {

    private static final Logger log = WebLogFactory.getLogger(SpringContextFileChangeListener.class);

    @Override
    public void doChangedConfigFileRefresh(File file, Map<String, String> changeFileKeyValueMap) {
        log.info("the file have changed!! the path is:{}", file.getAbsolutePath());
        for (Map.Entry<String, String> entry : changeFileKeyValueMap.entrySet()) {
            Set<SpringBeanWrapper> beanWrappers =
                    SpringBeanWrapperPool.getSpringBeanWrapperByKey(entry.getKey());
            updateTheBeanValues(entry.getValue(), beanWrappers);
        }
    }

    private void updateTheBeanValues(String value, Set<SpringBeanWrapper> beanWrappers) {
        for (SpringBeanWrapper beanWrapper : beanWrappers) {
            Class<?> clazz = beanWrapper.getValueType();
            if (null != clazz && JavaTypeUtils.isPrimitiveType(clazz)) {
                TypeConvertor<String, ?> typeConvertor =
                        TypeConvertorUtils.getTypeConvertor(JavaTypeEnum.getByClazz(clazz));
                try {
                    Object targetValue = typeConvertor.doConvert(value);
                    ReflectUtils.setFieldValue2Object(beanWrapper.getValueField(), beanWrapper.getBean(), targetValue);
                } catch (Exception e) {
                    log.error("配置转换发生异常 beanName:{} target value:{}", beanWrapper.getBeanName(), value, e);
                }
            } else {
                // 其他的类型 下一次支持
                log.info("暂时不支持该类型");
            }
        }
    }
}
