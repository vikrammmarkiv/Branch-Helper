package com.example.branchiohelper.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class LinkCreate {
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


    public LinkCreate(String branchKey, String channel, String feature, String campaign, HashMap<String, String> data) {
        this.branchKey = branchKey;
        this.channel = channel;
        this.feature = feature;
        this.campaign = campaign;
        this.data = data;
    }

    public LinkCreate(){

    }

    public String getBranchKey() {
        return branchKey;
    }

    public void setBranchKey(String branchKey) {
        this.branchKey = branchKey;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
