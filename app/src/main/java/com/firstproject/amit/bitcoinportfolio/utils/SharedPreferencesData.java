package com.firstproject.amit.bitcoinportfolio.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.firstproject.amit.bitcoinportfolio.model.UsdToBtcModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by amit on 31-12-2017.
 */

public class SharedPreferencesData {
    private static final String SHARED_PREF_USD_TO_BTC = "SHARED_PREF_USD_TO_BTC";

    private static final String GSON_USD_TO_BTC = "GSON_USD_TO_BTC";
    private static final String SYMBOL_NAME = "SYMBOL_NAME";
    private static final String PRICE_IN_USD = "PRICE_IN_USD";
    private static final String PRICE_IN_BTC = "PRICE_IN_BTC";


//    if(usdToBtcModel.getName().equalsIgnoreCase("Bitcoin")) {
//        tvUsdBtc.setText("USD / "+usdToBtcModel.getSymbol().toUpperCase());
//        tvUsdBtcValue.setText("$" + usdToBtcModel.getPrice_usd() + "/" + usdToBtcModel.getPrice_btc());
//    }


    public static void setSharedPrefUsdToBtc(Context context, UsdToBtcModel usdToBtcModel) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_USD_TO_BTC, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(SYMBOL_NAME,usdToBtcModel.getSymbol());
//        editor.putString(PRICE_IN_USD, String.valueOf(usdToBtcModel.getPrice_usd()));
//        editor.putString(PRICE_IN_BTC, String.valueOf(usdToBtcModel.getPrice_btc()));
        Gson gson = new Gson();
        String json = gson.toJson(usdToBtcModel);
        editor.putString(GSON_USD_TO_BTC, json);

        editor.commit();
    }

    public static UsdToBtcModel getSharedPrefUsdToBtcModel(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USD_TO_BTC, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(GSON_USD_TO_BTC, "");
        Type type = new TypeToken<UsdToBtcModel>() {
        }.getType();
        UsdToBtcModel usdToBtcModel = gson.fromJson(json, type);

        return usdToBtcModel;
    }

}
