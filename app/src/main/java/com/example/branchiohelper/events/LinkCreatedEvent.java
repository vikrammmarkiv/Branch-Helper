package com.example.branchiohelper.events;

/* Created by Vikram on 18-01-2020. */

import com.example.branchiohelper.models.LinkCreateResponse;

public class LinkCreatedEvent {
    private final LinkCreateResponse mResponse;
    public LinkCreatedEvent(LinkCreateResponse response) { mResponse = response; }
    public LinkCreateResponse getResponse() { return mResponse; }
}
