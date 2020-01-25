package com.example.branchiohelper.events;

/* Created by Vikram on 18-01-2020. */



public class LinkReadEvent {
    private final String mResponse;
    public LinkReadEvent(String response) { mResponse = response; }
    public String getResponse() { return mResponse; }
}
