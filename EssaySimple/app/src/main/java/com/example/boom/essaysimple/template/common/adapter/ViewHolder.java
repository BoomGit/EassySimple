package com.example.boom.essaysimple.template.common.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Boom on 2017/6/12.
 */

public class ViewHolder {
    //实例化我们的convertView
    private View mRootView;
    //integers to Object  相当于Map
    SparseArray mViews = new SparseArray();
    //new ViewHolder
    public ViewHolder(Context context, int layoutId) {
        //实例化我们的convertView
        mRootView=View.inflate(context,layoutId,null);
        //设置我们的tag
        mRootView.setTag(this);
    }
    //获取ViewHolder
    public static ViewHolder getHolder(Context context, View convertView,int layoutId){
        if (convertView==null){
            ViewHolder viewHolder = new ViewHolder(context,layoutId);
            return viewHolder;
        }else{
            return (ViewHolder) convertView.getTag();
        }
    }
    //返回convertView
    public View  getConvertView(){
        return mRootView;
    }

    /**
     * 设置文本
     * @param viewId
     * @param item
     * @return ViewHolder  为了给调用者   可以链式调用
     */
    public ViewHolder setText(int viewId,String item){
        //减少findViewById的次数
        TextView textView = (TextView) getView(viewId);
        textView.setText(item);
        return this;
    }

    /**
     *   根据viewId 获取V
     *   方法泛型
     * @param viewId
     * @return
     */
    public  <T extends View> T getView(int viewId){
        //去集合中去找
        View view = (View) mViews.get(viewId);
        if (view == null){
            //如果第一次没找到 相当于缓存
            view = mRootView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }
}
