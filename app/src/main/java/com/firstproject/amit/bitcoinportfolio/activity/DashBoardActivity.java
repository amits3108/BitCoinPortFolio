package com.firstproject.amit.bitcoinportfolio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.adapter.InvestMentHistoryAdapter;
import com.firstproject.amit.bitcoinportfolio.controller.DbController;
import com.firstproject.amit.bitcoinportfolio.interfaces.RefreshListListener;
import com.firstproject.amit.bitcoinportfolio.model.GraphDetailModel;
import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;
import com.firstproject.amit.bitcoinportfolio.model.UsdToBtcModel;
import com.firstproject.amit.bitcoinportfolio.model.ValuesModel;
import com.firstproject.amit.bitcoinportfolio.network.HttpRequestHandler;
import com.firstproject.amit.bitcoinportfolio.network.api.ApiCall;
import com.firstproject.amit.bitcoinportfolio.network.apiCall.GetUSDGraphDataApiCall;
import com.firstproject.amit.bitcoinportfolio.network.apiCall.GetUsdToBtcDataApiCall;
import com.firstproject.amit.bitcoinportfolio.utils.Constant;
import com.numetriclabz.numandroidcharts.ChartData;

import java.util.ArrayList;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {
    ImageView plusButton;
    private CardView usdToBtcView;
    private ArrayList<InvestmentModel> investmentModelArrayList;
    private RecyclerView rvInvestMentHistory;
    private TextView tvUsdBtc;
    private TextView tvUsdBtcValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setToolBar();
    }

    private void setToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            plusButton = (ImageView)toolbar.findViewById(R.id.img_plus);
            plusButton.setOnClickListener(DashBoardActivity.this);
        }
    }

    @Override
    public void getId() {
        usdToBtcView = (CardView) findViewById(R.id.usd_btc_view);
        rvInvestMentHistory = (RecyclerView) findViewById(R.id.rv_investment_history);
        tvUsdBtc = (TextView) findViewById(R.id.tv_usd_btc);
        tvUsdBtcValue = (TextView) findViewById(R.id.tv_usd_btc_value);
    }

    @Override
    public void setData() {
        getInvestMentHistory();
        setAdapter();
        getUsdToBtcServerData();
    }

    @Override
    public void setListener() {
        usdToBtcView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_plus:
                Intent intent = new Intent(new Intent(DashBoardActivity.this, AddInvestMentActivity.class));
                startActivityForResult(intent, Constant.REQUEST_CODE_ADD_INVESTMENT);
                break;
            case R.id.usd_btc_view:
                startActivity(new Intent(DashBoardActivity.this,RateGraphActivity.class));
                break;
        }
    }

    private void getInvestMentHistory() {
        DbController dbController = DbController.getInstance();
        investmentModelArrayList = dbController.getAllInvestMents(DashBoardActivity.this);
    }

    private void setAdapter() {
        InvestMentHistoryAdapter investMentHistoryAdapter = new InvestMentHistoryAdapter(DashBoardActivity.this, investmentModelArrayList);
        rvInvestMentHistory.setLayoutManager(new LinearLayoutManager(DashBoardActivity.this));
        rvInvestMentHistory.setAdapter(investMentHistoryAdapter);
    }

    private void getUsdToBtcServerData() {
        final GetUsdToBtcDataApiCall getUsdToBtcDataApiCall = new GetUsdToBtcDataApiCall(DashBoardActivity.this);
        HttpRequestHandler.getInstance(DashBoardActivity.this).executeRequest(getUsdToBtcDataApiCall, new ApiCall.OnApiCallCompleteListener() {

            @Override
            public void onComplete(Exception e) {
//                if (e == null) {
                UsdToBtcModel usdToBtcModel = (UsdToBtcModel) getUsdToBtcDataApiCall.getResult();
                if (usdToBtcModel.getName().equalsIgnoreCase("Bitcoin")) {
                    tvUsdBtc.setText("USD / " + usdToBtcModel.getSymbol().toUpperCase());
                    tvUsdBtcValue.setText("$" + usdToBtcModel.getPrice_usd() + "/" + usdToBtcModel.getPrice_btc());
                }
//                } else {
//                    Toast.makeText(DashBoardActivity.this, "No Data found for Conversion From server", Toast.LENGTH_SHORT).show();
//                }
            }
        }, false);
    }

    public void refreshLists() {
        rvInvestMentHistory.setAdapter(null);
        getInvestMentHistory();
        setAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_ADD_INVESTMENT && resultCode == Constant.RESULT_CODE_FROM_ADD_INVESMENT_DONE) {
            refreshLists();
        }
    }
}
