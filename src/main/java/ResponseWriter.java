import Entity.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class ResponseWriter {
    private static final String httpVersion = "HTTP/1.1";

    public void writeResponse(BufferedWriter writer, Response response) throws IOException {
        String statusLine = httpVersion + " " + response.getStatus().toString();
        writer.write(statusLine);
        BufferedReader reader = response.getContent();
        if (reader != null) {
            writer.newLine();
            writer.newLine();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                writer.write(currentLine);
            }
        }

    }


}
