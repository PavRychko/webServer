import Entity.HttpMethod;
import Entity.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class RequestParserTest {
    private final RequestParser requestParser = new RequestParser();
    private static final Map<String, String> headers = new HashMap<>();
    private final String uri = "/test.txt";
    private final String inputHeaders = """
            Host: uk.wikipedia.org
            User-Agent: firefox/5.0 (Linux; Debian 5.0.8; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7
            Connection: close""";


    @BeforeAll
    static void setUpHeadersForRequest() {
        headers.put("Host", "uk.wikipedia.org");
        headers.put("User-Agent", "firefox/5.0 (Linux; Debian 5.0.8; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7");
        headers.put("Connection", "close");
    }

    @Test
    public void parseRequestGetTest() {
        //given
        String requestSample = "GET " + uri + " HTTP/1.1\n" + inputHeaders;
        BufferedReader reader = new BufferedReader(new StringReader(requestSample));
        Request expected = new Request();
        expected.setHttpMethod(HttpMethod.GET);
        expected.setUri(uri);
        expected.setHeaders(headers);

        //do
        Request result = requestParser.parseRequest(reader);

        //verify
        assertEquals(expected.getHttpMethod(), result.getHttpMethod());
        assertEquals(expected.getUri(), result.getUri());
        assertEquals(expected.getHeaders(), result.getHeaders());  // two maps are equals if they contain same values for the same keys
    }

    @Test
    public void parseRequestThrowsExceptionWhenNoMethodProvided() {
        //given
        String requestSample = uri + " HTTP/1.1\n" + inputHeaders;
        BufferedReader reader = new BufferedReader(new StringReader(requestSample));

        //do & verify
        assertThrows(RuntimeException.class, () -> requestParser.parseRequest(reader));
    }

    @Test
    public void parseRequestThrowsExceptionWhenWrongMethodProvided() {
        //given
        String requestSample = "TEST " + uri + " HTTP/1.1\n" + inputHeaders;
        BufferedReader reader = new BufferedReader(new StringReader(requestSample));

        //do & verify
        assertThrows(RuntimeException.class, () -> requestParser.parseRequest(reader));
    }
}