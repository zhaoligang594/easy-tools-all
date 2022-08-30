package vip.breakpoint.browser;


import com.google.gson.Gson;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.enums.MimeTypeEnum;
import vip.breakpoint.enums.ResCodeEnum;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.utils.EasyStringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


/**
 * 将对象进进行写回浏览器
 * Created by Administrator on 2018/4/29 0029.
 */
public abstract class ExploreWriteUtils {

    // 将信息写会浏览器
    public static void writeMessage(ResCodeEnum retCodeConstant, HttpServletRequest request,
                                    HttpServletResponse response, Object message) throws IOException {
        preSetCommonHeader(request, response);
        response.setContentType(MimeTypeEnum.APPLICATION_JSON.getMimeType());
        Gson gson = new Gson();
        ResponseResult responseResult = ResponseResult.createResult(retCodeConstant, "操作失败", message);
        String writeMessage = gson.toJson(responseResult);
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

    // 下载文件的操作
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, InputStream inputStream,
                                    String downFileName) throws IOException, EasyToolException {
        if (null == inputStream) throw new EasyToolException("输入流不能为空");
        preSetCommonHeader(request, response);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downFileName, "UTF-8"));
        OutputStream out = null;
        try {
            int len = -1;
            byte buffer[] = new byte[10240];
            out = response.getOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new EasyToolException(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    // 下载文件的操作
    public static void downLoadFileByBytes(HttpServletRequest request, HttpServletResponse response, byte[] bytes,
                                           String downFileName) throws IOException, EasyToolException {
        if (null == bytes || bytes.length == 0) throw new EasyToolException("输入流不能为空");
        preSetCommonHeader(request, response);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downFileName, "UTF-8"));
        OutputStream out = null;
        try {
            int len = -1;
            out = response.getOutputStream();
            out.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new EasyToolException(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    // 下载文件的操作
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fileAbsolutePath,
                                    String downFileName) throws IOException, EasyToolException {
        if (EasyStringUtils.isBlank(fileAbsolutePath)) {
            throw new EasyToolException("fileAbsolutePath is blank");
        }
        if (EasyStringUtils.isBlank(downFileName)) {
            throw new EasyToolException("downFileName is blank");
        }
        preSetCommonHeader(request, response);
        OutputStream out = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileAbsolutePath);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downFileName, "UTF-8"));
            int len = -1;
            byte buffer[] = new byte[10240];
            out = response.getOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new EasyToolException(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


    //设置我们的请求可以跨域请求
    private static void preSetCommonHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,token,is");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Request-Headers", "Origin, X-Requested-With, content-Type, Accept, Authorization");
        response.setCharacterEncoding("UTF-8");
    }
}
