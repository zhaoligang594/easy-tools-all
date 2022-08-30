package vip.breakpoint.utils;

import vip.breakpoint.annotation.MethodParam;
import vip.breakpoint.exception.BaseException;

import java.io.*;

/**
 * 写入文件以及读取文件
 *
 * @author breakpoint/赵先生
 * 2020/09/03
 */
public abstract class FileUtils {

    // 将数据写入文件
    public static boolean writeBytesToFile(@MethodParam("二进制数据") byte[] bytes,
                                           @MethodParam("文件名") String fileName,
                                           @MethodParam("文件地址") String filePath) throws BaseException {
        if (null == bytes || bytes.length == 0) return false;
        try {
            LocalVerify.verifyStringIsNotNull(fileName, filePath);
        } catch (BaseException e) {
            return false;
        }
        File dir = new File(filePath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new BaseException("请更改该文件夹[" + filePath + "]的权限，新建文件夹失败！！");
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
    public static byte[] readBytesFromInputStream(@MethodParam("文件的输入流") InputStream inputStream) throws BaseException {
        if (null == inputStream) throw new BaseException("输入流不能为空");
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
    public static byte[] readBytesFromFile(@MethodParam("文件名") String fileName,
                                           @MethodParam("文件路径") String filePath) throws BaseException {
        LocalVerify.verifyStringIsNotNull(fileName, filePath);
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
    public static boolean copyFile(@MethodParam("源文件名称") String oriFileName, @MethodParam("源文件地址") String oriFilePath,
                                   @MethodParam("目标文件名字") String sinkFileName, @MethodParam("目标文件地址") String sinkFilePath)
            throws BaseException {
        LocalVerify.verifyString(oriFileName, "oriFileName");
        LocalVerify.verifyString(oriFilePath, "oriFilePath");
        LocalVerify.verifyString(sinkFileName, "sinkFileName");
        LocalVerify.verifyString(sinkFilePath, "sinkFilePath");
        byte[] bytes = readBytesFromFile(oriFileName, oriFilePath);
        if (null != bytes && bytes.length > 0) {
            try {
                writeBytesToFile(bytes, sinkFileName, sinkFilePath);
                return true;
            } catch (Exception e) {
                throw new BaseException("文件复制出现错误");
            }

        } else {
            throw new BaseException("源文件不存在");
        }
    }

    // 文件夹内部复制
    public static boolean copyFile(@MethodParam("源文件") String oriFileName, @MethodParam("文件路径") String filePath,
                                   @MethodParam("目标文件") String sinkFileName) throws BaseException {
        return copyFile(oriFileName, filePath, sinkFileName, filePath);
    }

    // 文件的删除操作
    public static boolean deleteFile(@MethodParam("文件名") String fileName, @MethodParam("文件路径") String filePath)
            throws BaseException {
        LocalVerify.verifyString(fileName, "fileName");
        LocalVerify.verifyString(filePath, "filePath");
        final File file = new File(filePath, fileName);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    // 删除文件
    public static boolean deleteFile(final File file)
            throws BaseException {
        LocalVerify.verifyObject(file, file.getName());
        return file.exists() && file.delete();
    }

}
