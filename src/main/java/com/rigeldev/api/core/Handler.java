package com.rigeldev.api.core;

import com.rigeldev.api.model.Request;
import com.rigeldev.api.model.Response;

@FunctionalInterface
public interface Handler {
    void handle(Request request, Response response);
}