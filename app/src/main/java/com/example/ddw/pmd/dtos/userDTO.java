package com.example.ddw.pmd.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class userDTO {

    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String usertype;
    private int mealplan;
    private int workoutplan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public int getMealplan() {
        return mealplan;
    }

    public void setMealplan(int mealplan) {
        this.mealplan = mealplan;
    }

    public int getWorkoutplan() {
        return workoutplan;
    }

    public void setWorkoutplan(int workoutplan) {
        this.workoutplan = workoutplan;
    }

}
