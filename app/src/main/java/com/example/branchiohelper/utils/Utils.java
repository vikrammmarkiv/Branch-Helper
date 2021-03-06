package com.example.branchiohelper.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.branchiohelper.constants.Constants;
import com.example.branchiohelper.models.FormData;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
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

    public static HashMap<String,Object> prepareLinkData(ArrayList<FormData> formItems,
                                                         Boolean quickLinkEnabled, Boolean isUpdate){
        HashMap<String,Object> requestBody = new HashMap<>();
        HashMap<String,Object> requestLinkData = new HashMap<>();
        requestBody.put("branch_key",BRANCH_KEY);
        if (isUpdate)
            requestBody.put("branch_secret",BRANCH_SECRET);
        if (formItems!=null) {
            for (FormData data : formItems) {
                if (Constants.DEFAULT_KEYS.contains(data.getKey())) {
                    requestBody.put(data.getKey(), data.getValue());
                } else {
                    requestLinkData.put(data.getKey(), data.getValue());
                }
            }
            if (quickLinkEnabled) {
                requestBody.put("type", 1);
                requestLinkData.put("$marketing_title", "Branch Helper Link");
            }
            requestBody.put("data", requestLinkData);
        }
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

    public static ArrayList<String> scanImageForText(Bitmap scannedImage, Activity activity){

        TextRecognizer textRecognizer = new TextRecognizer.Builder(activity.getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
           Toast.makeText(activity,"Dependencies are not loaded yet...please try after few moment!!", Toast.LENGTH_SHORT).show();
            Log.d("Branch","Dependencies are downloading....try after few moment");
            return null;
        }


        Frame imageFrame = new Frame.Builder()
                .setBitmap(scannedImage)
                .build();

        String imageText = "";
        ArrayList<String> imageTextList = new ArrayList<>();


        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            imageText = textBlock.getValue();
            if (imageText.contains("key")||imageText.contains("secret"))
                imageTextList.add(imageText);
            Log.d("Branch Image: ",imageText);
        }
        return imageTextList;
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

    public static ArrayList<String[]> convertLinkData(JsonElement data){
        ArrayList<String[]> linkData = new ArrayList<>();
        HashMap<String,String> convertedData = new Gson().fromJson(data.getAsString(), new TypeToken<HashMap<String, String>>(){}.getType());
        linkData.add((String[]) convertedData.keySet().toArray());
        linkData.add((String[]) convertedData.values().toArray());
        return linkData;
    }

    public static String getFilePath() {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/branchHelper/";
        File file = new File(rootPath);
        if (!file.exists())
            file.mkdirs();
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
    }


    public static String scanQRCodeForText(Bitmap scannedImage, Activity activity) {
        BarcodeDetector detector =
                new BarcodeDetector.Builder(activity.getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if (!detector.isOperational()) {
            Toast.makeText(activity, "Dependencies are not loaded yet...please try after few moment!!", Toast.LENGTH_SHORT).show();
            Log.d("Branch", "Dependencies are downloading....try after few moment");
            return null;
        }

        Frame frame = new Frame.Builder().setBitmap(scannedImage).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);
        Barcode thisCode = barcodes.valueAt(0);
        return thisCode.rawValue;
    }
}
