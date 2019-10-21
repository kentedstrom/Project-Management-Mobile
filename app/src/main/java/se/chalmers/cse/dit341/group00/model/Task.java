package se.chalmers.cse.dit341.group00.model;

// My task Class. Used by GSON to transfer JSON directly to Java Object
public class Task {
    public String name;
    public String description;
    public boolean completed;

    //Havn't found the right format on the Date Objects yet

    public Task() {
    }

    @Override
    public String toString() {
        return name; //This is for the spinner in EmailActivity
    }
}
