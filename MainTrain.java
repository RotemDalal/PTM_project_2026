package test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import test.RequestParser.RequestInfo;


public class MainTrain { // RequestParser
    

    private static void testParseRequest() {
        // Test data
        String request = "GET /api/resource?id=123&name=test HTTP/1.1\n" +
                            "Host: example.com\n" +
                            "Content-Length: 5\n"+
                            "\n" +
                            "filename=\"hello_world.txt\"\n"+
                            "\n" +
                            "hello world!\n"+
                            "\n" ;

        BufferedReader input=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));
        try {
            RequestParser.RequestInfo requestInfo = RequestParser.parseRequest(input);

            // Test HTTP command
            if (!requestInfo.getHttpCommand().equals("GET")) {
                System.out.println("HTTP command test failed (-5)");
            }

            // Test URI
            if (!requestInfo.getUri().equals("/api/resource?id=123&name=test")) {
                System.out.println("URI test failed (-5)");
            }

            // Test URI segments
            String[] expectedUriSegments = {"api", "resource"};
            if (!Arrays.equals(requestInfo.getUriSegments(), expectedUriSegments)) {
                System.out.println("URI segments test failed (-5)");
                for(String s : requestInfo.getUriSegments()){
                    System.out.println(s);
                }
            } 
            // Test parameters
            Map<String, String> expectedParams = new HashMap<>();
            expectedParams.put("id", "123");
            expectedParams.put("name", "test");
            expectedParams.put("filename","\"hello_world.txt\"");
            if (!requestInfo.getParameters().equals(expectedParams)) {
                System.out.println("Parameters test failed (-5)");
            }

            // Test content
            byte[] expectedContent = "hello world!\n".getBytes();
            if (!Arrays.equals(requestInfo.getContent(), expectedContent)) {
                System.out.println("Content test failed (-5)");
            } 
            input.close();
        } catch (IOException e) {
            System.out.println("Exception occurred during parsing: " + e.getMessage() + " (-5)");
        }        
    }


    public static void testServer() throws Exception {
        // 1. הגדרת פורט אקראי והפעלת השרת
        int port = 8080 + new java.util.Random().nextInt(100);
        MyHTTPServer server = new MyHTTPServer(port, 3);

        // 2. הוספת Servlet למפה של השרת
        server.addServlet("GET", "/check", new Servlet() {
            @Override
            public void handle(RequestParser.RequestInfo ri, OutputStream toClient) throws IOException {
                PrintWriter out = new PrintWriter(toClient);
                out.println("HTTP/1.1 200 OK");
                out.println();
                out.print("working"); // התשובה שהלקוח יחפש
                out.flush();
            }
            @Override
            public void close() throws IOException {}
        });

        server.start(); // הפעלת הלולאה ב-Thread נפרד
        Thread.sleep(500); // זמן קצר לשרת לעלות

        // 3. יצירת לקוח שמתחבר לשרת
        try (Socket client = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            // שליחת בקשת HTTP תקנית
            out.println("GET /check HTTP/1.1");
            out.println("Host: localhost");
            out.println();

            // 4. בדיקה אם קיבלנו "working" מהשרת
            String line;
            boolean success = false;
            while ((line = in.readLine()) != null) {
                if (line.contains("working")) {
                    success = true;
                    break;
                }
            }

            if (success) {
                System.out.println("Server test passed! (60 points)");
            } else {
                System.out.println("Server test failed: response not received (-60)");
            }
        } finally {
            server.close(); // סגירת השרת והת'רד
        }
    }
    public static void main(String[] args) {
        testParseRequest(); // 40 points
        try{
            testServer(); // 60
        }catch(Exception e){
            System.out.println("your server throwed an exception (-60)");
        }
        System.out.println("done");
    }

}
