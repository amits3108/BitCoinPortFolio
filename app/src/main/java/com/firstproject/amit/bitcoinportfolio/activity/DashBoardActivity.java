package com.firstproject.amit.bitcoinportfolio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.firstproject.amit.bitcoinportfolio.R;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {
    ImageView plusButton;
    private CardView usdToBtcView;

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
    }

    @Override
    public void setData() {

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
}
