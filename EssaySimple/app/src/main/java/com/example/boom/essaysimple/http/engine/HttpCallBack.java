package com.example.boom.essaysimple.http.engine;

/**
 * Created by Boom on 2017/6/14.
 */

/**
 * 回掉  接口或者定义abstract类
 * 是为了调用者要实现该方法
 * 方法为泛型 类也要为泛型  比如ArrayList<E>  ArrayList<String> list = new ArrayList();
 * interface 是特殊的类
 */
public interface HttpCallBack<T> {
    //成功
    public void OnSuccess(T result);
    //失败
    public void OnError();
}
