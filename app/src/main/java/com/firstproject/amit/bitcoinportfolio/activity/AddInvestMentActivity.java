package com.firstproject.amit.bitcoinportfolio.activity;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firstproject.amit.bitcoinportfolio.interfaces.DateChangeListener;
import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.controller.DbController;
import com.firstproject.amit.bitcoinportfolio.fragment.DatePickerFragment;
import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;
import com.firstproject.amit.bitcoinportfolio.utils.Constant;

import java.util.Calendar;

public class AddInvestMentActivity extends BaseActivity implements View.OnClickListener, DateChangeListener {

    private TextView tvSave;
    private boolean isBuy = true;
    private EditText edtEnterQuantity, tvCurrentPriceValue;
    private int rate, amount, totalPrice;
    private TextView tvTradingDateValue, tvTotalPriceValue;
    private CardView cvInvestmentDate;
    private Calendar calendar;
    private int year, month, date;
    private String currentTimeStamp, dateString;

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
            toolbar.setNavigationIcon(R.drawable.ic_back);
            tvSave = (TextView) toolbar.findViewById(R.id.tv_save);
            tvSave.setOnClickListener(this);
        }
    }

    @Override
    public void getId() {
        edtEnterQuantity = (EditText) findViewById(R.id.edt_enter_quantity_value);
        tvCurrentPriceValue = (EditText) findViewById(R.id.tv_current_price_value);
        tvTradingDateValue = (TextView) findViewById(R.id.tv_trading_date_value);
        tvTotalPriceValue = (TextView) findViewById(R.id.tv_total_value_price);
        cvInvestmentDate = (CardView) findViewById(R.id.cv_investment_date);
    }

    @Override
    public void setData() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);
        currentTimeStamp = String.valueOf(calendar.getTimeInMillis());
        dateString = new StringBuilder().append(date).append("/").append(month).append("/").append(year).toString();
        tvTradingDateValue.setText(dateString);
    }

    @Override
    public void setListener() {
        cvInvestmentDate.setOnClickListener(this);
//        edtEnterQuantity.setOnEditorActionListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                getEnteredData();
                saveInDB(totalPrice, rate, amount);
                setResult(Constant.RESULT_CODE_FROM_ADD_INVESMENT_DONE);
                finish();
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

    private void getEnteredData() {
        rate = TextUtils.isEmpty(edtEnterQuantity.getText().toString()) ? 0 : Integer.parseInt(edtEnterQuantity.getText().toString());
        amount = TextUtils.isEmpty(tvCurrentPriceValue.getText().toString()) ? 0 : Integer.parseInt(tvCurrentPriceValue.getText().toString());

        totalPrice = amount * rate;

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void dateChanged(String timeStamp, int date, int month, int year) {
        System.out.println(" timeStamp " + timeStamp + " date " + date + " month " + month + " year " + year);
        currentTimeStamp = timeStamp;
        dateString = new StringBuilder().append(date).append("/").append(month).append("/").append(year).toString();
        tvTradingDateValue.setText(dateString);
    }

    private void saveInDB(int totalPrice, int rate, int amount) {
        InvestmentModel investmentModel = new InvestmentModel();
        investmentModel.setTimeStamp(dateString);
        investmentModel.setBuy(isBuy);
        investmentModel.setAmount(amount);
        investmentModel.setRate(rate);
        investmentModel.setTotalPrice(totalPrice);

        DbController dbController = DbController.getInstance();
        dbController.saveInvestmentsDetails(AddInvestMentActivity.this, investmentModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
