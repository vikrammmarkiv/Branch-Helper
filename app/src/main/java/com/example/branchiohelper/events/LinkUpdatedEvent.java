package com.example.branchiohelper.events;

/* Created by Vikram on 18-01-2020. */

import com.example.branchiohelper.models.LinkCreateResponse;

public class LinkUpdatedEvent {
    private final String mResponse;
    public LinkUpdatedEvent(String response) { mResponse = response; }
    public String getResponse() { return mResponse; }
}
