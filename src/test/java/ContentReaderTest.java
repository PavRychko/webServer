import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContentReaderTest {
    private final String webAppPath = "src/test/resources";
    private final ContentReader contentReader = new ContentReader(webAppPath);

    @Test
    public void readContentTest() throws IOException {
        //given
        String uri = "index.html";
        String expectedContent = "test string";

        //do
        Response actual = contentReader.readContent(uri);
        Reader actualReader = actual.getContent();
        String actualContent = IOUtils.toString(actualReader);
        actualReader.close();

        //verify
        assertEquals(HttpStatus.OK, actual.getStatus());
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
    public void contentIsNotAFileTest() {
        //given
        String uri = "index";

        //do
        Response actual = contentReader.readContent(uri);

        //verify
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatus());
        assertNull(actual.getContent());
    }

    //TODO: create test for Internal Server Error case
}