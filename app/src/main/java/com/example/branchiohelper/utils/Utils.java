package com.example.branchiohelper.utils;

import com.example.branchiohelper.constants.Constants;
import com.example.branchiohelper.models.FormData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Response;

public class Utils {
    public static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    public static HashMap<String,Object> prepareDataForLinkCreate(ArrayList<FormData> formItems,
                                                                  Boolean quickLinkEnabled){
        HashMap<String,Object> requestBody = new HashMap<>();
        HashMap<String,Object> requestLinkData = new HashMap<>();
        requestBody.put("branch_key",Constants.BRANCH_KEY);
        for (FormData data:formItems){
            if (Constants.DEFAULT_KEYS.contains(data.getKey())){
                requestBody.put(data.getKey(),data.getValue());
            }
            else {
                requestLinkData.put(data.getKey(),data.getValue());
            }
        }
        if (quickLinkEnabled){
            requestBody.put("type",1);
            requestLinkData.put("$marketing_title","Branch Helper Link");
        }
        requestBody.put("data",requestLinkData);
        return requestBody;
    }
}
