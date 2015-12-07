package com.example.ddw.pmd.dtos;

/**
 * Created by Darryl on 12/6/2015.
 */
public class workoutplanDTO {

    private int id;
    private String planname;
    private String description;
    private String details;

    @Override
    public String toString() {
        return planname + " - " + description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
