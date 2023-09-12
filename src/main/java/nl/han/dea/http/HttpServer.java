package nl.han.dea.http;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer extends Thread {

    private int tcpPort;

    public HttpServer(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public static void main(String[] args) {
        new HttpServer(8383).startServer();
    }

    public void startServer() {
        try (
                var serverSocket = new ServerSocket(this.tcpPort);

        ) {
            while (true) {
                System.out.println("Server accepting requests on port " + tcpPort);
                var acceptedSocket = serverSocket.accept();
                Thread clientThread = new Thread(() -> {
                    var connectionHandler = new ConnectionHandler(acceptedSocket);
                    connectionHandler.handle();
                });
                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
