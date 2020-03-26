package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public class ListUtil {

    private static final String COMMA = ",";

    /**
     * List<String>类型转换成字符串，增加,
     */
    public static String joinCommaToString(List<String> list) {
        if (list == null) {
            return null;
        }
        return String.join(COMMA, list);
    }

    /**
     * 集合排序
     *
     * @param targetList 要排序的实体类List集合
     * @param sortField  排序字段(实体类属性名), 只支持基本类型及基本类型包装类型和String
     * @param isAsc      true升序，false降序
     */
    public static <T> void sort(List<T> targetList, String sortField, boolean isAsc) {
        if (targetList == null || StringUtil.isEmpty(sortField)) {
            return;
        }

        String type = getTypeName(targetList, sortField);
        if (type == null) {
            return;
        }

        targetList.sort((obj1, obj2) -> {
            int retVal = 0;
            try {
                Object comp1 = getFieldValue(sortField, obj1);
                Object comp2 = getFieldValue(sortField, obj2);
                Object o1 = isAsc ? comp1 : comp2;
                Object o2 = isAsc ? comp2 : comp1;

                // 字段为null，排在最后
                if (o1 == null || o2 == null) {
                    return 1;
                }

                switch (type) {
                    case "byte":
                    case "java.lang.Byte":
                    case "short":
                    case "java.lang.short":
                    case "int":
                    case "java.lang.Integer":
                        retVal = Integer.compare((int) o1, (int) o2);
                        break;

                    case "long":
                    case "java.lang.Long":
                        retVal = Long.compare((long) o1, (long) o2);
                        break;

                    case "float":
                    case "java.lang.Float":
                        retVal = Float.compare((float) o1, (float) o2);
                        break;

                    case "double":
                    case "java.lang.Double":
                        retVal = Double.compare((double) o1, (double) o2);
                        break;

                    case "boolean":
                    case "java.lang.Boolean":
                        int a = (boolean) o1 ? 1 : 0;
                        int b = (boolean) o2 ? 1 : 0;
                        retVal = Integer.compare(a, b);
                        break;

                    case "java.lang.String":
                        retVal = ((String) o1).compareTo((String) o2);
                        break;

                    default:
                        break;
                }
            } catch (Exception e) {
                log.error("排序异常,sortField={}", sortField, e);
            }
            return retVal;
        });
    }

    /**
     * 反射获取属性值
     */
    private static <T> Object getFieldValue(String sortField, T obj1) throws NoSuchFieldException, IllegalAccessException {
        Field field1 = obj1.getClass().getDeclaredField(sortField);
        field1.setAccessible(true);
        return field1.get(obj1);
    }

    /**
     * 获取排序属性类型
     */
    private static String getTypeName(List<?> targetList, String sortField) {
        Field field = null;
        try {
            field = targetList.get(0).getClass().getDeclaredField(sortField);
        } catch (Exception ignored) {
        }
        if (field == null) {
            return null;
        }
        return field.getType().getTypeName();
    }



}
