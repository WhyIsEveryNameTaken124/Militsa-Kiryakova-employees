package dataProcessors;
import dataClasses.Entry;
import dataClasses.Pair;
import java.util.*;

public class DataProcessor {


    public static List<Pair> findProjectPairs(List<Entry> entries) {
        List<Pair> projectPairs = new ArrayList<>();
        sortEntries(entries);
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                Entry entry1 = entries.get(i);
                Entry entry2 = entries.get(j);

                if (entry1.getProjectID() == entry2.getProjectID() && entry1.getEmpID() != entry2.getEmpID()) {
                    long overlap = entry1.overlapDays(entry2);
                    if (overlap > 0) {
                        projectPairs.add(new Pair(entry1.getEmpID(), entry2.getEmpID(), entry1.getProjectID(), overlap));
                    }
                }
            }
        }
        return projectPairs;
    }

    public static void sortEntries(List<Entry> entries) {
        Collections.sort(entries);
    }

    public static Pair findLongestWorkingPair(List<Entry> entries) {
        List<Pair> projectPairs = findProjectPairs(entries);
        Map<String, Long> pairTotalOverlap = new HashMap<>();
        Pair maxPair = null;

        for (Pair projectPair : projectPairs) {
            String pairKey = generatePairKey(projectPair.getEmp1(), projectPair.getEmp2());
            pairTotalOverlap.put(pairKey, pairTotalOverlap.getOrDefault(pairKey, 0L) + projectPair.getOverlapDays());
        }

        for (Map.Entry<String, Long> entry : pairTotalOverlap.entrySet()) {
            String[] empIds = entry.getKey().split("-");
            int emp1 = Integer.parseInt(empIds[0]);
            int emp2 = Integer.parseInt(empIds[1]);
            long totalOverlap = entry.getValue();

            if (maxPair == null || totalOverlap > maxPair.getOverlapDays()) {
                maxPair = new Pair(emp1, emp2, -1, totalOverlap);
            }
        }

        return maxPair;
    }

    private static String generatePairKey(int empID1, int empID2) {
        return empID1 < empID2 ? empID1 + "-" + empID2 : empID2 + "-" + empID1;
    }
}
