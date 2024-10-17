package dataClasses;

public class Pair {
    int emp1;
    int emp2;
    int projectID;
    long overlapDays;

    public Pair(int emp1, int emp2, int projectID, long overlapDays) {
        this.emp1 = emp1;
        this.emp2 = emp2;
        this.projectID = projectID;
        this.overlapDays = overlapDays;
    }

    public int getEmp1() {
        return emp1;
    }

    public int getEmp2() {
        return emp2;
    }

    public int getProjectID() {
        return projectID;
    }

    public long getOverlapDays() {
        return overlapDays;
    }
}