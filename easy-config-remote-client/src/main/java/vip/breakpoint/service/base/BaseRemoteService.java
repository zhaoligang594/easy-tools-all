package vip.breakpoint.service.base;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vip.breakpoint.dto.RemoteResult;
import vip.breakpoint.enums.ResCodeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.ExecutorPoolUtils;
import vip.breakpoint.utils.ObjectMapperUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public abstract class BaseRemoteService {

    private static final Logger log = WebLogFactory.getLogger(BaseRemoteService.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取 list 结果
     *
     * @param uri          请求的链接
     * @param uriVariables 参数配置
     * @param clazz        转换类型
     * @param <T>          类型
     * @return 返回的类型
     */
    protected <T> List<T> getListFromRemote(String uri, Map<String, Object> uriVariables, Class<T> clazz) {
        try {
            ResponseEntity<RemoteResult> responseEntity = ExecutorPoolUtils.getObjectWithTimeOut(500L,
                    () -> restTemplate.getForEntity(uri, RemoteResult.class, uriVariables));
            if (null != responseEntity) {
                RemoteResult body = responseEntity.getBody();
                if (null != body) {
                    log.info("getObjectFromRemote response data:{}", JSONObject.toJSONString(body));
                    if (ResCodeEnum.SUCCESS.getCode() == body.getCode()) {
                        return ObjectMapperUtils.getList(JSONObject.toJSONString(body.getData()), clazz);
                    }
                }
                return null;
            }
            log.info("getObjectFromRemote nothing return");
            return null;
        } catch (Exception e) {
            log.error("getObjectFromRemote occur error:", e);
            return null;
        }
    }

    /**
     * 获取 list 结果
     *
     * @param uri          请求的链接
     * @param uriVariables 参数配置
     * @param clazz        转换类型
     * @param <T>          类型
     * @return 返回的类型
     */
    protected <T> T getObjectFromRemote(String uri, Map<String, Object> uriVariables, Class<T> clazz) {
        try {
            ResponseEntity<RemoteResult> responseEntity = ExecutorPoolUtils.getObjectWithTimeOut(500L,
                    () -> restTemplate.getForEntity(uri, RemoteResult.class, uriVariables));
            if (null != responseEntity) {
                RemoteResult body = responseEntity.getBody();
                if (null != body && ResCodeEnum.SUCCESS.getCode() == body.getCode()) {
                    return ObjectMapperUtils.getObject(JSONObject.toJSONString(body.getData()), clazz);
                }
                return null;
            }
            log.info("getObjectFromRemote nothing return");
            return null;
        } catch (Exception e) {
            log.error("getObjectFromRemote occur error:", e);
            return null;
        }
    }

}
