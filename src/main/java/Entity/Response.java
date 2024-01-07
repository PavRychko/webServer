package Entity;

import lombok.Getter;


import java.io.BufferedReader;

@Getter
public class Response {
    private final HttpStatus status;
    private BufferedReader content;

    public Response(HttpStatus status) {
        this.status = status;
    }

    public Response(HttpStatus status, BufferedReader content) {
        this.status = status;
        this.content = content;
    }


    public static Response internalErrorResponse() {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Response createNotFoundResponse() {
        return new Response(HttpStatus.NOT_FOUND);
    }

    public static Response createBadRequestResponse() {
        return new Response(HttpStatus.BAD_REQUEST);
    }

    public static Response createOkResponse(BufferedReader reader) {
        return new Response(HttpStatus.OK, reader);
    }
}


