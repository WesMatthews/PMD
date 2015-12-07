package com.example.ddw.pmd;

/**
 * Created by Darryl on 12/5/2015.
 */
public class DBContract {

    public static final String CREATE_DB =
            UserInfo.CREATETABLE_USERS + MealPlanInfo.CREATETABLE_MEALPLANS + WorkoutPlanInfo.CREATETABLE_WORKOUTPLANS;


    public static abstract class UserInfo {
        public static final String USER_TABLE = "users";
        public static final String USER_ID = "userid";
        public static final String USER_USERNAME = "username";
        public static final String USER_PASSWORD = "password";
        public static final String USER_FIRSTNAME = "firstname";
        public static final String USER_LASTNAME = "lastname";
        public static final String USER_USERTYPE = "usertype";
        public static final String USER_EMAIL = "email";
        public static final String USER_MEALPLAN = "mealplan";
        public static final String USER_WORKOUTPLAN = "workoutplan";

        public static final String CREATETABLE_USERS =
                "create table " + USER_TABLE + " (" +
                        USER_ID + " integer primary key autoincrement, " +
                        USER_USERNAME + " text not null, " +
                        USER_PASSWORD + " text not null, " +
                        USER_FIRSTNAME + " text not null, " +
                        USER_LASTNAME + " text not null, " +
                        USER_USERTYPE + " integer not null, " +
                        USER_EMAIL + " text not null, " +
                        USER_MEALPLAN + " integer, " +
                        USER_WORKOUTPLAN + " integer);\n\n";
    }

    public static abstract class MealPlanInfo {
        public static final String MEALPLAN_TABLE = "mealplans";
        public static final String MEALPLAN_ID = "mealplanid";
        public static final String MEALPLAN_PLANNAME = "planname";
        public static final String MEALPLAN_DESCRIPTION = "description";
        public static final String MEALPLAN_DETAILS = "details";

        public static final String CREATETABLE_MEALPLANS =
                "create table " + MEALPLAN_TABLE + " (" +
                        MEALPLAN_ID + " integer primary key autoincrement, " +
                        MEALPLAN_PLANNAME + " text not null, " +
                        MEALPLAN_DESCRIPTION + " text not null, " +
                        MEALPLAN_DETAILS + " text not null);\n\n";
    }

    public static abstract class WorkoutPlanInfo {
        public static final String WORKOUTPLAN_TABLE = "workoutplans";
        public static final String WORKOUTPLAN_ID = "workoutplanid";
        public static final String WORKOUTPLAN_PLANNAME = "planname";
        public static final String WORKOUTPLAN_DESCRIPTION = "description";
        public static final String WORKOUTPLAN_DETAILS = "details";

        public static final String CREATETABLE_WORKOUTPLANS =
                "create table " + WORKOUTPLAN_TABLE + " (" +
                        WORKOUTPLAN_ID + " integer primary key autoincrement, " +
                        WORKOUTPLAN_PLANNAME + " text not null, " +
                        WORKOUTPLAN_DESCRIPTION + " text not null, " +
                        WORKOUTPLAN_DETAILS + " text not null);\n\n";
    }

}
