package com.example.boom.essaysimple.template.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.boom.essaysimple.R;

import java.util.List;

/**
 * Created by Boom on 2017/6/12.
 */

public class MyAdapter extends CommonAdapter<String> {

    public MyAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.item);
    }

 /*   @Override
    protected View convertView(int position, View convertView, ViewGroup parent) {

        return null;
    }*/

    @Override
    protected void convert(ViewHolder viewHolder, String s, int position) {

    }
}
