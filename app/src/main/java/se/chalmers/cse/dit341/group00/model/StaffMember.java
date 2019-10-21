package se.chalmers.cse.dit341.group00.model;

public class StaffMember {
    public String name;
    public int salaryPerHour;
    public String email;
    public String _id;

    StaffMember() {


    }

    @Override
    public String toString() {
        return name; //This is for the spinner in EmailActivity
    }
}
