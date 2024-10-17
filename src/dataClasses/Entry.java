package dataClasses;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Entry implements Comparable<Entry> {
    private final int empID;
    private final int projectID;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    private static final List<DateTimeFormatter> DATE_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("dd MM yyyy"),
            DateTimeFormatter.ofPattern("MM dd, yyyy")
    );

    public Entry(int empID, int projectID, String dateFromStr, String dateToStr) {
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = parseDate(dateFromStr);
        this.dateTo = dateToStr.equalsIgnoreCase("NULL") ? LocalDate.now() : parseDate(dateToStr);
    }

    public int getEmpID() { return empID; }
    public int getProjectID() { return projectID; }

    @Override
    public int compareTo(Entry other) {
        return Integer.compare(this.projectID, other.projectID);
    }

    public long overlapDays(Entry other) {
        LocalDate maxStart = dateFrom.isAfter(other.dateFrom) ? dateFrom : other.dateFrom;
        LocalDate minEnd = dateTo.isBefore(other.dateTo) ? dateTo : other.dateTo;

        if (!maxStart.isBefore(minEnd)) {
            return 0;
        }
        return ChronoUnit.DAYS.between(maxStart, minEnd);
    }

    private LocalDate parseDate(String dateStr) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {}
        }
        throw new IllegalArgumentException("Date format not supported: " + dateStr);
    }
}