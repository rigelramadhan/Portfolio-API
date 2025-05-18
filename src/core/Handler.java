package core;

import model.Request;
import model.Response;

@FunctionalInterface
public interface Handler {
    void handle(Request request, Response response);
}