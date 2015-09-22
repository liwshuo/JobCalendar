package com.liwshuo.jobcalendar.month;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liwshuo.jobcalendar.R;
import com.liwshuo.jobcalendar.utils.CalendarUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liwshuo on 2015/9/19.
 */
public class MonthAdapter extends RecyclerView.Adapter {
    public final static int ITEM_VIEW_TYPE_DATE = 0;
    public final static int ITEM_VIEW_TYPE_WEEK=1;
    public final static int ITEM_VIEW_TYPE_DAY=2;

    private List<String> dataList;
    private RecyclerView.ViewHolder lastClickViewHolder;
    private CalendarUtil calendarUtil;
    private Context context;

    public MonthAdapter(Context context, int year, int month) {
        this.context = context;
        calendarUtil = new CalendarUtil();
        this.dataList = calendarUtil.getMonthViewData(year, month);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_DATE:
                return new DateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.month_date_view, null));
            case ITEM_VIEW_TYPE_WEEK:
                return new WeekViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.month_week_view, null));
            case ITEM_VIEW_TYPE_DAY:
                return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.month_day_view, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isDate(position)) {
            ((DateViewHolder) holder).dateInfo.setText("dateInfo");
        }else if (isWeek(position)) {
            ((WeekViewHolder) holder).weekInfo.setText(dataList.get(position - 1));
        }else {
            ((DayViewHolder) holder).dayInfo.setText(dataList.get(position - 1));
            ((DayViewHolder) holder).dayInfo.setBackground(null);
            if (calendarUtil.getPositionOfCurrentDayInMonthView() == position) {
                ((DayViewHolder) holder).dayInfo.setBackground(context.getResources().getDrawable(R.drawable.month_day_circle_red));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isDate(position)) {
            return ITEM_VIEW_TYPE_DATE;
        }
        if(isWeek(position)) {
            return ITEM_VIEW_TYPE_WEEK;
        }
        return ITEM_VIEW_TYPE_DAY;
    }

    public boolean isDate(int position) {
        if (position == 0) {
            return true;
        }
        return false;
    }

    public boolean isWeek(int position) {
        if (position >= 1 && position <= CalendarUtil.DATS_OF_WEEK) {
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder{
        public TextView dateInfo;
        public DateViewHolder(View itemView) {
            super(itemView);
            dateInfo = (TextView) itemView.findViewById(R.id.dateInfo);
        }

    }

    public class WeekViewHolder extends RecyclerView.ViewHolder {
        public TextView weekInfo;
        public WeekViewHolder(View itemView) {
            super(itemView);
            weekInfo = (TextView) itemView.findViewById(R.id.weekInfo);
        }
    }

    public class DayViewHolder extends RecyclerView.ViewHolder{
        public TextView dayInfo;
        public DayViewHolder(View itemView) {
            super(itemView);
            dayInfo = (TextView) itemView.findViewById(R.id.dayInfo);
            dayInfo.setFocusableInTouchMode(true);
            dayInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        v.setBackground(null);
                    }
                }
            });
        }
    }

    public RecyclerView.ViewHolder getLastClickViewHolder() {
        return lastClickViewHolder;
    }

    public void setLastClickViewHolder(RecyclerView.ViewHolder viewHolder) {
        lastClickViewHolder = viewHolder;
    }



    public void dateChanged(int year, int month) {
        dataList.clear();
        dataList.addAll(calendarUtil.getMonthViewData(year, month));
        notifyDataSetChanged();
    }
}
