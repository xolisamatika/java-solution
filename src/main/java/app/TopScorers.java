package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class TopScorers {
    public static void main(String[] args) {
        String csvFile = "src\\main\\resources\\TestData.csv";
        List<String[]> rows = readCSVFile(csvFile);

        if (rows != null) {
            Map<Integer, List<String>> scores = new HashMap<>();

            //Remove the first row
            rows.remove(0);

            // Store the scores in a map
            for (String[] row : rows) {
                String firstName = row[0];
                String secondName = row[1];
                int score = Integer.parseInt(row[2]);

                if (scores.containsKey(score)) {
                    scores.get(score).add(firstName.concat(" ").concat(secondName));
                } else {
                    List<String> names = new ArrayList<>();
                    names.add(firstName.concat(" ").concat(secondName));
                    scores.put(score, names);
                }
            }

            // Find the highest score
            int maxScore = Integer.MIN_VALUE;
            for (int score : scores.keySet()) {
                if (score > maxScore) {
                    maxScore = score;
                }
            }

            // Get the names of the top scorers in alphabetical order
            TreeSet<String> topScorers = new TreeSet<>(scores.get(maxScore));

            for (String name : topScorers) {
                System.out.println(name);
            }

            System.out.println("Score: " + maxScore);
        }
    }

    public static List<String[]> readCSVFile(String csvFile) {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                rows.add(row);
            }

            return rows;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
