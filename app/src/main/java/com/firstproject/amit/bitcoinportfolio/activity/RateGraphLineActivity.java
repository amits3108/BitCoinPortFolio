package com.firstproject.amit.bitcoinportfolio.activity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.model.GraphDetailModel;
import com.firstproject.amit.bitcoinportfolio.model.ValuesModel;
import com.firstproject.amit.bitcoinportfolio.network.HttpRequestHandler;
import com.firstproject.amit.bitcoinportfolio.network.api.ApiCall;
import com.firstproject.amit.bitcoinportfolio.network.apiCall.GetUSDGraphDataApiCall;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.numetriclabz.numandroidcharts.AreaChart;
//import com.numetriclabz.numandroidcharts.ChartData;
//import com.numetriclabz.numandroidcharts.LineChart;
import com.github.mikephil.charting.charts.LineChart;


import java.util.ArrayList;
import java.util.List;

public class RateGraphLineActivity extends BaseActivity implements OnChartGestureListener, OnChartValueSelectedListener {
    private LineChart areaChart;
    //    List<ChartData> value1;
    private TextView tvGraphName, tvGraphUnit, tvGraphDescription;
    private ArrayList<Entry> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_graph_line);
        setToolBar();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.graph_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void getId() {
        areaChart = findViewById(R.id.area_chart_line);
        tvGraphName = (TextView) findViewById(R.id.tv_graph_name);
        tvGraphUnit = (TextView) findViewById(R.id.tv_graph_unit);
        tvGraphDescription = (TextView) findViewById(R.id.tv_graph_description);
    }

    @Override
    public void setData() {
        getGraphDataFromServer();
        setGraph();
    }

    @Override
    public void setListener() {

    }

    private void getGraphDataFromServer() {
        final GetUSDGraphDataApiCall getUSDGraphDataApiCall = new GetUSDGraphDataApiCall(RateGraphLineActivity.this);
        HttpRequestHandler.getInstance(RateGraphLineActivity.this).executeRequest(getUSDGraphDataApiCall, new ApiCall.OnApiCallCompleteListener() {
            @Override
            public void onComplete(Exception e) {
                if (e == null) {
                    GraphDetailModel graphDetailModel = (GraphDetailModel) getUSDGraphDataApiCall.getResult();

                    values = new ArrayList<Entry>();
                    if (graphDetailModel.getStatus().equalsIgnoreCase("ok")) {
                        //TODO: When populating the TO much data than graph is distorted
                        //TODO: need to search out the Zoom In/Out functionality.
                        for (ValuesModel valuesModel : graphDetailModel.getValues()) {
                            values.add(new Entry(valuesModel.getX(), valuesModel.getY(), getResources().getDrawable(R.drawable.star)));
                        }
                        //Set Graph Details
                        tvGraphName.append(" " + graphDetailModel.getName());
                        tvGraphUnit.append(" " + graphDetailModel.getUnit());
                        tvGraphDescription.append(" " + graphDetailModel.getDescription());

                        setGraphData();
                    }
                } else {
                    Toast.makeText(RateGraphLineActivity.this, "No Data found for graph From server", Toast.LENGTH_SHORT).show();
                }
            }
        }, false);
    }

    private void setGraph() {
        areaChart.setOnChartGestureListener(this);
        areaChart.setOnChartValueSelectedListener(this);
        areaChart.setDrawGridBackground(false);

        // no description text
        areaChart.getDescription().setEnabled(false);

        // enable touch gestures
        areaChart.setTouchEnabled(true);

        // enable scaling and dragging
        areaChart.setDragEnabled(true);
        areaChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        areaChart.setPinchZoom(true);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = areaChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = areaChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
//        leftAxis.setAxisMaximum(200f);
//        leftAxis.setAxisMinimum(-50f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        areaChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
//        setGraphData(45, 100);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        areaChart.animateX(2000);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = areaChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
    }

    private void setGraphData() {
        LineDataSet set1;

        if (areaChart.getData() != null &&
                areaChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) areaChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            areaChart.getData().notifyDataChanged();
            areaChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            areaChart.setData(data);
        }
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
