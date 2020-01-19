package com.example.branchiohelper.constants;

/* Created by Vikram on 18-01-2020. */

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MediaType;

public class Constants {
    public static final String BASE_URL = "https://api2.branch.io/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String BRANCH_KEY = "key_live_agUyNsXpTCpC2scqzuq1FoijtugHpDsn";
    public static String BRANCH_SECRET = "secret_live_UHzaFNDG9g5BHEmjzcWAAYFd60fpctWV";
    public static String ERROR = "Error Occurred!";
    public static ArrayList<String> DEFAULT_KEYS = new ArrayList<>(Arrays.asList("branch_key","campaign","feature","channel","stage","tags"));
}
