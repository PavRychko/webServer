import Entity.HttpStatus;
import Entity.Request;
import Entity.Response;
import lombok.AllArgsConstructor;

import java.io.*;

@AllArgsConstructor
public class RequestProcessor {
    private String webAppPath;

    public Response processRequest(Request request) {
        File file = new File(webAppPath + "/" + request.getUri());
      return switch (request.getHttpMethod()) {
            case GET -> readContent(file);
            case POST -> updateContent(file, request.getBody());
            case DELETE -> deleteContent(file);
        };

    }

    private Response readContent(File file) {
        if (!file.exists()) {
            return createNotFoundResponse();
        }
        if (!file.isFile()) {
            return createBadRequestResponse();
        }
        try {
            Reader reader = new FileReader(file);
            return createOkResponse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return internalErrorResponse();
    }

    //TODO: write update and delete properly
    private Response updateContent(File file, String body) {
        try  {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    private Response deleteContent(File file) {
        return null;
    }

    private Response internalErrorResponse() {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Response createNotFoundResponse() {
        return new Response(HttpStatus.NOT_FOUND);
    }

    private Response createBadRequestResponse() {
        return new Response(HttpStatus.BAD_REQUEST);
    }

    private Response createOkResponse(Reader reader) {
         return new Response(HttpStatus.OK, reader);
    }

}
