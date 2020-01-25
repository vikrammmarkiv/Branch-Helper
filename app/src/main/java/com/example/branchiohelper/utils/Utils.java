package com.example.branchiohelper.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.branchiohelper.constants.Constants;
import com.example.branchiohelper.models.FormData;
import com.google.android.material.snackbar.Snackbar;

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

    public static String BRANCH_KEY="";
    public static String BRANCH_SECRET="";

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
        requestBody.put("branch_key",BRANCH_KEY);
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

    public static void showSnackBar(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String formatJsonString(String text){

        StringBuilder json = new StringBuilder();
        String indentString = "\t\t";
        json.append("{\n");
        for (int i = 1; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }
}
