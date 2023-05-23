package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TopScorersTest {

    private static final String CSV_FILE = "src/main/resources/TestData.csv";

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testReadCSVFile() throws IOException {
        List<String[]> rows = TopScorers.readCSVFile(CSV_FILE);

        // Verify that rows are not null
        Assertions.assertNotNull(rows);

        // Verify the number of rows read from the CSV file
        Assertions.assertEquals(5, rows.size());

        // Verify the content of the first row
        String[] firstRow = rows.get(1);
        Assertions.assertEquals("Dee", firstRow[0]);
        Assertions.assertEquals("Moore", firstRow[1]);
        Assertions.assertEquals("56", firstRow[2]);

        // Verify the content of the last row
        String[] lastRow = rows.get(rows.size() - 1);
        Assertions.assertEquals("George", lastRow[0]);
        Assertions.assertEquals("Of The Jungle", lastRow[1]);
        Assertions.assertEquals("78", lastRow[2]);
    }
}
