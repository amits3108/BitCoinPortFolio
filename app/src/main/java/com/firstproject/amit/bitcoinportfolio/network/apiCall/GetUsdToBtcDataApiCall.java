package com.firstproject.amit.bitcoinportfolio.network.apiCall;

import android.content.Context;
import android.content.SharedPreferences;

import com.firstproject.amit.bitcoinportfolio.model.GraphDetailModel;
import com.firstproject.amit.bitcoinportfolio.model.UsdToBtcModel;
import com.firstproject.amit.bitcoinportfolio.network.UrlRequests;
import com.firstproject.amit.bitcoinportfolio.utils.SharedPreferencesData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by vishambar on 10/17/2016.
 */

public class GetUsdToBtcDataApiCall extends BaseApiCall {

    private String errorCode;
    private String userId;
    private Object resultObj;
    private Context context;

    public GetUsdToBtcDataApiCall(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected String getRequestUrl() {
        return UrlRequests.TICKER_BIT_COIN;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.JSON_ARRAY_REQUEST;
    }

    /**
     * Method is used to receive the response
     *
     * @param response
     * @throws JSONException
     */

    public void populateFromResponse(Object response) throws JSONException {
        System.out.println("  jsonResponse GetUsdToBtcDataApiCall  :- " + response);
        if (response instanceof JSONArray) {
            JSONObject jsonObject = (JSONObject) ((JSONArray) response).get(0);
            Type type = new TypeToken<UsdToBtcModel>() {
            }.getType();
            resultObj = new Gson().fromJson(jsonObject.toString(), type);
        }
    }

    //    @Override
    public Object getRequest() {
        JSONObject postObject = null;
        System.out.println("  jsonRequest GetUsdToBtcDataApiCall :- " + postObject);
        return postObject;
    }

    /**
     * Method to return the result got from the API call
     *
     * @return result, if any problem occur then return null
     */

    public String getError() {
        return errorCode;
    }

    @Override
    public Object getResult() {
        return resultObj;
    }
}
