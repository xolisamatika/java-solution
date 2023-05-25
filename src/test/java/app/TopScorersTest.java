package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TopScorersTest {
    private static final String TEST_CSV_FILE_PATH = "src/test/resources/TestData.csv";
    private static final String NON_EXISTENT_CSV_FILE_PATH = "src/test/resources/NonExistentData.csv";

    private TopScorers topScorers;

    @BeforeEach
    void setUp() {
        topScorers = new TopScorers();
    }

    @Test
    void testLoadCSVFile_ValidFilePath_RowsLoaded() {
        topScorers.loadCSVFile(TEST_CSV_FILE_PATH);
        List<String[]> rows = topScorers.getRows();

        assertNotNull(rows);
        assertFalse(rows.isEmpty());
        assertEquals(5, rows.size());
    }

    @Test
    void testLoadCSVFile_EmptyFilePath_RowsNotLoaded() {
        topScorers.loadCSVFile("");
        List<String[]> rows = topScorers.getRows();

        assertNull(rows);
    }

    @Test
    void testLoadCSVFile_NonExistentFilePath_RowsNotLoaded() {
        topScorers.loadCSVFile(NON_EXISTENT_CSV_FILE_PATH);
        List<String[]> rows = topScorers.getRows();

        assertNull(rows);
    }

    @Test
    void testHasRows_RowsExist_ReturnsTrue() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"John", "Doe", "80"});
        topScorers.setRows(rows);

        assertTrue(topScorers.hasRows());
    }

    @Test
    void testHasRows_NoRows_ReturnsFalse() {
        assertFalse(topScorers.hasRows());
    }

    @Test
    void testProcessScores_RowsExist_ScoresProcessed() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"John", "Doe", "80"});
        rows.add(new String[]{"Jane", "Smith", "90"});
        topScorers.setRows(rows);

        topScorers.processScores();
        Map<Integer, List<String>> scores = topScorers.getScores();
        int maxScore = topScorers.getMaxScore();

        assertNotNull(scores);
        assertEquals(2, scores.size());
        assertTrue(scores.containsKey(80));
        assertTrue(scores.containsKey(90));
        assertEquals(1, scores.get(80).size());
        assertEquals(1, scores.get(90).size());
        assertEquals(90, maxScore);
    }

    @Test
    void testPrintTopScorers_ScoresExist_TopScorersPrinted() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"John", "Doe", "90"});
        rows.add(new String[]{"Jane", "Smith", "90"});
        topScorers.setRows(rows);
        topScorers.processScores();

        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        topScorers.printTopScorers();

        String expectedOutput = String.join(System.lineSeparator(),"Jane Smith","John Doe","Score: 90","");
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testPrintTopScorers_NoScores_NoOutput() {
        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        topScorers.printTopScorers();

        assertEquals("", outputStream.toString());
    }
}
