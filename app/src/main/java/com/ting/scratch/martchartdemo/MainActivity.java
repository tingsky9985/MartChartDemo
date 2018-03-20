package com.ting.scratch.martchartdemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener, OnChartGestureListener {

    private LineChart mChart;
    final List<String> mXDataList = new ArrayList<>();
    final List<Double> mYDataList = new ArrayList<Double>();

    List<ClientDetailTransactionBean> clientDetailTransactionBeans = new ArrayList<>();
    ClientDetailTransactionBean clientDetailTransactionBean = new ClientDetailTransactionBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChart = (LineChart) findViewById(R.id.chart_body);

        setData();//

        bindChartView(mChart, 6, 6,getXDataClientDetailTransactionList(clientDetailTransactionBeans), getYMaxData(mYDataList));
        setChartData(6, 3);
        mChart.animateX(1000);
    }

    public void setData() {
        clientDetailTransactionBean.setPayDate("2018-12");
        clientDetailTransactionBeans.add(clientDetailTransactionBean);
        clientDetailTransactionBean.setPayDate("2019-01");
        clientDetailTransactionBeans.add(clientDetailTransactionBean);
        clientDetailTransactionBean.setPayDate("2019-02");
        clientDetailTransactionBeans.add(clientDetailTransactionBean);
        clientDetailTransactionBean.setPayDate("2019-03");
        clientDetailTransactionBeans.add(clientDetailTransactionBean);
        clientDetailTransactionBean.setPayDate("2019-04");
        clientDetailTransactionBeans.add(clientDetailTransactionBean);
        clientDetailTransactionBean.setPayDate("2019-05");
        clientDetailTransactionBeans.add(clientDetailTransactionBean);

        mYDataList.add(100.00);
        mYDataList.add(398.6);
        mYDataList.add(309.4);
        mYDataList.add(960.3);
    }

    public List<String> getXDataClientDetailTransactionList(List<ClientDetailTransactionBean> clientDetailTransactionBeans) {
        List<String> mXDataList = new ArrayList<>();
        for (int xData = 0; xData < clientDetailTransactionBeans.size(); xData++) {
            if (xData == 0) {
                mXDataList.add(clientDetailTransactionBeans.get(xData).getPayDate());
            } else {
                mXDataList.add(DateUtils.YearMonthToSingleMonth(clientDetailTransactionBeans.get(xData).getPayDate()));
            }
        }

        Log.d("aaaaa", "getXDataClientDetailTransactionList" + mXDataList.toString());

        return mXDataList;
    }


    public List<String> getYDataList(List<Double> dataList) {
        List<String> mYDataList = new ArrayList<String>();
        double maxData = 0;
        for (int yData = 0; yData < dataList.size(); yData++) {
            if (maxData < dataList.get(yData)) {
                maxData = dataList.get(yData);
            }
        }

        maxData = Math.ceil(maxData);

        if (maxData == 0) {
            for (int y = 0; y < 6; y++) {
                mYDataList.add(String.valueOf(y));
            }
        }


        return mYDataList;
    }

    public float getYMaxData(List<Double> dataList) {
        double maxData = 0;
        for (int yData = 0; yData < dataList.size(); yData++) {
            if (maxData < dataList.get(yData)) {
                maxData = dataList.get(yData);
            }
        }

        maxData = Math.ceil(maxData);

        if (maxData == 0) {
            maxData = 5;
        }

        Log.d("aaaaa", "getYMaxData" + maxData);

        return (float)maxData;
    }


    /**
     * 绑定折线图
     */
    private void bindChartView(LineChart mChart, int mXSize, int mYSize, final List<String> xDataList, float yDataList) {
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.getDescription().setEnabled(false);
        // enable touch gestures
        mChart.setTouchEnabled(false);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawBorders(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        mChart.setDrawLegend(false);

        //X轴
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);//是否绘制x轴线
        xAxis.setDrawGridLines(false);//是否绘制网络线
        xAxis.setTextColor(getResources().getColor(R.color.chart_x_text_color));
        xAxis.setTextSize(10f);
        //xAxis.setDrawLabels(false);//liuting
        xAxis.setLabelCount(mXSize, true);//设置x轴标签数
        xAxis.setAvoidFirstLastClipping(false);////图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("aaaaa", "getFormattedValue" + value + ",xDataList: " + xDataList);
                if (value >= xDataList.size()) return "";
                return xDataList.get((int) value); //mList为存有月份的集合
            }
        });

        //左侧y轴
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setDrawGridLines(true);//是否绘制网格线
        leftAxis.setGridColor(getResources().getColor(R.color.chart_grid_color));
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawAxisLine(false);//是否绘制坐标轴线
        leftAxis.setTextColor(getResources().getColor(R.color.chart_x_text_color));
        leftAxis.setTextSize(10f);
        leftAxis.setLabelCount(6);
        leftAxis.setAxisMaxValue(yDataList);


        mChart.getAxisRight().setEnabled(false);//右侧y轴禁用

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
    }

    /**
     * 设置折线图数据
     * @param count
     * @param range
     */
    private void setChartData(int count, float range) {
        ArrayList<Entry> values = new ArrayList<Entry>();
        //折线图点图标
        int listSize = count;
        if (listSize == 0) {
            return;
        }

        for (int i = 0; i < listSize; i++) {
            //int payCount = clientDetailBeans.get(i).getPayCount();
            //double moneyCount = clientDetailBeans.get(i).getMoneyCount();
            float val = (float) (Math.random() * range) + 3;
            //TODO 此处需要y值算法
            int moneyMax = 0;
            values.add(new Entry(i, (int)count));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");

            set1.setDrawIcons(false);
            // set the line to be drawn like this "- - - - - -"
            //set1.enableDashedLine(10f, 5f, 0f);
            //set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.enableDashedLine(0f, 0f, 0f);
            set1.setColor(getResources().getColor(R.color.chart_text_color));
            set1.setCircleColor(getResources().getColor(R.color.chart_text_color));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.setDrawCircleHole(false);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(0f);
            set1.setDrawValues(false);//不显示线上值

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.chart_fade_background);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(R.color.chart_text_color);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
