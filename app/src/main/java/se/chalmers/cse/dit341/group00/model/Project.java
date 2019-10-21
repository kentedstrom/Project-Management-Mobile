package se.chalmers.cse.dit341.group00.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// My project Class. Used by GSON to transfer JSON directly to Java Object
public class Project {
    public String name;
    public String description;
    public boolean status;
    public String[] tasks;


    //Havn't found the right format on the Date Objects yet
    //public Date startDate;
    //public Date projectedEndDate;
    //public Date actualEndDate;
    //public Tasks[] tasks;

    Project() {
    }

    @Override
    public String toString() {
        return name; //This is for the spinner in EmailActivity
    }
}
