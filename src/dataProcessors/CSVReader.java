package dataProcessors;
import dataClasses.Entry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Entry> readEntriesFromCSV(String filePath) {
        List<Entry> entries = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            try {
                if ((line = br.readLine()) != null) {
                    String[] values = line.split(",");

                    int empID = Integer.parseInt(values[0].trim());
                    int projectID = Integer.parseInt(values[1].trim());
                    String dateFrom = values[2].trim();
                    String dateTo = values[3].trim();

                    Entry entry = new Entry(empID, projectID, dateFrom, dateTo);
                    entries.add(entry);
                }
            } catch (Exception e) {
                //if header line -> skip
            }

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                int empID = Integer.parseInt(values[0].trim());
                int projectID = Integer.parseInt(values[1].trim());
                String dateFrom = values[2].trim();
                String dateTo = values[3].trim();

                Entry entry = new Entry(empID, projectID, dateFrom, dateTo);
                entries.add(entry);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return entries;
    }
}