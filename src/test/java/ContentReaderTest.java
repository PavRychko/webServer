import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ContentReaderTest {
    private final String webAppPath = "/test";
    private final ContentReader contentReader = new ContentReader(webAppPath);

    @Test
    public void readContentTest() throws IOException {
        //given
        String uri = "index.html";
        Response expected = new Response();
        expected.setStatus(HttpStatus.OK);
        String expectedContent = "test string";

        //do
        Response actual = contentReader.readContent(uri);
        Reader actualReader = actual.getContent();
        String actualContent = IOUtils.toString(actualReader);
        actualReader.close();

        //verify
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void readContentWithWrongUriTest() {
        //given
        String uri ="test";

        //do
        Response actual = contentReader.readContent(uri);

        //verify
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatus());
        assertNull(actual.getContent());
    }

    @Test
    public void exceptionWhileReadingContentTest() throws IOException {
        //given
        String uri = "index.html";
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenThrow(IOException.class);

        //do
        Response actual = contentReader.readContent(uri);

        //verify
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatus());
        assertNull(actual.getContent());
    }

}