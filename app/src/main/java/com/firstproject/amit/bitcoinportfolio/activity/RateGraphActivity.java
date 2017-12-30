package com.firstproject.amit.bitcoinportfolio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.model.GraphDetailModel;
import com.firstproject.amit.bitcoinportfolio.model.ValuesModel;
import com.firstproject.amit.bitcoinportfolio.network.HttpRequestHandler;
import com.firstproject.amit.bitcoinportfolio.network.api.ApiCall;
import com.firstproject.amit.bitcoinportfolio.network.apiCall.GetUSDGraphDataApiCall;
import com.numetriclabz.numandroidcharts.AreaChart;
import com.numetriclabz.numandroidcharts.ChartData;

import java.util.ArrayList;
import java.util.List;

public class RateGraphActivity extends BaseActivity {
    AreaChart areaChart;
    List<ChartData> value1;
    private TextView tvGraphName, tvGraphUnit, tvGraphDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rate_graph);
        setToolBar();
    }

    private void setToolBar(){
        Toolbar toolbar = findViewById(R.id.graph_toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void getId() {
        areaChart = (AreaChart) findViewById(R.id.area_chart);
        tvGraphName = (TextView) findViewById(R.id.tv_graph_name);
        tvGraphUnit = (TextView) findViewById(R.id.tv_graph_unit);
        tvGraphDescription = (TextView) findViewById(R.id.tv_graph_description);
    }

    @Override
    public void setData() {
//        createChartData();
        getGraphDataFromServer();
    }

    @Override
    public void setListener() {

    }

    public void createChartData() {
        //////////////////////////////////NOTE: This is the dummy data only/////////////////////////
        value1 = new ArrayList<>();
        value1.add(new ChartData(9f, 1f));
        value1.add(new ChartData(18f, 2f));
        value1.add(new ChartData(2f, 4f));
        value1.add(new ChartData(12f, 5f));
        value1.add(new ChartData(9f, 7f));

        List<ChartData> value2 = new ArrayList<>();
        value2.add(new ChartData(5f, 1f));
        value2.add(new ChartData(6f, 2f));
        value2.add(new ChartData(15f, 3f));
        value2.add(new ChartData(2f, 5f));
        value2.add(new ChartData(3f, 8f));

        List<ChartData> value3 = new ArrayList<>();
        value3.add(new ChartData(value1));
        value3.add(new ChartData(value2));

        areaChart.setData(value3);

//        List<String> type = new ArrayList<>();
//        type.add(value1);
//        type.add(value2);
//        areaChart.setLegends(type);
    }

    private void getGraphDataFromServer() {
        final GetUSDGraphDataApiCall getUSDGraphDataApiCall = new GetUSDGraphDataApiCall(RateGraphActivity.this);
        HttpRequestHandler.getInstance(RateGraphActivity.this).executeRequest(getUSDGraphDataApiCall, new ApiCall.OnApiCallCompleteListener() {
            @Override
            public void onComplete(Exception e) {
                if (e == null) {
                    value1 = new ArrayList<>();
                    GraphDetailModel graphDetailModel = (GraphDetailModel) getUSDGraphDataApiCall.getResult();

                    if(graphDetailModel.getStatus().equalsIgnoreCase("ok")) {
                        for (ValuesModel valuesModel : graphDetailModel.getValues()) {
                            value1.add(new ChartData(valuesModel.getY(), (float) valuesModel.getX()));
                        }
                        //Set Graph Details
                        tvGraphName.append(" "+graphDetailModel.getName());
                        tvGraphUnit.append(" "+graphDetailModel.getUnit());
                        tvGraphDescription.append(" "+graphDetailModel.getDescription());

                        setGraphData(value1);
                    }
                } else {
                    Toast.makeText(RateGraphActivity.this, "No Data found for graph From server", Toast.LENGTH_SHORT).show();
                }
            }
        }, false);
    }

    private void setGraphData(List<ChartData> charDataValue) {
        List<ChartData> value3 = new ArrayList<>();
        value3.add(new ChartData(charDataValue));
        areaChart.setData(value3);

        //graph Renovation
        areaChart.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        areaChart.setHorizontalScrollBarEnabled(true);
        areaChart.setVerticalScrollBarEnabled(true);
        areaChart.canScrollHorizontally(View.SCROLL_AXIS_HORIZONTAL);
        areaChart.canScrollVertically(View.SCROLL_AXIS_VERTICAL);
        areaChart.setScrollContainer(true);
        areaChart.invalidate();
    }

}
