package vip.breakpoint.utils;


import com.google.gson.Gson;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.enums.MimeTypeEnum;
import vip.breakpoint.enums.ResCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * 将对象进进行写回浏览器
 * Created by Administrator on 2018/4/29 0029.
 */
public abstract class ExploreWriteUtils {

    // 将信息写会浏览器
    public static void writeMessage(ResCodeEnum resCodeEnum, HttpServletRequest request,
                                    HttpServletResponse response, Object message) throws IOException {
        ResponseResult<Object> responseResult = ResponseResult.createResult(resCodeEnum, "操作失败", message);
        writeMessage(request, response, responseResult);
    }

    public static <D> void writeMessage(HttpServletRequest request, HttpServletResponse response, D data)
            throws IOException {
        ResponseUtils.preSetCommonHeader(response);
        response.setContentType(MimeTypeEnum.APPLICATION_JSON.getMimeType());
        String writeMessage = getMessage(data);
        String callbackFunName = request.getParameter("callback");
        PrintWriter writer = response.getWriter();
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase("")) {
            writer.println(writeMessage);
        } else {
            writer.println(callbackFunName + "( " + writeMessage + " )");
        }
        response.setStatus(HttpServletResponse.SC_OK);
        writer.close();
        return;
    }

    private static String getMessage(Object data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
