package vip.breakpoint.utils;


import vip.breakpoint.exception.EasyToolException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;


/**
 * 下载文件工具
 */
public abstract class ExploreDownloadUtils {

    // 下载文件的操作
    public static void downLoadFile(HttpServletResponse response, InputStream inputStream,
                                    String downFileName) throws IOException, EasyToolException {
        if (null == inputStream) throw new EasyToolException("输入流不能为空");
        ResponseUtils.preSetCommonHeader(response);
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(downFileName, "UTF-8"));
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
    public static void downLoadFileByBytes(HttpServletResponse response, byte[] bytes,
                                           String downFileName) throws IOException, EasyToolException {
        if (null == bytes || bytes.length == 0) throw new EasyToolException("输入流不能为空");
        ResponseUtils.preSetCommonHeader(response);
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(downFileName, "UTF-8"));
        OutputStream out = null;
        try {
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
    public static void downLoadFile(HttpServletResponse response, String fileAbsolutePath,
                                    String downFileName) throws IOException, EasyToolException {
        if (EasyStringUtils.isBlank(fileAbsolutePath)) {
            throw new EasyToolException("fileAbsolutePath is blank");
        }
        if (EasyStringUtils.isBlank(downFileName)) {
            throw new EasyToolException("downFileName is blank");
        }
        ResponseUtils.preSetCommonHeader(response);
        OutputStream out = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileAbsolutePath);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(downFileName, "UTF-8"));
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
}
