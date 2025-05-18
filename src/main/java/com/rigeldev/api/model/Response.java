package com.rigeldev.api.model;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Response {
    private final PrintWriter writer;

    public Response(OutputStream outputStream) {
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), false);
    }

    public void send(int statusCode, String statusText, String contentType, String body) {
        writer.print("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
        writer.print("Content-Type: " + contentType + "\r\n");
        writer.print("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
        writer.print("Connection: close\r\n");
        writer.print("\r\n");
        writer.print(body);
        writer.flush();
    }
}