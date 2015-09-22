package com.liwshuo.jobcalendar.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liwshuo on 2015/9/21.
 */
public class CalendarUtil {
    public static int DAYS_OF_MONTH_VIEW = 42;
    public static int DATS_OF_WEEK = 7;
    private int positionOfCurrentDayInMonthView;

    private int currentYear;
    private int currentMonth;
    private int currentDay;

    public CalendarUtil() {
        positionOfCurrentDayInMonthView = -1;
        initCurrentDate();
    }

    private void initCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
        String simpleDate = simpleDateFormat.format(date);
        String[] simpleDateArray = simpleDate.split("-");
        currentYear = Integer.valueOf(simpleDateArray[0]);
        currentMonth = Integer.valueOf(simpleDateArray[1]);
        currentDay = Integer.valueOf(simpleDateArray[2]);
    }

    public List<String> getMonthViewData(int year, int month) {
        List<String> monthViewData = new ArrayList<>();
        monthViewData.addAll(getWeekData());
        monthViewData.addAll(getMonthData(year, month));
        positionOfCurrentDayInMonthView += DATS_OF_WEEK;
        return monthViewData;
    }

    public List<String> getWeekData() {
        List<String> weekData = new ArrayList<>(7);
        weekData.add("周日");
        weekData.add("周一");
        weekData.add("周二");
        weekData.add("周三");
        weekData.add("周四");
        weekData.add("周五");
        weekData.add("周六");
        return weekData;
    }

    public List<String> getMonthData(int year, int month) {
        List<String> monthData = new ArrayList<>(DAYS_OF_MONTH_VIEW);
        positionOfCurrentDayInMonthView = -1;
        int daysOfPreMonth = getDaysOfPreMonth(year, month);
        int daysOfCurMonth = getDaysOfMonth(year, month);
        int firstWeekDayOfMonth = getWeekDayOfMonth(year, month, 1);
        if (firstWeekDayOfMonth == 1) {
            firstWeekDayOfMonth += 7;
        }
        int j = 1;
        for (int i = 1; i <= DAYS_OF_MONTH_VIEW; i++) {
            if (i < firstWeekDayOfMonth) {
                monthData.add(String.valueOf(daysOfPreMonth - firstWeekDayOfMonth + i + 1));
            }else if(i < daysOfCurMonth + firstWeekDayOfMonth) {
                int day = i - firstWeekDayOfMonth + 1;
                monthData.add(String.valueOf(day));
                if (currentYear == year && currentMonth == month && currentDay == day) {
                    positionOfCurrentDayInMonthView = i;
                }
            }else {
                monthData.add(String.valueOf(j));
                j++;
            }
        }
        return monthData;
    }

    private int getDaysOfPreMonth(int year, int month) {
        if(month == 1) {
            month = 12;
            year = year - 1;
        }else {
            month = month - 1;
        }
        return getDaysOfMonth(year, month);
    }

    private int getDaysOfNextMonth(int year, int month) {
        if(month == 12) {
            month = 1;
            year = year + 1;
        }else {
            month = month + 1;
        }
        return getDaysOfMonth(year, month);
    }

    public int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
        }
        return 0;
    }

    public boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        }else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    public int getWeekDayOfMonth(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public int getPositionOfCurrentDayInMonthView() {
        return positionOfCurrentDayInMonthView;
    }
}
