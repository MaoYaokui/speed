package com.tasi.speed.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author myk
 * @version V1.0
 * @FileName CommonFunction.java
 * @Description:TODO 公共工具类
 * @createtime 2019-07-31
 */
public class CommonFunction {

    /**
     * 判断是否为空
     *
     * @param content
     * @return
     */
    public static boolean isnull(String content) {
        return content == null || content.trim().isEmpty() || "null".equals(content);
    }

    /**
     * 判断是否为空(所有类型)
     *
     * @param content
     * @return
     */
    public static boolean isnull(Object content) {
        return content == null || "".equals(content);
    }


    /**
     * 补齐不足长度
     *
     * @param length 长度
     * @param number 数字
     * @return
     */
    public static String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 切分Ids
     *
     * @param ids
     * @return
     */
    public static List<Integer> segmentationIds(String ids) {
        List<Integer> idList = new ArrayList<>();
        if (ids.contains(",")) {
            String[] idGroup = ids.split(",");
            for (String id : idGroup) {
                idList.add(Integer.parseInt(id));
            }
        } else {
            idList.add(Integer.parseInt(ids));
        }
        return idList;
    }

    /**
     * Object 转 List
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 时间转换 将 HH:mm:ss.SS 格式 转换为秒
     * @param Time
     * @return
     * @throws ParseException
     */
    public static long timeTransform(String Time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = sdf.parse("1970-01-01 " + Time);
        return date.getTime() / 1000;
    }
}
