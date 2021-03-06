/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * A request for retrieving a {@link JSONObject} response body at a given URL, allowing for an
 * optional {@link JSONObject} to be passed in as part of the request body.
 */
public class JsonObjectGzipRequest extends JsonRequest<JSONObject> {

    /**
     * Creates a new request.
     * @param method the HTTP method to use
     * @param url URL to fetch the JSON from
     * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and
     *   indicates no parameters will be posted along with request.
     * @param listener Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JsonObjectGzipRequest(int method, String url, JSONObject jsonRequest,
                                 Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                    errorListener);
    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is
     * <code>null</code>, <code>POST</code> otherwise.
     *
     * @see #JsonObjectGzipRequest(int, String, JSONObject, Listener, ErrorListener)
     */
    public JsonObjectGzipRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener,
                                 ErrorListener errorListener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
                listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String output = "";
        try {
            //GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
            InflaterInputStream gStream = new InflaterInputStream(new ByteArrayInputStream(response.data));
            InputStreamReader reader = new InputStreamReader(gStream);
            BufferedReader in = new BufferedReader(reader);
            String read;
            while ((read = in.readLine()) != null) {
                output += read;
            }
            reader.close();
            in.close();
            gStream.close();
        } catch (IOException e) {
            // return Response.error(new ParseError());
            //return Response.error(new ParseError());
            try {
                output = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e1) {
                return Response.error(new ParseError(e1));
            }
        }

        try {
            /*String jsonString =
                new String(response.data, HttpHeaderParser.parseCharset(response.headers));*/
            return Response.success(new JSONObject(output),
                    HttpHeaderParser.parseCacheHeaders(response));
        } /*catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } */catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
