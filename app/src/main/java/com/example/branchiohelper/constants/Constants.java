package com.example.branchiohelper.constants;

/* Created by Vikram on 18-01-2020. */

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MediaType;

public class Constants {
    public static final String BASE_URL = "https://api2.branch.io/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String MY_PREFS = "branch_helper_app";
    public static String ERROR = "Error Occurred!";
    public static int CAMERA_PERMISSION_CODE = 100;
    public static int CAMERA_REQUEST = 101;
    public static ArrayList<String> DEFAULT_KEYS = new ArrayList<>(Arrays.asList("branch_key","campaign","feature","channel","stage","tags"));
}
