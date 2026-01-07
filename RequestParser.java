package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static RequestInfo parseRequest(BufferedReader reader) throws IOException {
        String firstLine = reader.readLine();
        if (firstLine == null || firstLine.isEmpty()) return null;


        String[] parts = firstLine.split(" ");
        String httpCommand = parts[0];
        String fullUri = parts[1];

        Map<String, String> parameters = new HashMap<>();
        String uriPath = fullUri;
        if (fullUri.contains("?")) {
            String[] uriParts = fullUri.split("\\?");
            uriPath = uriParts[0];
            parseParams(uriParts[1], parameters);
        }

        String[] uriSegments = Arrays.stream(uriPath.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {

        }


        while (reader.ready() && (line = reader.readLine()) != null && !line.isEmpty()) {
            parseParams(line, parameters);
        }

        
        StringBuilder contentBuilder = new StringBuilder();
        while (reader.ready() && (line = reader.readLine()) != null) {
            if (line.isEmpty()) break;
            contentBuilder.append(line).append("\n");
        }

        return new RequestInfo(httpCommand, fullUri, uriSegments, parameters, contentBuilder.toString().getBytes());
    }

    private static void parseParams(String paramString, Map<String, String> params) {
        for (String pair : paramString.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2) params.put(kv[0], kv[1]);
            else if (kv.length == 1) params.put(kv[0], "");
        }
    }

    public static class RequestInfo {
        private final String httpCommand, uri;
        private final String[] uriSegments;
        private final Map<String, String> parameters;
        private final byte[] content;

        public RequestInfo(String httpCommand, String uri, String[] uriSegments, Map<String, String> parameters, byte[] content) {
            this.httpCommand = httpCommand;
            this.uri = uri;
            this.uriSegments = uriSegments;
            this.parameters = parameters;
            this.content = content;
        }

        public String getHttpCommand() { return httpCommand; }
        public String getUri() { return uri; }
        public String[] getUriSegments() { return uriSegments; }
        public Map<String, String> getParameters() { return parameters; }
        public byte[] getContent() { return content; }
    }
}