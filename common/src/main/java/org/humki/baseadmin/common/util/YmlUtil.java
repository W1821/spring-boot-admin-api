package org.humki.baseadmin.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Kael
 * @date 2018/11/26 0026
 */
public class YmlUtil {

    /**
     * Tab用两个空格格式化
     */
    private static final String TAB_SPACE = "  ";

    /**
     * yml每行结尾
     */
    private static final String ENDING_SYMBOL = ": ";

    /**
     * properties转yml
     */
    public static boolean propertiesToYml(String propertiesPath, String propertiesCharset) {
        List<String> lines = FileUtil.readLines(propertiesPath, propertiesCharset);
        String path = FileUtil.getAbsolutePath(propertiesPath);
        List<String> ymlLines = propertiesListToYmlList(lines);
        return FileUtil.writeLines(path, ymlLines);
    }

    /**
     * properties格式行转yml格式
     */
    private static List<String> propertiesListToYmlList(List<String> lines) {
        //保存yml的行内容
        List<String> ymlLines = new ArrayList<>();

        //yml文档树
        Map<String, List<String>> treeMap = new TreeMap<>();
        //父级名称
        String parent = "";
        //子节点列表
        List<String> element;

        //使用 TreeMap 排好序
        Map<String, String> sourceMap = getSourceTreeMap(lines);
        for (String key : sourceMap.keySet()) {
            //.拆分key
            String[] keys = key.split("\\.");
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < keys.length; i++) {
                //从第二个节点开始，行前面需要加tab来格式化，并设置它的父节点
                if (i > 0) {
                    parent += keys[i - 1];
                    prefix.append(TAB_SPACE);
                }
                String line = prefix + keys[i] + ENDING_SYMBOL;
                treeMap.computeIfAbsent(parent, k -> new ArrayList<>());

                if (!treeMap.get(parent).contains(line)) {
                    element = treeMap.get(parent) == null ? new ArrayList<>() : treeMap.get(parent);
                    if (!element.contains(line)) {
                        element.add(line);
                        treeMap.put(parent, element);
                    }
                    if (i == keys.length - 1) {
                        ymlLines.add(line + sourceMap.get(key));
                        parent = "";
                    } else {
                        ymlLines.add(line);
                    }
                }
            }
        }
        return ymlLines;
    }

    /**
     * TreeMap进行排序
     * 去掉注释行
     */
    private static Map<String, String> getSourceTreeMap(List<String> lines) {
        Map<String, String> sourceMap = new TreeMap<>();
        for (String line : lines) {
            if (!StringUtils.isEmpty(line) && !line.startsWith("#")) {
                String key = line.substring(0, line.indexOf("="));
                String value = line.substring(line.indexOf("=") + 1);
                sourceMap.put(key, value);
            }
        }
        return sourceMap;
    }


}
