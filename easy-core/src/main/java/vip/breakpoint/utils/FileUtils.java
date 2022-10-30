package vip.breakpoint.utils;

import vip.breakpoint.annotation.MParam;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 写入文件以及读取文件
 *
 * @author breakpoint/赵先生
 * 2020/09/03
 */
public abstract class FileUtils {

    /**
     * 日志的操作
     */
    private static final Logger log = WebLogFactory.getLogger(FileUtils.class);

    // 将数据写入文件
    public static boolean writeBytesToFile(@MParam("二进制数据") byte[] bytes,
                                           @MParam("文件名") String fileName,
                                           @MParam("文件地址") String filePath) throws EasyToolException {
        if (null == bytes || bytes.length == 0) return false;
        try {
            EasyVerifyUtils.verifyStringIsNotNull(fileName, filePath);
        } catch (EasyToolException e) {
            return false;
        }
        File dir = new File(filePath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new EasyToolException("请更改该文件夹[" + filePath + "]的权限，新建文件夹失败！！");
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(dir, fileName));
            fos.write(bytes);
            fos.flush();
            // 保存成功的情况
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private static ByteArrayOutputStream getInitByteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }

    // 从文件流里面读取我们的数据 注意 ：已经将文件流进行了关闭的操作
    public static byte[] readBytesFromInputStream(@MParam("文件的输入流") InputStream inputStream)
            throws EasyToolException {
        if (null == inputStream) throw new EasyToolException("输入流不能为空");
        ByteArrayOutputStream bos = null;
        try {
            bos = getInitByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            // 返回数据
            return bos.toByteArray();
        } catch (Exception e) {
            return null;
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }// end finally
    }

    // 读取文件数据
    public static byte[] readBytesFromFile(@MParam("文件名") String fileName,
                                           @MParam("文件路径") String filePath) throws EasyToolException {
        EasyVerifyUtils.verifyStringIsNotNull(fileName, filePath);
        File file = new File(filePath, fileName);
        if (!file.exists()) return null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = getInitByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            // 返回数据
            return bos.toByteArray();
        } catch (Exception e) {
            return null;
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }// end finally
    }

    // 复制文件的操作
    public static boolean copyFile(@MParam("源文件名称") String oriFileName, @MParam("源文件地址") String oriFilePath,
                                   @MParam("目标文件名字") String sinkFileName, @MParam("目标文件地址") String sinkFilePath)
            throws EasyToolException {
        EasyVerifyUtils.verifyString(oriFileName, "oriFileName");
        EasyVerifyUtils.verifyString(oriFilePath, "oriFilePath");
        EasyVerifyUtils.verifyString(sinkFileName, "sinkFileName");
        EasyVerifyUtils.verifyString(sinkFilePath, "sinkFilePath");
        byte[] bytes = readBytesFromFile(oriFileName, oriFilePath);
        if (null != bytes && bytes.length > 0) {
            try {
                writeBytesToFile(bytes, sinkFileName, sinkFilePath);
                return true;
            } catch (Exception e) {
                throw new EasyToolException("文件复制出现错误");
            }

        } else {
            throw new EasyToolException("源文件不存在");
        }
    }

    // 文件夹内部复制
    public static boolean copyFile(@MParam("源文件") String oriFileName, @MParam("文件路径") String filePath,
                                   @MParam("目标文件") String sinkFileName) throws EasyToolException {
        return copyFile(oriFileName, filePath, sinkFileName, filePath);
    }

    // 文件的删除操作
    public static boolean deleteFile(@MParam("文件名") String fileName, @MParam("文件路径") String filePath)
            throws EasyToolException {
        EasyVerifyUtils.verifyString(fileName, "fileName");
        EasyVerifyUtils.verifyString(filePath, "filePath");
        final File file = new File(filePath, fileName);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    // 删除文件
    public static boolean deleteFile(final File file) throws EasyToolException {
        EasyVerifyUtils.verifyObject(file, file.getName());
        return file.exists() && file.delete();
    }

    // 获取字符串 从文件中
    public static String getStringFromFile(@MParam("文件名") String fileName,
                                           @MParam("文件路径") String filePath) {
        return getStringFromFile(filePath + File.separator + fileName);
    }

    // 获取字符串重文件中
    public static String getStringFromFile(@MParam("文件名") String absolutePath) {
        StringBuilder jsonStr = new StringBuilder();
        Reader reader = null;
        try {
            // 获取到文件
            File file = new File(absolutePath);
            if (!file.exists()) return null;
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                jsonStr.append((char) ch);
            }
            return jsonStr.toString();
        } catch (IOException e) {
            log.error("文件读取失败 [{}]", e.getMessage());
            return null;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // nothing to do
                }
            }
        }
    }
}
