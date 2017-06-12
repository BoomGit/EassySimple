package com.example.boom.essaysimple.template.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.boom.essaysimple.R;

import java.util.List;

/**
 * Created by Boom on 2017/6/12.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mcontext;
    private List<T> mDatas;
    private int layoutId;
    public CommonAdapter(Context context ,List<T> datas,int layoutId){
        this.mcontext = context;
        this.mDatas = datas;
        this.layoutId=layoutId;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getHolder(mcontext,convertView,layoutId);
        //把设置参数的内容传递给子类
        convert(viewHolder,mDatas.get(position),position);
        return viewHolder.getConvertView();
    }

    protected abstract void convert(ViewHolder viewHolder, T t, int position);

    /*//命名为abstract 是为了让子类来实现
    protected abstract View convertView(int position, View convertView, ViewGroup parent);*/

}
