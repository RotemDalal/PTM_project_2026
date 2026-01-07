package test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MyHTTPServer extends Thread implements HTTPServer {
    private final int port;
    private final ExecutorService threadPool;
    private final Map<String, Map<String, Servlet>> servlets;
    private volatile boolean stop = false;
    private ServerSocket serverSocket;

    public MyHTTPServer(int port, int nThreads) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(nThreads);
        this.servlets = new ConcurrentHashMap<>();
        this.servlets.put("GET", new ConcurrentHashMap<>());
        this.servlets.put("POST", new ConcurrentHashMap<>());
        this.servlets.put("DELETE", new ConcurrentHashMap<>());
    }

    public void addServlet(String command, String uri, Servlet s) {
        servlets.get(command.toUpperCase()).put(uri, s);
    }

    public void removeServlet(String command, String uri) {
        servlets.get(command.toUpperCase()).remove(uri);
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000); // מאפשר בדיקה של flag ה-stop כל שנייה
            while (!stop) {
                try {
                    Socket client = serverSocket.accept();
                    threadPool.execute(() -> handleClient(client));
                } catch (SocketTimeoutException e) { /* Timeout תקין, חוזר לבדוק stop */ }
            }
        } catch (IOException e) {
            if (!stop) e.printStackTrace();
        }
    }

    private void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             OutputStream out = client.getOutputStream()) {

            RequestParser.RequestInfo ri = RequestParser.parseRequest(in);
            if (ri == null) return;

            // Longest Prefix Match
            Servlet bestMatch = null;
            String bestUri = "";
            Map<String, Servlet> methodMap = servlets.get(ri.getHttpCommand().toUpperCase());

            if (methodMap != null) {
                for (String uri : methodMap.keySet()) {
                    if (ri.getUri().startsWith(uri) && uri.length() > bestUri.length()) {
                        bestUri = uri;
                        bestMatch = methodMap.get(uri);
                    }
                }
            }

            if (bestMatch != null) {
                bestMatch.handle(ri, out);
            }
        } catch (IOException e) {
            // טיפול בשגיאת תקשורת
        } finally {
            try { client.close(); } catch (IOException e) {}
        }
    }

    public void close() {
        stop = true;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {}
        threadPool.shutdownNow(); // סגירה מיידית של כל הטיפולים הפתוחים
    }
}