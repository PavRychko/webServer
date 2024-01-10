import Entity.HttpMethod;
import Entity.HttpStatus;
import Entity.Request;
import Entity.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {
    private final String webAppPath = "src/test/resources";
    private final String requestURI = "index.html";
    private final BufferedReader reader = Mockito.mock(BufferedReader.class);
    private final BufferedWriter writer = Mockito.mock(BufferedWriter.class);
    private final RequestHandler requestHandler = new RequestHandler(reader, writer, webAppPath);

    @Test
    public void processGetRequestTest() throws IOException {
        //given
        String expected = "test string";
        Request request = new Request();
        request.setHttpMethod(HttpMethod.GET);
        request.setUri(requestURI);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertNotNull(response.getContent());
        String actual = response.getContent().readLine();
        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void processGetRequestWithWrongUri() {
        //given
        String uri = "wrongIndex.html";
        Request request = new Request();
        request.setHttpMethod(HttpMethod.GET);
        request.setUri(uri);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processGetRequestWithNotAFileUri() {
        //given
        String uri = "index";
        Request request = new Request();
        request.setHttpMethod(HttpMethod.GET);
        request.setUri(uri);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processGetRequestWithExceptionWhileReadingUri() {
        //given
        Request request = new Request();
        request.setHttpMethod(HttpMethod.GET);
        request.setUri(requestURI);
        Response mock = Mockito.mock(Response.class);
        Mockito.when(Response.createOkResponse(reader)).thenThrow(new RuntimeException("test"));

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processRequestWithNullRequest() {
        //do
        Response response = requestHandler.processRequest(null);

        //verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processRequestWithNullRequestMethod() {
        //given
        Request request = new Request();
        request.setHttpMethod(null);
        request.setUri(requestURI);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processRequestWithNullRequestUri() {
        //given
        Request request = new Request();
        request.setHttpMethod(HttpMethod.GET);
        request.setUri(null);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processRequestWithPostRequestMethod() {
        //given
        Request request = new Request();
        request.setHttpMethod(HttpMethod.POST);
        request.setUri(requestURI);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatus());
        assertNull(response.getContent());
    }

    @Test
    public void processRequestWithDeleteRequestMethod() {
        //given
        Request request = new Request();
        request.setHttpMethod(HttpMethod.DELETE);
        request.setUri(requestURI);

        //do
        Response response = requestHandler.processRequest(request);

        //verify
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatus());
        assertNull(response.getContent());
    }
}