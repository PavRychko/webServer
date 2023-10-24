import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RequestParser {

    public Request parseRequest(BufferedReader reader) {
        Request request = new Request();
        injectUriAndMethod(reader, request);
        injectHeaders(reader, request);
        injectBody(reader, request);
        return request;
    }

    private void injectUriAndMethod(BufferedReader reader, Request request) {
        try {
            String firstLine = reader.readLine();
            String[] split = firstLine.split(" ");
            if (split.length != 3) {
                throw new ParseException("request`s first line is not equals to 3 arguments: " + firstLine);
            }
            HttpMethod method = Arrays.stream(HttpMethod.values()).filter(m -> m.toString().equalsIgnoreCase(split[0])).findFirst().orElseThrow(IllegalArgumentException::new);
            request.setHttpMethod(method);
            request.setUri(split[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void injectHeaders(BufferedReader reader, Request request) {
        Map<String, String> headers = new HashMap<>();
        String currentLine;
        String[] splitted;
        try {
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(":")) {
                    splitted = currentLine.split(": ");
                    headers.put(splitted[0], splitted[1]);
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


    private void injectBody(BufferedReader reader, Request request) {
        try {
            if (reader.ready()) {
                StringBuilder buffer = new StringBuilder();
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    buffer.append(currentLine);
                }
                request.setBody(buffer.toString());
            } else {
                request.setBody("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

