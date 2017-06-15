package com.example.boom.essaysimple.http.engine;

import android.util.Log;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Boom on 2017/6/14.
 */

public class XutilsHttpEngine<T> implements HttpEngine {

    @Override
    public void post(Map<String, String> params, String url, final HttpCallBack httpCallBack) {
        RequestParams requestParams = new RequestParams(url);
        //添加的参数肯定是从 params 里面解析  遍历Map集合
        Iterator<Map.Entry<String,String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry)iterator.next();
            //Object key = entry.getKey();
            //Object value = entry.getValue();
            requestParams.addBodyParameter(entry.getKey(),entry.getValue());
        }
        //post请求
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "onSuccess: "+ result );
                //2.Gson解析请求到的数据
                Gson gson = new Gson();
                //获取泛型的类型
                Type genType = httpCallBack.getClass().getGenericSuperclass();
                Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
                Class<T> typeClass= (Class<T>) params[0];
                //利用Gson转换类型
                final T resultObj = gson.fromJson(result,typeClass);
                //运行在主线程中
                HttpUtils.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.OnSuccess(resultObj);
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                httpCallBack.OnError();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void get(Map<String, String> params, String url, HttpCallBack httpCallBack) {
        RequestParams requestParams = new RequestParams(url);
        Iterator<Map.Entry<String,String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry)iterator.next();
            //Object key = entry.getKey();
            //Object value = entry.getValue();
            requestParams.addBodyParameter(entry.getKey(),entry.getValue());
        }
        //get请求
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
