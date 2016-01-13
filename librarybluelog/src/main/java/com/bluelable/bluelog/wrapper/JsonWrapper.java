package com.bluelable.bluelog.wrapper;

/**
 * Created by xinspace on 01/13/16.
 * 用于把类封装成JSON字符串，与把字符串转换成相应的类
 */
public interface JsonWrapper<T> {

    /**
     * @author xinspace
     * created at 01/13/16 14:38
     * 把类转换为JSON字符串
     * @param object 要转换的对象，每个属性有get和set方法
     * @return 转换后的字符串
     */
    String toJson(Object object);

    /**
     * @author xinspace
     * created at 01/13/16 14:40
     * 把字符串转换为相应的类
     */
    <T> T fromJson(String jsonString, Class<T> classOfT);
}
