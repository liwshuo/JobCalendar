package com.liwshuo.jobcalendar.month;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.liwshuo.jobcalendar.R;
import com.liwshuo.jobcalendar.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MonthActivity extends ActionBarActivity {

    private RecyclerView monthView;
    private MonthAdapter monthAdapter;
    private GestureDetector gestureDetector;
    private ViewFlipper monthFlipper;
    private Spinner yearPicker;
    private Spinner monthPicker;
    private int selectedYear;
    private int selectedMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        initSpinner();
        initMonthView();
    }

    private void initSpinner() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        yearPicker = (Spinner) findViewById(R.id.yearPicker);
        monthPicker = (Spinner) findViewById(R.id.monthPicker);
        List<Integer> yearList = new ArrayList<>();
        List<Integer> monthList = new ArrayList<>();
        for (int i = 1970; i < 2100; i++) {
            yearList.add(i);
        }
        final ArrayAdapter yearPickerAdapter = new ArrayAdapter<Integer>(this, R.layout.picker_view, yearList);
        yearPickerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearPicker.setAdapter(yearPickerAdapter);
        yearPicker.setSelection(year - 1970);
        yearPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = (int) yearPickerAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i = 1; i <= 12; i++) {
            monthList.add(i);
        }
        final ArrayAdapter monthPickerAdapter = new ArrayAdapter<Integer>(this, R.layout.picker_view, monthList);
        monthPickerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthPicker.setAdapter(monthPickerAdapter);
        monthPicker.setSelection(month - 1);
        monthPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = (int) monthPickerAdapter.getItem(position);
                monthAdapter.dateChanged(selectedYear, selectedMonth);
//                monthViewContainerAdapter
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initMonthView() {
        monthView = (RecyclerView) findViewById(R.id.monthView);
        monthAdapter = new MonthAdapter(this,selectedYear, selectedMonth);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (monthAdapter.isDate(position)) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        RecyclerViewTouchListener.OnItemClickListener clickListener = new RecyclerViewTouchListener.OnItemClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder, int position, long itemId) {
                if (monthAdapter.isDate(position)) {

                }else if (monthAdapter.isWeek(position)) {

                }else {
                    if(monthAdapter.getLastClickViewHolder() != null && monthAdapter.getLastClickViewHolder() != viewHolder) {
                        MonthAdapter.DayViewHolder lastDayViewHolder = (MonthAdapter.DayViewHolder) monthAdapter.getLastClickViewHolder();
                        lastDayViewHolder.dayInfo.setBackground(null);
                        lastDayViewHolder.dayInfo.setTextColor(getResources().getColor(R.color.black));
                    }
                    MonthAdapter.DayViewHolder dayViewHolder = (MonthAdapter.DayViewHolder) viewHolder;
                    dayViewHolder.dayInfo.setBackground(getResources().getDrawable(R.drawable.month_day_circle_blue));
                    dayViewHolder.dayInfo.setTextColor(getResources().getColor(R.color.white));
                    monthAdapter.setLastClickViewHolder(viewHolder);
                }
                System.out.println("click");
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder, int position, long itemId) {
                Toast.makeText(MonthActivity.this, "longclick", Toast.LENGTH_SHORT).show();
            }
        };
        monthView.setAdapter(monthAdapter);
        monthView.setLayoutManager(gridLayoutManager);
        monthView.addItemDecoration(new MonthItemDecoration());
        monthView.addOnItemTouchListener(new RecyclerViewTouchListener(this, monthView, clickListener));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_month, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
