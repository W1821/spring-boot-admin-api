package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip解压缩工具类
 *
 * @author Kael
 * @date 2018/10/10 0010
 */
@Slf4j
public class ZipUtil {

    /**
     * 文档压缩
     *
     * @param resourcePath 需要压缩的文件或目录
     * @param targetPath   压缩后的文件全名称
     */
    public static boolean compress(String resourcePath, String targetPath) {
        if (resourcePath == null || targetPath == null) {
            return false;
        }
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(targetPath);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)
        ) {
            File file = new File(resourcePath);
            zipFile(file, zipOutputStream, null, targetPath);
            return true;
        } catch (IOException e) {
            log.error("压缩失败！");
        }
        return false;
    }

    /**
     * 压缩目录或文件
     */
    private static void zipFile(File inFile, ZipOutputStream zipOutputStream, String dirPath, String targetPath) {
        if (inFile.isDirectory()) {
            zipDirectoryHandle(inFile, zipOutputStream, dirPath, targetPath);
        } else {
            zipFileHandle(inFile, zipOutputStream, dirPath, targetPath);
        }
    }

    /**
     * 压缩目录处理
     */
    private static void zipDirectoryHandle(File directory, ZipOutputStream zipOutputStream, String dirPath, String targetPath) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String name = directory.getName();
            if (dirPath != null && dirPath.length() != 0) {
                name = dirPath + File.separator + name;
            }
            zipFile(file, zipOutputStream, name, targetPath);
        }
    }

    /**
     * 压缩文件处理
     */
    private static void zipFileHandle(File file, ZipOutputStream zipOutputStream, String dirPath, String targetPath) {
        // 不添加压缩文件
        if (targetPath.equals(file.getPath())) {
            return;
        }
        String entryName;
        if (dirPath != null && dirPath.length() != 0) {
            entryName = dirPath + File.separator + file.getName();
        } else {
            entryName = file.getName();
        }

        try (
                InputStream is = new FileInputStream(file)
        ) {
            ZipEntry entry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(entry);
            byte[] buffer = new byte[1024 * 8];
            int len;
            while ((len = is.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("压缩写入失败失败", e);
        }
    }

    /**
     * 文档解压
     *
     * @param sourcePath 需要解压缩的文档名称
     * @param path       需要解压缩的路径
     */
    public static boolean unCompress(String sourcePath, String path) {
        File dir = new File(path);
        if (!createDirectory(dir)) {
            return false;
        }
        // zip文件
        File source = new File(sourcePath);
        //实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
        ZipEntry zipEntry;
        //实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
        try (
                ZipFile zipFile = new ZipFile(source);
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(source))
        ) {
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File temp = new File(path + File.separator + fileName);
                if (!createDirectory(temp.getParentFile())) {
                    return false;
                }
                try (
                        OutputStream os = new FileOutputStream(temp);
                        //通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
                        InputStream is = zipFile.getInputStream(zipEntry)
                ) {
                    byte[] buffer = new byte[1024 * 8];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            log.error("解压失败", e);
            return false;
        }
        return true;
    }

    /**
     * 创建多级目录
     *
     * @param directory 目录文件
     * @return 是否成功
     */
    private static boolean createDirectory(File directory) {
        return directory.exists() || directory.mkdirs();
    }

}
