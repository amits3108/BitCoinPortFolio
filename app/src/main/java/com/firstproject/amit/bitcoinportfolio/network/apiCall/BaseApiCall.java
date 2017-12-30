package com.firstproject.amit.bitcoinportfolio.network.apiCall;


import android.content.Context;
import android.util.Log;


import com.firstproject.amit.bitcoinportfolio.network.api.ApiCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public abstract class BaseApiCall extends ApiCall {
    private String errorCode;
    private String errorDesc;

    protected abstract String getRequestUrl();

    public BaseApiCall() {
        super();
    }

    public BaseApiCall(Context context) {
        super();
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.toString();
    }


    @Override
    public String getServiceURL() {
        System.out.println("  URL :- " + getRequestUrl());
        return getRequestUrl();
    }

    @Override
    public void populateFromResponse(Object response) throws JSONException {
        parseResponseCode(response);
    }

    public void parseResponseCode(Object response) throws JSONException {
        if (response instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) response;
            errorCode = jsonObject.optString("error_code");
            errorDesc = jsonObject.optString("error_msg");
        }
    }

    @Override
    public String getErrorCode() {
        if (errorCode.length() == 0) {
            return null;
        } else if (errorCode.equals("400")) {
            return null;
        } else {
            return errorCode;
        }
    }

    @Override
    public String getErrorDesc() {
        return errorDesc;
    }
}
