package Entity;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,
    DELETE;


    public static HttpMethod getHttpMethod(String httpMethodString) {
        return Arrays.stream(HttpMethod.values()).filter(m -> m.toString().equalsIgnoreCase(httpMethodString)).findFirst().
                orElseThrow(() -> new IllegalArgumentException("Unknown HttpMethod " + httpMethodString));
    }
}
