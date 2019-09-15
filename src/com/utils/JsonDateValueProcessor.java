package com.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 在将对象信息转为json格式的时候，Date类型的数据转化被转化为不是我们想要的格式的
 * 本工具就是将Date转化为我们想要的格式的
 * 调用的方法：
 *    JsonConfig jsonConfig = new JsonConfig();
 *    jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
 *    JSONArray.fromObject(对象,jsonConfig).toString();
 *
 * @autor goh_liu
 * @date 2019/8/31 - 9:01
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

    private String pattern = "yyyy-MM-dd HH:mm:ss";

    public Object processArrayValue(Object value, JsonConfig config) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig config) {
        return process(value);
    }
    private Object process(Object value){
        if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.UK);
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }

}
