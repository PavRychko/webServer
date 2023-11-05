package Entity;

import Entity.HttpStatus;
import lombok.Getter;


import java.io.Reader;

@Getter
public class Response {
    private HttpStatus status;
    private Reader content;

    public Response (HttpStatus status){
        this.status = status;
    }

    public Response(HttpStatus status, Reader content){
        this.status = status;
        this.content = content;
    }
}
