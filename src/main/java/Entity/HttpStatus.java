package Entity;

public enum HttpStatus {
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD REQUEST"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    NOT_FOUND(404, "NOT FOUND"),
    METHOD_NOT_ALLOWED(405, "METHOD NOT ALLOWED");

    HttpStatus(int statusCode, String message) {
    }
}
