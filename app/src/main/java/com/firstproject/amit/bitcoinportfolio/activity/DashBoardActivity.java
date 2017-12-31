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
import android.widget.Toast;

import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.adapter.InvestMentHistoryAdapter;
import com.firstproject.amit.bitcoinportfolio.controller.DbController;
import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;

import java.util.ArrayList;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {
    ImageView plusButton;
    private CardView usdToBtcView;
    private ArrayList<InvestmentModel> investmentModelArrayList;
    private RecyclerView rvInvestMentHistory;

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
    }

    @Override
    public void setData() {
        getInvestMentHistory();
        setAdapter();
    }

    @Override
    public void setListener() {
        usdToBtcView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_plus:
                startActivity(new Intent(DashBoardActivity.this,AddInvestMentActivity.class));
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
}
