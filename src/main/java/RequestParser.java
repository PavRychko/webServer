import Entity.HttpMethod;
import Entity.ParseException;
import Entity.Request;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;


public class RequestParser {

    public Request parseRequest(BufferedReader reader) {
        Request request = new Request();
        injectUriAndMethod(reader, request);
        injectHeaders(reader, request);
        return request;
    }

    private void injectUriAndMethod(BufferedReader reader, Request request) {
        try {
            String firstLine = reader.readLine();
            String[] split = firstLine.split(" ");
            if (split.length != 3) {
                throw new ParseException("request`s first line is not equals to 3 arguments: " + firstLine);
            }
            HttpMethod method = HttpMethod.getHttpMethod(split[0]);
            request.setHttpMethod(method);
            request.setUri(split[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void injectHeaders(BufferedReader reader, Request request) {
        Map<String, String> headers = new HashMap<>();
        String currentLine;
        String[] split;
        try {
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(":")) {
                    split = currentLine.split(": ");
                    headers.put(split[0], split[1]);
                } else if (currentLine.equals("") || currentLine.equals(" ")) {
                    break;
                } else {
                    throw new ParseException("while reading headers get line without ':' separator! Line = " + currentLine);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setHeaders(headers);
    }

}

