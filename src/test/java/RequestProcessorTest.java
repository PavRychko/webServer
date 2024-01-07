//import Entity.HttpMethod;
//import Entity.HttpStatus;
//import Entity.Request;
//import Entity.Response;
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RequestProcessorTest {
//    private final String webAppPath = "src/test/resources";
//    private final RequestProcessor requestProcessor = new RequestProcessor(webAppPath);
//
//    @Test
//    public void processGetRequestTest() throws IOException {
//        //given
//        String uri = "index.html";
//        String expectedContent = "test string";
//        Request request = new Request();
//        request.setUri(uri);
//        request.setHttpMethod(HttpMethod.GET);
//
//        //do
//        Response actual = requestProcessor.processRequest(request);
//        Reader actualReader = actual.getContent();
//        String actualContent = IOUtils.toString(actualReader);
//        actualReader.close();
//
//        //verify
//        assertEquals(HttpStatus.OK, actual.getStatus());
//        assertEquals(expectedContent, actualContent);
//    }
//
//    @Test
//    public void processGetRequestWithWrongUriTest() {
//        //given
//        String uri = "test";
//        Request request = new Request();
//        request.setUri(uri);
//        request.setHttpMethod(HttpMethod.GET);
//
//        //do
//        Response actual = requestProcessor.processRequest(request);
//
//        //verify
//        assertEquals(HttpStatus.NOT_FOUND, actual.getStatus());
//        assertNull(actual.getContent());
//    }
//
//    @Test
//    public void uriPathIsNotAFilePathTest() {
//        //given
//        String uri = "index";
//        Request request = new Request();
//        request.setUri(uri);
//        request.setHttpMethod(HttpMethod.GET);
//
//        //do
//        Response actual = requestProcessor.processRequest(request);
//
//        //verify
//        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatus());
//        assertNull(actual.getContent());
//    }
//
//    @Test
//    public void nullRequestTest() {
//        //do
//        Response actual = requestProcessor.processRequest(null);
//
//        //verify
//        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatus());
//        assertNull(actual.getContent());
//    }
//
//    @Test
//    public void nullStatusTest() {
//        //given
//        Request request = new Request();
//
//        //do
//        Response actual = requestProcessor.processRequest(request);
//
//        //verify
//        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatus());
//        assertNull(actual.getContent());
//    }
//
//
//}