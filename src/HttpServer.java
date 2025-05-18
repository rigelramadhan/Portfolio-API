import com.google.gson.Gson;
import model.Request;
import model.Response;
import core.Router;
import model.ApiResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {
        int port = 8000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at http://localhost:" + port);
            Router router = new Router();
            registerRouter(router, "GET", "/hello", new ApiResponse("success", "Hello World! Successfully called! :D"));

            registerRouter(router, "GET", "/test", new ApiResponse("success", "This is a /test endpoint."));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket, router)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket, Router router) {
        try (clientSocket;
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()
        ) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isBlank()) return;

            System.out.println("model.Request: " + requestLine);
            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;

            String method = parts[0];
            String path = parts[1];

            Request request = new Request(method, path);
            Response response = new Response(out);

            router.handle(request, response);
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    public static void registerRouter(Router router, String method, String path, Object body) {
        router.register(method, path, (req, res) -> {
            res.send(200, "OK", "application/json", new Gson().toJson(body));
        });
    }
}