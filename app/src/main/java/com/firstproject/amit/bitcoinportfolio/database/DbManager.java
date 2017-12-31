package com.firstproject.amit.bitcoinportfolio.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;

import java.util.ArrayList;


public class DbManager {
    private DbOpenHelper sqliteHelperImage;
    private SQLiteDatabase sqliteDataBase = null;

    public DbManager(Context mContext) {
        sqliteHelperImage = new DbOpenHelper(mContext);
    }

    public boolean isDbOpen() {
        if (sqliteDataBase != null && sqliteDataBase.isOpen())
            return true;
        else
            return false;
    }

    public void open() throws SQLException {
        try {
            try {
                sqliteDataBase = sqliteHelperImage.getWritableDatabase();
            } catch (Exception e) {
                sqliteDataBase = sqliteHelperImage.getReadableDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            sqliteHelperImage.close();
        } catch (Exception e) {


            e.printStackTrace();
        }
    }

    public void clearAllDb() {
        try {
            sqliteDataBase.delete(DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS, null, null);
        } catch (Exception e) {
            System.out.print("DELETE DROP ANOMALY");
            e.printStackTrace();
        }
    }

    public long saveInvestmentsDetails(InvestmentModel investmentModel) {
        ContentValues values = new ContentValues();
        long insertId = 0;

        values.put(DbOpenHelper.COLUMN_INVESTMENT_AMOUNT, investmentModel.getAmount());
        values.put(DbOpenHelper.COLUMN_INVESTMENT_RATE, investmentModel.getRate());
        values.put(DbOpenHelper.COLUMN_INVESTMENT_TOTAL_PRICE, investmentModel.getTotalPrice());
        values.put(DbOpenHelper.COLUMN_INVESTMENT_IS_BUY, investmentModel.isBuy() ? 1 : 0);
        values.put(DbOpenHelper.COLUMN_INVESTMENT_TIMESTAMP, investmentModel.getTimeStamp());

        insertId = sqliteDataBase.insert(DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS, null, values);
        return insertId;
    }

    public ArrayList<InvestmentModel> getInvestMentList() {
        ArrayList<InvestmentModel> investmentModelArrayList = new ArrayList<>();
        Cursor cursor = sqliteDataBase.query(DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS, null, null, null, null, null, DbOpenHelper.COLUMN_INVESTMENT_TIMESTAMP + " desc ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InvestmentModel investmentModel = evaluateInvestMentModel(cursor);
            investmentModelArrayList.add(investmentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return investmentModelArrayList;
    }

    public InvestmentModel evaluateInvestMentModel(Cursor cursor) {
        InvestmentModel investmentModel = new InvestmentModel();
        try {
//            cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_ID));
            investmentModel.setAmount(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_AMOUNT)));
            investmentModel.setRate(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_RATE)));
            investmentModel.setTotalPrice(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_TOTAL_PRICE)));
            investmentModel.setBuy(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_IS_BUY)) != 0);
            investmentModel.setTimeStamp(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_TIMESTAMP)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return investmentModel;
    }

    public int calculateTotalInvestment() {
        int reminingTotalValue, BuyingValue = 0, SoldValue = 0;
        ArrayList<InvestmentModel> investmentModelArrayList = getInvestMentList();
        for (InvestmentModel investmentModel : investmentModelArrayList) {
            if (investmentModel.isBuy())
                BuyingValue = BuyingValue + investmentModel.getTotalPrice();

            if (!investmentModel.isBuy())
                SoldValue = SoldValue + investmentModel.getTotalPrice();
        }
        reminingTotalValue = BuyingValue - SoldValue;
        return reminingTotalValue;
    }

}
