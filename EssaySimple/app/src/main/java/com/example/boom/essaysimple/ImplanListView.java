package com.example.boom.essaysimple;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Boom on 2017/6/5.
 */

public class ImplanListView extends ListView {

    public ImplanListView(Context context) {
        super(context);
    }

    public ImplanListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImplanListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
