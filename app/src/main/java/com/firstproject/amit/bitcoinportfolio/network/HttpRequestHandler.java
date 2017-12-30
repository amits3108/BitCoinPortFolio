package com.firstproject.amit.bitcoinportfolio.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firstproject.amit.bitcoinportfolio.network.api.APIException;
import com.firstproject.amit.bitcoinportfolio.network.api.ApiCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import static com.firstproject.amit.bitcoinportfolio.network.api.ApiCall.RequestType.JSON_ARRAY_REQUEST;
import static com.firstproject.amit.bitcoinportfolio.network.api.ApiCall.RequestType.JSON_OBJECT_REQUEST;

public class HttpRequestHandler {
    public static final String DEFAULT_REQUEST_TAG = "";
    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10MB cap
    private static HttpRequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private Cache mCache;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    public static synchronized HttpRequestHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpRequestHandler(context);
        }
        return mInstance;
    }

    private HttpRequestHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public void executeRequest(final ApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener) {
        executeRequest(apiCall, onApiCallCompleteListener, true);
    }

    public void executeRequest(final ApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener, boolean shouldCache, JSONObject object) {

        if (object != null) {
            handleResponse(object, apiCall, onApiCallCompleteListener);
            return;
        }
        Request request = null;

        try {
            switch (apiCall.getRequestType()) {
                case JSON_ARRAY_REQUEST:
                    request = new JsonArrayRequest(apiCall.getServiceURL(), new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            if (apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
                                return super.getHeaders();
                            else
                                return apiCall.getHeaders();
                        }
                    };
                    break;

                case JSON_OBJECT_REQUEST:
                    request = new JsonObjectRequest(apiCall.getServiceURL(), (JSONObject) apiCall.getRequest(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            if (apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
                                return super.getHeaders();
                            else
                                return apiCall.getHeaders();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };
                    break;
            }

            if (request != null) {
                request = request.setShouldCache(shouldCache);
//                request = request.setUseOfflineCache(shouldCache);

                addToRequestQueue(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(e, onApiCallCompleteListener);
        }
    }

    public void executeRequest(final ApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener, boolean shouldCache) {
        Request request = null;

        try {
            switch (apiCall.getRequestType()) {
                case JSON_ARRAY_REQUEST:
                    request = new JsonArrayRequest(apiCall.getServiceURL(), new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            if (apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
                                return super.getHeaders();
                            else
                                return apiCall.getHeaders();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };
                    break;

                case JSON_OBJECT_REQUEST:
                    request = new JsonObjectRequest(apiCall.getServiceURL(), (JSONObject) apiCall.getRequest(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            if (apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
                                return super.getHeaders();
                            else
                                return apiCall.getHeaders();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };
                    break;
            }

            if (request != null) {
                request = request.setShouldCache(shouldCache);
//                request = request.setUseOfflineCache(shouldCache);

                addToRequestQueue(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(e, onApiCallCompleteListener);
        }
    }

    private void handleResponse(Object response, final ApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener) {
        if (response != null) {
            try {
                apiCall.populateFromResponse(response);

                if (apiCall.getErrorCode() != null /*|| (!apiCall.getErrorCode().equalsIgnoreCase(Constants.RESPONSE_CODES.successLogin )&& apiCall.getServiceURL().contains("apps")) ||((!apiCall.getErrorCode().equalsIgnoreCase(Constants.RESPONSE_CODES.successSignUp )&& apiCall.getServiceURL().contains("Signup")))*/) {
                    handleError(new APIException(apiCall.getErrorCode(), apiCall.getErrorDesc()), onApiCallCompleteListener);

                }
//                else if(apiCall.getResult() == null) {
//                    handleError(new NullPointerException("Response is null"), onApiCallCompleteListener);
//                }
                else if (onApiCallCompleteListener != null) {
                    onApiCallCompleteListener.onComplete(null);
                }
            } catch (Exception e) {
                handleError(e, onApiCallCompleteListener);
            }
        } else {
            handleError(new NullPointerException("Response is null"), onApiCallCompleteListener);
        }
    }

    private void handleError(Exception error, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener) {
        if (onApiCallCompleteListener != null) {
            if (error instanceof VolleyError) {
                VolleyError volleyError = (VolleyError) error;
                if (volleyError.networkResponse != null
                        && (volleyError.networkResponse.statusCode < 200 || volleyError.networkResponse.statusCode > 200)) {

                    // Response Body
                    String responseBody = null;
                    if (volleyError.networkResponse.data != null) {
                        responseBody = new String(volleyError.networkResponse.data);
//                        L.e("responseBody= " + responseBody);
                    }

                    // Complete callback with an ApiException with Response Body
                    APIException apiException = new APIException("" + volleyError.networkResponse.statusCode, "", responseBody);
                    onApiCallCompleteListener.onComplete(apiException);
                    return;
                }
            }
            onApiCallCompleteListener.onComplete(error);
        }
    }

    public void cancelRequest(ApiCall apiCall) {
        cancelRequest(apiCall.getTag());
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Instantiate the cache
            mCache = new DiskBasedCache(mCtx.getCacheDir(), CACHE_SIZE);

            HttpStack stack;
            // If the device is running a version >= Gingerbread...
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            {
                // use HttpURLConnection for stack.
                stack = new HurlStack(mCtx);

            }
//            else {
//                // use AndroidHttpClient for stack.
//                String userAgent = "volley/0";
//                try {
//                    String packageName = mCtx.getPackageName();
//                    PackageInfo info = mCtx.getPackageManager().getPackageInfo(packageName, 0);
//                    userAgent = packageName + "/" + info.versionCode;
//                } catch (NameNotFoundException e) {
//                }
//                // Prior to Gingerbread, HttpUrlConnection was unreliable.
//                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
//                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
//            }

            Network network = new BasicNetwork(stack);

            // Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(mCache, network);

            // Start the queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(DEFAULT_REQUEST_TAG);
        getRequestQueue().add(req);
    }

    public void cancelRequest(Object tag) {
        if (tag != null) {
            getRequestQueue().cancelAll(tag);
        }
    }

    public void stop() {
        getRequestQueue().cancelAll(DEFAULT_REQUEST_TAG);
        getRequestQueue().stop();
        mInstance = null;
    }

    public void clearVolleyCache(Context context) {
        mCache.clear();
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
