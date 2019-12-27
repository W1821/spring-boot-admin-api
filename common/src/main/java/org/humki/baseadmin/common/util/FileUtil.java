package org.humki.baseadmin.common.util;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author Kael
 * @date 2018/10/10 0010
 */
@Slf4j
public class FileUtil {

    /**
     * Buffer的大小
     */
    private static final Integer BUFFER_SIZE = 1024 * 1024 * 10;


    /**
     * 复制文件
     *
     * @param resourcePath 源文件
     * @param targetPath   目标文件
     * @return 是否成功
     */
    public static boolean copy(String resourcePath, String targetPath) {
        File file = new File(resourcePath);
        return copy(file, targetPath);
    }

    /**
     * 复制文件
     * 通过该方式复制文件文件越大速度越是明显
     *
     * @param file       需要处理的文件
     * @param targetFile 目标文件
     * @return 是否成功
     */
    public static boolean copy(File file, String targetFile) {
        try (
                FileInputStream fin = new FileInputStream(file);
                FileOutputStream fout = new FileOutputStream(new File(targetFile))
        ) {
            FileChannel in = fin.getChannel();
            FileChannel out = fout.getChannel();
            //设定缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (in.read(buffer) != -1) {
                //准备写入，防止其他读取，锁住文件
                buffer.flip();
                out.write(buffer);
                //准备读取。将缓冲区清理完毕，移动文件内部指针
                buffer.clear();
            }
        } catch (IOException e) {
            log.error("复制文件出错", e);
        }
        return false;
    }


    /**
     * 创建多级目录
     *
     * @param paths 需要创建的目录
     * @return 是否成功
     */
    public static boolean createPaths(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdir();
    }

    /**
     * 创建多级目录
     *
     * @param directory 目录文件
     * @return 是否成功
     */
    public static boolean createDirectory(File directory) {
        return directory.exists() || directory.mkdirs();
    }

    /**
     * 创建文件支持多级目录
     *
     * @param filePath 需要创建的文件
     * @return 是否成功
     */
    public static boolean createFiles(String filePath) {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    log.error("创建文件支持多级目录错误");
                }
            }
        }
        return false;
    }

    /**
     * 删除一个文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean deleteFile(File file) {
        return file.delete();
    }

    /**
     * 删除一个目录
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean deleteDir(File file) {
        List<File> files = listFileAll(file);
        if (files == null) {
            return true;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                deleteDir(f);
            } else {
                deleteFile(f);
            }
        }
        return file.delete();
    }

    /**
     * 快速清空一个超大的文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean cleanFile(File file) {
        try (
                FileWriter fw = new FileWriter(file)
        ) {
            fw.write("");
            return true;
        } catch (IOException e) {
            log.error("快速清空一个超大的文件", e);
        }
        return false;
    }

    /**
     * 快速的删除超大的文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean deleteBigFile(File file) {
        return cleanFile(file) && file.delete();
    }


    /**
     * 复制目录
     *
     * @param filePath   需要处理的文件
     * @param targetPath 目标文件
     */
    public static void copyDir(String filePath, String targetPath) {
        File file = new File(filePath);
        copyDir(file, targetPath);
    }

    /**
     * 复制目录
     *
     * @param filePath   需要处理的文件
     * @param targetPath 目标文件
     */
    public static void copyDir(File filePath, String targetPath) {
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            createPaths(targetPath);
        }
        File[] files = filePath.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String path = file.getName();
            if (file.isDirectory()) {
                copyDir(file, targetPath + File.separator + path);
            } else {
                copy(file, targetPath + File.separator + path);
            }
        }

    }

    /**
     * 罗列指定路径下的全部文件包括文件夹
     *
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public static List<File> listFileAll(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (files == null) {
            return list;
        }
        for (File file : files) {
            list.add(file);
            if (file.isDirectory()) {
                list.addAll(listFileAll(file));
            }
        }
        return list;
    }

    /**
     * 写入字符串
     *
     * @param path 文件全路径
     * @param data 字符串数据
     */
    public static boolean writeLines(String path, List<String> data) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);
        ) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            log.error("写入文件错误", e);
            return false;
        }
    }

    /**
     * 一行一行读取文件
     *
     * @param path    文件全路径
     * @param charset 字符集
     */
    public static List<String> readLines(String path, String charset) {
        List<String> lines = new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader read = new BufferedReader(inputStreamReader);
        ) {
            String line;
            while ((line = read.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error("读取文件错误", e);
        }
        return lines;
    }

    /**
     * 获取相对路径
     */
    public static String getAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }


}
