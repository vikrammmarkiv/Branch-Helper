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
    public static ArrayList<String> DEFAULT_KEYS = new ArrayList<>(Arrays.asList("branch_key","campaign","feature","channel","stage","tags"));
    public static String[] BRANCH_STANDARD_PARAMS = {"channel", "feature", "campaign", "stage", "tags", "type", "$click_install_window_days", "$click_session_start_window_days", "$click_conversion_window_days", "$impression_install_window_days", "$impression_session_start_window_days", "$impression_conversion_window_days", "$fallback_url", "$fallback_url_xx", "$desktop_url", "$ios_url", "$ipad_url", "$android_url", "$samsung_url", "$windows_phone_url", "$blackberry_url", "$fire_url", "$ios_wechat_url", "$android_wechat_url", "$web_only", "$uri_redirect_mode", "$deeplink_path", "$android_deeplink_path", "$ios_deeplink_path", "$desktop_deeplink_path", "$match_duration", "$always_deeplink", "$ios_redirect_timeout", "$android_redirect_timeout", "$custom_sms_text", "$marketing_title", "$deeplink_no_attribution", "$ios_deepview", "$android_deepview", "$desktop_deepview", "$og_title", "$og_description", "$og_image_url", "$og_image_width", "$og_image_height", "$og_video", "$og_url", "$og_type", "$og_redirect", "$og_app_id"};
}
