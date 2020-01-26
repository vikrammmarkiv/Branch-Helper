package com.example.branchiohelper.events;

/* Created by Vikram on 18-01-2020. */



public class LinkDeleteEvent {
    private final String mResponse;
    public LinkDeleteEvent(String response) { mResponse = response; }
    public String getResponse() { return mResponse; }
}
