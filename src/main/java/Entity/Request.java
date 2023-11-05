package Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Request {
    private HttpMethod httpMethod;
    private String uri;
    private Map<String, String> headers;
    private String body;
}
