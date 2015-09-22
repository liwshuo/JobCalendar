package com.liwshuo.jobcalendar.month;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liwshuo on 2015/9/19.
 */
public class MonthItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(parent.getContext().getResources().getColor(android.R.color.black));
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0) {
                continue;
            }
            View childView = parent.getChildAt(i);
            float x = childView.getX();
            float y = childView.getY();
            int width = childView.getWidth();
            int height = childView.getHeight();
            canvas.drawLine(x, y, x + width, y, paint);
            canvas.drawLine(x, y + height, x + width, y + height, paint);
        }
        super.onDraw(canvas,parent,state);
    }

}
