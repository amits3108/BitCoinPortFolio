package com.firstproject.amit.bitcoinportfolio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firstproject.amit.bitcoinportfolio.R;
import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;

import java.util.ArrayList;

/**
 * Created by amit on 31-12-2017.
 */

public class InvestMentHistoryAdapter extends RecyclerView.Adapter<InvestMentHistoryAdapter.MyViewHolder> {
    private LayoutInflater inflator;
    private Context mContext;
    private ArrayList<InvestmentModel> investmentModelArrayList;

    public InvestMentHistoryAdapter(Context context, ArrayList<InvestmentModel> investmentModelArrayList) {
        this.mContext = context;
        inflator = LayoutInflater.from(context);
        this.investmentModelArrayList = investmentModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(inflator.inflate(R.layout.row_investment_history, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InvestmentModel investmentModel = investmentModelArrayList.get(position);
//        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (investmentModelArrayList.size() > 0 && holder != null) {
            holder.tvBuyOrSell.setText(investmentModel.isBuy() ? "Buy" : "Sell");
            holder.tvAmountValue.setText(String.valueOf(investmentModel.getAmount()));
            holder.tvRateValue.setText(String.valueOf(investmentModel.getRate()));
            holder.tvTotalValuePrice.setText(String.valueOf(investmentModel.getTotalPrice()));
            holder.tvTradingDate.setText(String.valueOf(investmentModel.getTimeStamp()));
        }
    }

    @Override
    public int getItemCount() {
        return investmentModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTradingDate, tvBuyOrSell, tvRateValue, tvAmountValue, tvTotalValuePrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTradingDate = (TextView) itemView.findViewById(R.id.tv_trade_date_value);
            tvBuyOrSell = (TextView) itemView.findViewById(R.id.tv_buy_Or_Sell);
            tvRateValue = (TextView) itemView.findViewById(R.id.rate_value);
            tvAmountValue = (TextView) itemView.findViewById(R.id.amount_value);
            tvTotalValuePrice = (TextView) itemView.findViewById(R.id.tv_total_value_price);
        }
    }
}
