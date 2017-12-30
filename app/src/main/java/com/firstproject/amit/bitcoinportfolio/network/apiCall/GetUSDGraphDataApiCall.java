package com.firstproject.amit.bitcoinportfolio.network.apiCall;

import android.content.Context;

import com.firstproject.amit.bitcoinportfolio.model.GraphDetailModel;
import com.firstproject.amit.bitcoinportfolio.network.UrlRequests;
import com.firstproject.amit.bitcoinportfolio.network.api.ApiCall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by vishambar on 10/17/2016.
 */

public class GetUSDGraphDataApiCall extends BaseApiCall {

    private String errorCode;
    private String userId;
    private Object resultObj;

    public GetUSDGraphDataApiCall(Context context) {
        super(context);
    }

    @Override
    protected String getRequestUrl() {
        return UrlRequests.USD_GRAPH_DETAILS;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.JSON_OBJECT_REQUEST;
    }

    /**
     * Method is used to receive the response
     *
     * @param response
     * @throws JSONException
     */

    public void populateFromResponse(Object response) throws JSONException {
        System.out.println("  jsonResponse GetUSDGraphDataApiCall  :- " + response);
        if (response instanceof JSONObject) {
            JSONObject json = (JSONObject) response;
            parseResponseCode(response);
            Type type = new TypeToken<GraphDetailModel>(){}.getType();
            resultObj = new Gson().fromJson(response.toString(),type);
        }
    }
//
//    private void parseResponse(JSONObject object) throws JSONException {
////        String message  = object.optString("about");
//        resultObj = object;
//
//    }


    //    @Override
    public Object getRequest() {
        JSONObject postObject = null;
        //            Constants.setUrl(Constants.REQUEST_TYPE_ABOUT_US);
//        postObject = new JSONObject();
        System.out.println("  jsonRequest GetUSDGraphDataApiCall :- " + postObject);
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
