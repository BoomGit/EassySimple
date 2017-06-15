package com.example.boom.essaysimple.http.engine;

import android.os.Handler;

import java.util.Map;

/**
 * Created by Boom on 2017/6/14.
 */

public class HttpUtils implements HttpEngine {
    public static Handler mHandler = new Handler();
    private HttpEngine httpEngine;
    public HttpUtils(){
        httpEngine= new XutilsHttpEngine();
    }

    @Override
    public void post(Map<String, String> params, String url, HttpCallBack httpCallBack) {
        httpEngine.post(params,url,httpCallBack);
    }

    @Override
    public void get(Map<String, String> params, String url, HttpCallBack httpCallBack) {
        httpEngine.get(params,url,httpCallBack);
    }
}
