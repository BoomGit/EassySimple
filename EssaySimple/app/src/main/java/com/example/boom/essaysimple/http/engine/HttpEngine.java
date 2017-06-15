package com.example.boom.essaysimple.http.engine;

import java.util.Map;

/**
 * Created by Boom on 2017/6/14.
 */

public interface HttpEngine {
    //参数  url  自定义回掉  post提交
    public void post(Map<String,String> params ,String url,HttpCallBack httpCallBack);
    //参数  url  自定义回掉  get提交
    public void get(Map<String,String> params ,String url,HttpCallBack httpCallBack);
}
