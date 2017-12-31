package com.firstproject.amit.bitcoinportfolio.controller;

import android.content.Context;

import com.firstproject.amit.bitcoinportfolio.database.DbManager;
import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DbController {

    private static DbController instance = null;
    private DbManager db = null;

    public static DbController getInstance() {
        if (instance == null) {
            instance = new DbController();
        }
        return instance;
    }

    public DbManager getInstanceDb(Context con) {
        if (db == null) {
            db = new DbManager(con);
            db.open();
        }

        if (db != null && !db.isDbOpen())
            db.open();

        return db;
    }

    public void clearDatabase(Context context) {
        DbManager dbManager = getInstanceDb(context);
        dbManager.clearAllDb();
    }

    public void saveInvestmentsDetails(Context context, InvestmentModel investmentModel) {
        DbManager dbManager = getInstanceDb(context);
        dbManager.saveInvestmentsDetails(investmentModel);
    }

    public ArrayList<InvestmentModel> getAllInvestMents(Context context) {
        DbManager dbManager = getInstanceDb(context);
        return dbManager.getInvestMentList();
    }

    public int calculateTotalInvestment(Context context) {
        DbManager dbManager = getInstanceDb(context);
        return dbManager.calculateTotalInvestment();
    }
}
