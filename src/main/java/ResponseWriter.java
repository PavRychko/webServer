import Entity.Response;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ResponseWriter {
    private final String httpVersion = "HTTP/1.1";

    public void writeResponse(Writer writer, Response response) throws IOException {
        String responseHeaders = httpVersion + " " + response.getStatus().toString();
        writer.write(responseHeaders);
        Reader reader = response.getContent();
        if(reader != null) {
            reader.transferTo(writer);
        }

    }


}
