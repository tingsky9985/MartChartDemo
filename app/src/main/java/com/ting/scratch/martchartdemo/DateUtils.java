package com.ting.scratch.martchartdemo;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by ting on 18-3-20.
 * 关于日期时间的工具类
 */

public class DateUtils {

    /**
     * 接口返回的2018-08转化为08月
     *
     * @param year
     * @return
     */
    public static String YearMonthToMonth (String year) {
        String month = "";
        if (TextUtils.isEmpty(year)) {
            return month;
        }

        try {
            month = year.split("-")[1];
        } catch (Exception e) {
            e.printStackTrace();
            month = year;
        }

        return month;
    }

    /**
     * 接口返回的2018-08转化为8月
     *
     * @param year
     * @return
     */
    public static String YearMonthToSingleMonth (String year) {
        String month = "";
        if (TextUtils.isEmpty(year)) {
            return month;
        }

        try {
            month = year.split("-")[1];
            Log.d("aaaaa", "YearMonthToSingleMonth 1" + month);
            String singleMonth = month.split("")[0];
            Log.d("aaaaa", "YearMonthToSingleMonth 2" + singleMonth);

            if ("0".equals(singleMonth)) {
                month = month.split("")[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
            month = year;
        }

        return month;
    }

}
