
import Entity.HttpMethod;
import Entity.Request;
import Entity.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

import static Entity.Response.*;
import static Entity.Response.internalErrorResponse;

@Slf4j
public class RequestHandler {
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final String webAppPath;


    public RequestHandler(BufferedReader reader, BufferedWriter writer, String webAppPath) {
        bufferedReader = reader;
        bufferedWriter = writer;
        this.webAppPath = webAppPath;
    }

    public void handle() throws IOException {
        log.info("handling request started");
        RequestParser requestParser = new RequestParser();
        Request request = requestParser.parseRequest(bufferedReader);
        log.info("request is parsed");

        Response response = processRequest(request);
        log.info("request is processed ");
        ResponseWriter responseWriter = new ResponseWriter();
        log.info("writing response");
        responseWriter.writeResponse(bufferedWriter, response);
        log.info("end of response");
    }

    public Response processRequest(Request request) {
        if (request == null || request.getHttpMethod() == null || request.getUri() == null) {
            return createBadRequestResponse();
        } else if (request.getHttpMethod() != HttpMethod.GET) {
            return createMethodNotAllowedResponse();
        }
        return readContent(request.getUri());
    }

    private Response readContent(String uri) {
        File file = new File(webAppPath + "/" + uri);
        if (!file.exists()) {
            log.info("There is nothing exists by path: " + uri);
            return createNotFoundResponse();
        }
        if (!file.isFile()) {
            log.info("There is no file by path: " + uri);
            return createBadRequestResponse();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return createOkResponse(reader);
        } catch (Exception e) {
            log.error("Exception occurred in readContent method, message: \n" + e.getMessage());
        }
        return internalErrorResponse();
    }

}
