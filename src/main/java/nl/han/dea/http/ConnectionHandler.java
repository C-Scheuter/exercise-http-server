package nl.han.dea.http;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ConnectionHandler {
    private HtmlPageReader htmlPageReader = new HtmlPageReader();
    ZonedDateTime currentTime = ZonedDateTime.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);


    private final String HTTP_HEADERS = "HTTP/1.1 200 OK\n" +
            "Date:" + dateFormatter + "\n" +
            "HttpServer: Simple DEA Webserver\n" +
            "Content-Length:" + htmlPageReader.setLength("index.html") + "\n" +
            "Content-Type: text/html\n";
    //    private static final String HTTP_BODY = "<!DOCTYPE html>\n" +
//            "<html lang=\"en\">\n" +
//            "<head>\n" +
//            "<meta charset=\"UTF-8\">\n" +
//            "<title>Simple Http Server</title>\n" +
//            "</head>\n" +
//            "<body>\n" +
//            "<h1>Hi DEA folks!</h1>\n" +
//            "<p>This is a simple line in html.</p>\n" +
//            "</body>\n" +
//            "</html>\n" +
//            "\n";
    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        handle();
    }

    public void handle() {
        try {
            var inputStreamReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
            var outputStreamWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.US_ASCII));

            parseRequest(inputStreamReader);
            writeResponse(outputStreamWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseRequest(BufferedReader inputStreamReader) throws IOException {
        var request = inputStreamReader.readLine();

        while (request != null && !request.isEmpty()) {
            System.out.println(request);
            request = inputStreamReader.readLine();
        }
    }

    private void writeResponse(BufferedWriter outputStreamWriter) {
        try {
            outputStreamWriter.write(HTTP_HEADERS);
            outputStreamWriter.newLine();
            outputStreamWriter.write(htmlPageReader.readFile("index.html"));
            outputStreamWriter.newLine();
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
