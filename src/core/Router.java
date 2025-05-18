package core;

import model.Request;
import model.Response;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Handler> routes = new HashMap<>();

    public void register(String method, String path, Handler handler) {
        routes.put(method + " " + path, handler);
    }

    public void handle(Request request, Response response) {
        Handler handler = routes.get(request.method + " " + request.path);
        if (handler != null) {
            handler.handle(request, response);
        } else {
            response.send(404, "Not Found", "text/plain", "404 Not Found");
        }
    }
}