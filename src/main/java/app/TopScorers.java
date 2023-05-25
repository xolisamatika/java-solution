package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TopScorers {
    private static final String CSV_FILE_PATH = "src/main/resources/TestData.csv";
    private List<String[]> rows;
    private Map<Integer, List<String>> scores;
    private int maxScore;

    public static void main(String[] args) {
        TopScorers topScorers = new TopScorers();
        topScorers.loadCSVFile(CSV_FILE_PATH);

        if (topScorers.hasRows()) {
            topScorers.processScores();
            topScorers.printTopScorers();
        }
    }

    public void loadCSVFile(String csvFilePath) {
        rows = readCSVFile(csvFilePath);
    }

    public boolean hasRows() {
        return rows != null && !rows.isEmpty();
    }

    public void processScores() {
        scores = new HashMap<>();

        // Store the scores in a map
        for (String[] row : rows) {
            String firstName = row[0];
            String secondName = row[1];
            int score = Integer.parseInt(row[2]);

            scores.computeIfAbsent(score, k -> new ArrayList<>())
                    .add(firstName.concat(" ").concat(secondName));
        }

        maxScore = scores.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(Integer.MIN_VALUE);
    }

    public void printTopScorers() {
        if (scores == null || scores.isEmpty()) {
            return;
        }
        Set<String> topScorers = new TreeSet<>(scores.get(maxScore));

        for (String name : topScorers) {
            System.out.println(name);
        }

        System.out.println("Score: " + maxScore);
    }

    private List<String[]> readCSVFile(String csvFilePath) {
        List<String[]> rows;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            rows = reader
                    .lines()
                    .skip(1)
                    .map(s -> s.split(","))
                    .collect(Collectors.toList());
            return rows;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String[]> getRows() {
        if (hasRows()) {
            return rows;
        }
        return null;
    }

    public void setRows(List<String[]> rows) {
        this.rows = rows;
    }

    public Map<Integer, List<String>> getScores() {
        return scores;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
