package vip.breakpoint.resolver;


import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import vip.breakpoint.bean.PageInfo;
import vip.breakpoint.utils.EasyStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 分頁信息
 *
 * @author breakpoint/赵先生
 * 2020/12/24
 */
public class PageInfoResolver implements HandlerMethodArgumentResolver {

    // 判断参数类型
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PageInfo.class);
    }

    // 解析对象数据
    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        // 获取到请求的对象
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        PageInfo<Object> pageInfo = new PageInfo<>();
        String currentPage = "1";
        String pageSize = "10";
        if (null == request) {
            currentPage = request.getParameter("currentPage");
            pageSize = request.getParameter("pageSize");
        }
        // 初始化基本操作
        if (EasyStringUtils.isNotBlank(currentPage)) {
            try {
                pageInfo.setCurrentPage(Integer.parseInt(currentPage));
                if (pageInfo.getCurrentPage() == 1) {
                    pageInfo.setPrePage(1);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            pageInfo.setCurrentPage(1);
            pageInfo.setPrePage(1);
        }
        if (EasyStringUtils.isNotBlank(pageSize)) {
            try {
                pageInfo.setPageSize(Integer.parseInt(pageSize));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            pageInfo.setPageSize(10);
        }
        /*    设置数据的开始和结束的位置  start  */
        int start = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();
        int end = (pageInfo.getCurrentPage()) * pageInfo.getPageSize();
        pageInfo.setDataStart(start);
        pageInfo.setDataEnd(end);
        /*    设置数据的开始和结束的位置  end  */
        return pageInfo;
    }
}
