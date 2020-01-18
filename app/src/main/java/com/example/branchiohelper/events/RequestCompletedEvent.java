package com.example.branchiohelper.events;

/* Created by Vikram on 18-01-2020. */

import com.example.branchiohelper.models.LinkCreateResponse;
import com.google.gson.JsonElement;

import okhttp3.Response;

public class RequestCompletedEvent {
    private JsonElement mResponse;
    public RequestCompletedEvent(JsonElement response) { mResponse = response; }
    public JsonElement getResponse() { return mResponse; }
}
