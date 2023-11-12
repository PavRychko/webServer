
import Entity.Request;
import Entity.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class RequestHandler {
    private RequestProcessor contentProcessor;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public RequestHandler (BufferedReader reader, BufferedWriter writer, String webAppPath) {
        bufferedReader = reader;
        bufferedWriter = writer;
        contentProcessor = new RequestProcessor(webAppPath);
    }

    public void handle() throws IOException {
        RequestParser requestParser = new RequestParser();
        Request request = requestParser.parseRequest(bufferedReader);
        Response response = contentProcessor.processRequest(request);
        ResponseWriter responseWriter = new ResponseWriter();
        responseWriter.writeResponse(bufferedWriter, response);
    }

}
