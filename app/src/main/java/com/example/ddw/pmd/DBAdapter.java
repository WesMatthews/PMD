package com.example.ddw.pmd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Darryl on 12/5/2015.
 */
public class DBAdapter {

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "PMDdb";
    static final int DATABASE_VERSION = 1;
    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    String currentTable = "";

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                String boobs = DBContract.CREATE_DB;
                Log.d("Script: ", DBContract.CREATE_DB);
                db.execSQL(DBContract.CREATE_DB);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DBContract.WorkoutPlanInfo.WORKOUTPLAN_TABLE + ";" +
                    "DROP TABLE IF EXISTS " + DBContract.MealPlanInfo.MEALPLAN_TABLE + ";" +
                    "DROP TABLE IF EXISTS " + DBContract.UserInfo.USER_TABLE + ";");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() { DBHelper.close(); }

    //---retrieves all the users---
    public Cursor getAllUsers()
    {
        return db.query(DBContract.UserInfo.USER_TABLE, new String[] {
                DBContract.UserInfo.USER_ID,
                DBContract.UserInfo.USER_USERNAME,
                DBContract.UserInfo.USER_PASSWORD,
                DBContract.UserInfo.USER_FIRSTNAME,
                DBContract.UserInfo.USER_LASTNAME,
                DBContract.UserInfo.USER_USERTYPE,
                DBContract.UserInfo.USER_EMAIL,
                DBContract.UserInfo.USER_MEALPLAN,
                DBContract.UserInfo.USER_WORKOUTPLAN},                       
                null, null, null, null, null);
    }

    //---retrieves a particular user---
    public Cursor getUser(long id) throws SQLException {
        Cursor mCursor =
                db.query(true, DBContract.UserInfo.USER_TABLE, new String[] {
                        DBContract.UserInfo.USER_ID,
                        DBContract.UserInfo.USER_USERNAME,
                        DBContract.UserInfo.USER_PASSWORD,
                        DBContract.UserInfo.USER_FIRSTNAME,
                        DBContract.UserInfo.USER_LASTNAME,
                        DBContract.UserInfo.USER_USERTYPE,
                        DBContract.UserInfo.USER_EMAIL,
                        DBContract.UserInfo.USER_MEALPLAN,
                        DBContract.UserInfo.USER_WORKOUTPLAN},
                        DBContract.UserInfo.USER_ID + "=" + id, 
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getUserByUsername(String username) throws SQLException {
        Cursor mCursor =
                db.query(true, DBContract.UserInfo.USER_TABLE, new String[] {
                                DBContract.UserInfo.USER_ID,
                                DBContract.UserInfo.USER_USERNAME,
                                DBContract.UserInfo.USER_PASSWORD,
                                DBContract.UserInfo.USER_FIRSTNAME,
                                DBContract.UserInfo.USER_LASTNAME,
                                DBContract.UserInfo.USER_USERTYPE,
                                DBContract.UserInfo.USER_EMAIL,
                                DBContract.UserInfo.USER_MEALPLAN,
                                DBContract.UserInfo.USER_WORKOUTPLAN},
                        DBContract.UserInfo.USER_USERNAME + "= ?",
                        new String[] {username}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getUserByEmail(String email) throws SQLException {
        Cursor mCursor =
                db.query(true, DBContract.UserInfo.USER_TABLE, new String[] {
                                DBContract.UserInfo.USER_ID,
                                DBContract.UserInfo.USER_USERNAME,
                                DBContract.UserInfo.USER_PASSWORD,
                                DBContract.UserInfo.USER_FIRSTNAME,
                                DBContract.UserInfo.USER_LASTNAME,
                                DBContract.UserInfo.USER_USERTYPE,
                                DBContract.UserInfo.USER_EMAIL,
                                DBContract.UserInfo.USER_MEALPLAN,
                                DBContract.UserInfo.USER_WORKOUTPLAN},
                        DBContract.UserInfo.USER_EMAIL + "= ?",
                        new String[] {email}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor checkUserLogin(String username, String password) throws SQLException {
        Cursor mCursor =
                db.query(true, DBContract.UserInfo.USER_TABLE, new String[] {
                                DBContract.UserInfo.USER_ID,
                                DBContract.UserInfo.USER_USERNAME,
                                DBContract.UserInfo.USER_PASSWORD,
                                DBContract.UserInfo.USER_FIRSTNAME,
                                DBContract.UserInfo.USER_LASTNAME,
                                DBContract.UserInfo.USER_USERTYPE,
                                DBContract.UserInfo.USER_EMAIL,
                                DBContract.UserInfo.USER_MEALPLAN,
                                DBContract.UserInfo.USER_WORKOUTPLAN},
                        DBContract.UserInfo.USER_USERNAME + "= ?",
                        new String[] {username}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        else {
            return null;
        }
        if(mCursor.getCount() > 0) {
            if (mCursor.getString(2).equals(password)) {
                return mCursor;
            }
        }
        return null;
    }
    
    //---insert a user into the database---
    public long addUser(String username, String password, String firstname, String lastname, String usertype, String email, int mealplan, int workoutplan) {
        currentTable = DBContract.UserInfo.USER_TABLE;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBContract.UserInfo.USER_USERNAME, username);
        initialValues.put(DBContract.UserInfo.USER_PASSWORD, password);
        initialValues.put(DBContract.UserInfo.USER_FIRSTNAME, firstname);
        initialValues.put(DBContract.UserInfo.USER_LASTNAME, lastname);
        initialValues.put(DBContract.UserInfo.USER_USERTYPE, usertype);
        initialValues.put(DBContract.UserInfo.USER_EMAIL, email);
        initialValues.put(DBContract.UserInfo.USER_MEALPLAN, mealplan);
        initialValues.put(DBContract.UserInfo.USER_WORKOUTPLAN, workoutplan);
        return db.insert(currentTable, null, initialValues);
    }

    public long addUser(String username, String password, String firstname, String lastname, String usertype, String email) {
        currentTable = DBContract.UserInfo.USER_TABLE;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBContract.UserInfo.USER_USERNAME, username);
        initialValues.put(DBContract.UserInfo.USER_PASSWORD, password);
        initialValues.put(DBContract.UserInfo.USER_FIRSTNAME, firstname);
        initialValues.put(DBContract.UserInfo.USER_LASTNAME, lastname);
        initialValues.put(DBContract.UserInfo.USER_USERTYPE, usertype);
        initialValues.put(DBContract.UserInfo.USER_EMAIL, email);
        return db.insert(currentTable, null, initialValues);
    }

    //---update user in database---
    public boolean updateUser(long id, String username, String password, String firstname, String lastname, String usertype, String email, int mealplan, int workoutplan) {
        currentTable = DBContract.UserInfo.USER_TABLE;
        ContentValues values = new ContentValues();
        values.put(DBContract.UserInfo.USER_USERNAME, username);
        values.put(DBContract.UserInfo.USER_PASSWORD, password);
        values.put(DBContract.UserInfo.USER_FIRSTNAME, firstname);
        values.put(DBContract.UserInfo.USER_LASTNAME, lastname);
        values.put(DBContract.UserInfo.USER_USERTYPE, usertype);
        values.put(DBContract.UserInfo.USER_EMAIL, email);
        values.put(DBContract.UserInfo.USER_MEALPLAN, mealplan);
        values.put(DBContract.UserInfo.USER_WORKOUTPLAN, workoutplan);
        return db.update(currentTable, values, DBContract.UserInfo.USER_ID + " = " + id, null) > 0;
    }

    //---deletes a particular user---
    public boolean deleteUser(long id)
    {
        return db.delete(DBContract.UserInfo.USER_TABLE, DBContract.UserInfo.USER_ID + "=" + id, null) > 0;
    }

    //---retrieves all the users---
    public Cursor getAllMealplans() {
        return db.query(DBContract.MealPlanInfo.MEALPLAN_TABLE, new String[] {
                        DBContract.MealPlanInfo.MEALPLAN_ID,
                        DBContract.MealPlanInfo.MEALPLAN_PLANNAME,
                        DBContract.MealPlanInfo.MEALPLAN_DESCRIPTION,
                        DBContract.MealPlanInfo.MEALPLAN_DETAILS},
                null, null, null, null, null);
    }

    //---retrieves a particular meal plan---
    public Cursor getMealplan(long id) throws SQLException {
        Cursor mCursor =
                db.query(true, DBContract.MealPlanInfo.MEALPLAN_TABLE, new String[] {
                                DBContract.MealPlanInfo.MEALPLAN_ID,
                                DBContract.MealPlanInfo.MEALPLAN_PLANNAME,
                                DBContract.MealPlanInfo.MEALPLAN_DESCRIPTION,
                                DBContract.MealPlanInfo.MEALPLAN_DETAILS},
                        DBContract.MealPlanInfo.MEALPLAN_ID + "=" + id,
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    
    //---insert a meal plan into the database---
    public long addMealplan(String name, String desc, String detail) {
        long boobs = -1;
        currentTable = DBContract.MealPlanInfo.MEALPLAN_TABLE;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBContract.MealPlanInfo.MEALPLAN_PLANNAME, name);
        initialValues.put(DBContract.MealPlanInfo.MEALPLAN_DESCRIPTION, desc);
        initialValues.put(DBContract.MealPlanInfo.MEALPLAN_DETAILS, detail);
        boobs = db.insert(currentTable, null, initialValues);
        return boobs;
    }

    //---update meal plan in database---
    public boolean updateMealplan (long id, String name, String desc, String detail) {
        currentTable = DBContract.MealPlanInfo.MEALPLAN_TABLE;
        ContentValues values = new ContentValues();
        values.put(DBContract.MealPlanInfo.MEALPLAN_PLANNAME, name);
        values.put(DBContract.MealPlanInfo.MEALPLAN_DESCRIPTION, desc);
        values.put(DBContract.MealPlanInfo.MEALPLAN_DETAILS, detail);
        boolean gupta = db.update(currentTable, values, DBContract.MealPlanInfo.MEALPLAN_ID + " = " + id, null) > 0;
        return gupta;
    }

    //---deletes a particular meal plan---
    public boolean deleteMealplan(long id)
    {
        return db.delete(DBContract.MealPlanInfo.MEALPLAN_TABLE, DBContract.MealPlanInfo.MEALPLAN_ID + "=" + id, null) > 0;
    }

    //---retrieves all the workout plans---
    public Cursor getAllWorkoutplans() {
        return db.query(DBContract.WorkoutPlanInfo.WORKOUTPLAN_TABLE, new String[] {
                        DBContract.WorkoutPlanInfo.WORKOUTPLAN_ID,
                        DBContract.WorkoutPlanInfo.WORKOUTPLAN_PLANNAME,
                        DBContract.WorkoutPlanInfo.WORKOUTPLAN_DESCRIPTION,
                        DBContract.WorkoutPlanInfo.WORKOUTPLAN_DETAILS},
                null, null, null, null, null);
    }

    //---retrieves a particular workout plan---
    public Cursor getWorkoutplan(long id) throws SQLException {
        Cursor mCursor =
                db.query(true, DBContract.WorkoutPlanInfo.WORKOUTPLAN_TABLE, new String[] {
                                DBContract.WorkoutPlanInfo.WORKOUTPLAN_ID,
                                DBContract.WorkoutPlanInfo.WORKOUTPLAN_PLANNAME,
                                DBContract.WorkoutPlanInfo.WORKOUTPLAN_DESCRIPTION,
                                DBContract.WorkoutPlanInfo.WORKOUTPLAN_DETAILS},
                        DBContract.WorkoutPlanInfo.WORKOUTPLAN_ID + "=" + id,
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //---insert a workout plan into the database---
    public long addWorkoutplan(String name, String desc, String detail) {
        currentTable = DBContract.WorkoutPlanInfo.WORKOUTPLAN_TABLE;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBContract.WorkoutPlanInfo.WORKOUTPLAN_PLANNAME, name);
        initialValues.put(DBContract.WorkoutPlanInfo.WORKOUTPLAN_DESCRIPTION, desc);
        initialValues.put(DBContract.WorkoutPlanInfo.WORKOUTPLAN_DETAILS, detail);
        return db.insert(currentTable, null, initialValues);
    }

    //---update workout plan in database ---
    public boolean updateWorkoutplan(long id, String name, String desc, String detail) {
        currentTable = DBContract.WorkoutPlanInfo.WORKOUTPLAN_TABLE;
        ContentValues values = new ContentValues();
        values.put(DBContract.WorkoutPlanInfo.WORKOUTPLAN_PLANNAME, name);
        values.put(DBContract.WorkoutPlanInfo.WORKOUTPLAN_DESCRIPTION, desc);
        values.put(DBContract.WorkoutPlanInfo.WORKOUTPLAN_DETAILS, detail);
        return db.update(currentTable, values, DBContract.WorkoutPlanInfo.WORKOUTPLAN_ID + " = " + id, null) > 0;
    }

    //---deletes a particular workout plan---
    public boolean deleteWorkoutplan(long id)
    {
        return db.delete(DBContract.WorkoutPlanInfo.WORKOUTPLAN_TABLE, DBContract.WorkoutPlanInfo.WORKOUTPLAN_ID + "=" + id, null) > 0;
    }
}
