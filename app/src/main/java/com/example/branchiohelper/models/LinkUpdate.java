package com.example.branchiohelper.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class LinkUpdate {
    @SerializedName("branch_key")
    String branchKey;
    @SerializedName("~channel")
    String channel;
    @SerializedName("~feature")
    String feature;
    @SerializedName("~campaign")
    String campaign;
    @SerializedName("data")
    HashMap<String,String> data;


}
