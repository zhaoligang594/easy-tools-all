package vip.breakpoint.utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import vip.breakpoint.annotation.MParam;
import vip.breakpoint.exception.EasyExcelException;
import vip.breakpoint.utils.base.BaseExplore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * 将对象进进行写回浏览器
 * Created by Administrator on 2018/4/29 0029.
 */
public abstract class ExcelDownloadUtils extends BaseExplore {

    // 下载 excel
    public static <T> void downLoadExcelByCurrentData(HttpServletResponse response, List<T> data,
                                                      String downFileName, String sheetName) throws IOException, EasyExcelException {
        downLoadExcelByCurrentData(response, data, downFileName, sheetName, false);
    }

    // 下载 excel
    public static <T> void downLoadExcelByCurrentData(HttpServletResponse response, List<T> data,
                                                      String downFileName, String sheetName,
                                                      @MParam("是否下载模版") boolean isMould) throws IOException, EasyExcelException {
        if (null == data || data.size() == 0) throw new EasyExcelException("下载的数据不能是空");
        if (null == sheetName || "".equals(sheetName)) throw new EasyExcelException("sheetName is empty");
        preSetCommonHeader(response);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            HSSFWorkbook hssfWorkbook = HSSFWorkbookUtils.getHSSFWorkbook(sheetName, data, isMould);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downFileName + ".xls", "UTF-8"));
            hssfWorkbook.write(out);
        } catch (Exception e) {
            throw new EasyExcelException(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    // 下载 excel 多页的操作
    public static <T> void downLoadExcelByCurrentData(HttpServletResponse response,
                                                      Map<String, List<T>> dataMapVal,
                                                      String downFileName) throws IOException, EasyExcelException {
        downLoadExcelByCurrentData(response, dataMapVal, downFileName, false);
    }

    // 下载 excel 多页的操作
    public static <T> void downLoadExcelByCurrentData(HttpServletResponse response,
                                                      Map<String, List<T>> dataMapVal,
                                                      String downFileName, @MParam("是否下载模版") boolean isMould) throws IOException, EasyExcelException {
        if (null == dataMapVal || dataMapVal.size() == 0) throw new EasyExcelException("下载的数据不能是空");
        preSetCommonHeader(response);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            HSSFWorkbook hssfWorkbook = HSSFWorkbookUtils.getHSSFWorkbook(dataMapVal, isMould);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downFileName + ".xls", "UTF-8"));
            hssfWorkbook.write(out);
        } catch (Exception e) {
            throw new EasyExcelException(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
