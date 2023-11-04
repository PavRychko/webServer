import lombok.AllArgsConstructor;

import java.io.*;

@AllArgsConstructor
public class ContentReader {
    private String webAppPath;


    public Response readContent(String uri) {
        try {
        File file = new File(webAppPath + "/" + uri);
        if (!file.exists()) {
            return createNotFoundResponse();
        }
        if (!file.isFile()) {
            return createBadRequestResponse(uri);
        }
            Reader reader = new FileReader(file);
            return createOkResponse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return internalErrorResponse();
    }

    private Response internalErrorResponse() {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Response createNotFoundResponse() {
        return new Response(HttpStatus.NOT_FOUND);
    }

    private Response createBadRequestResponse(String uri) {
        return new Response(HttpStatus.BAD_REQUEST);
    }

    private Response createOkResponse(Reader reader) {
         return new Response(HttpStatus.OK, reader);
    }

}
