package com.firstproject.amit.bitcoinportfolio.activity;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firstproject.amit.bitcoinportfolio.DateChangeListener;
import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.fragment.DatePickerFragment;

import java.util.Calendar;

public class AddInvestMentActivity extends BaseActivity implements View.OnClickListener, DateChangeListener {

    private TextView tvSave;
    private boolean isBuy = true;
    private EditText edtEnterQuantity, tvCurrentPriceValue;
    private int rate;
    private TextView tvTradingDateValue;
    private int amount;
    private CardView cvInvestmentDate;
    private Calendar calendar;
    private int year,month,date;
    private String currentTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invest_ment);
        setToolBar();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.add_investment_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            tvSave = (TextView) toolbar.findViewById(R.id.tv_save);
            tvSave.setOnClickListener(this);
        }
    }

    @Override
    public void getId() {
        edtEnterQuantity = (EditText) findViewById(R.id.edt_enter_quantity_value);
        tvCurrentPriceValue = (EditText) findViewById(R.id.tv_current_price_value);
        tvTradingDateValue = (TextView) findViewById(R.id.tv_trading_date_value);
        cvInvestmentDate = (CardView) findViewById(R.id.cv_investment_date);
    }

    @Override
    public void setData() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);
        currentTimeStamp = String.valueOf(calendar.getTimeInMillis());
        tvTradingDateValue.setText(new StringBuilder().append(date).append("/").append(month).append("/").append(year));
    }

    @Override
    public void setListener() {
        cvInvestmentDate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                break;
            case R.id.cv_investment_date:
                showDatePickerDialog();
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_buy:
                if (isChecked) {
                    isBuy = true;
                }
                break;
            case R.id.radio_sell:
                if (isChecked) {
                    isBuy = false;
                }
                break;
        }
    }

    private void getEnteredData(){
        rate = TextUtils.isEmpty(edtEnterQuantity.getText().toString()) ? 0 : Integer.parseInt(edtEnterQuantity.getText().toString());
        amount = TextUtils.isEmpty(tvCurrentPriceValue.getText().toString())? 0 :Integer.parseInt(tvCurrentPriceValue.getText().toString());

        int totalPrice = amount * rate ;

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void dateChanged(String timeStamp, int date, int month, int year) {
        System.out.println(" timeStamp "+ timeStamp+" date "+ date+" month "+ month+" year "+year);
        currentTimeStamp = timeStamp;
        tvTradingDateValue.setText(new StringBuilder().append(date).append("/").append(month).append("/").append(year));
    }

    private void saveInDB(int totalPrice, int rate, int amount){

    }
}
