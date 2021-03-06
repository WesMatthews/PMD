package com.example.ddw.pmd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddw.pmd.dtos.mealplanDTO;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserList.OnFragmentInteractionListener,
        MealPlanListFragment.OnFragmentInteractionListener, WorkoutPlanListFragment.OnFragmentInteractionListener,
        WorkoutPlanDetailsFragment.OnFragmentInteractionListener, MealPlanDetailsFragment.OnFragmentInteractionListener{

    Fragment frag = null;
    SharedPreferences pref;

    private String name;
    private String shortdescription;
    private String summary;

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBAdapter(getApplicationContext());

        pref = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);

        frag = new Home_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (frag instanceof MealPlanDetailsFragment) {
            frag = new MealPlanListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
        } else if (frag instanceof WorkoutPlanDetailsFragment) {
            frag = new WorkoutPlanListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
        } else if (frag instanceof About_Fragment) {
            frag = new Home_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
        }
        else
            super.onBackPressed();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_about) {
            frag = new About_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            frag = new Home_Fragment();
        } else if (id == R.id.nav_UserList) {
            frag = new UserList();
        } else if (id == R.id.nav_MealPlan) {
            frag = new MealPlanListFragment();
        } else if (id == R.id.nav_WorkoutPlan){
            frag = new WorkoutPlanListFragment();
        }  else if (id == R.id.nav_signout) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("currUser", -1).apply();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onArticleSelected(int position)  {
        //do things;
    }

    public void onMealArticleSelected(int mealid){
        frag = new MealPlanDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", mealid);
        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();

    }
    public void onWorkoutArticleSelected(int workoutid){
        frag = new WorkoutPlanDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", workoutid);
        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
    }

    public void onUserArticleSelected(int userid){
        frag = new Home_Fragment();
        Bundle args = new Bundle();
        args.putInt("id", userid);
        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
    }

    public void addMealPlan(View view){
        EditText edtText = (EditText)findViewById(R.id.Plan_Name);
        name = edtText.getText().toString();
        edtText = (EditText)findViewById(R.id.Plan_Descript);
        shortdescription = edtText.getText().toString();
        edtText = (EditText)findViewById(R.id.Plan_Summary);
        summary = edtText.getText().toString();

        if(!name.equals("") && !shortdescription.equals("") && !summary.equals("")) {
            db.open();
            db.addMealplan(name, shortdescription, summary);
            db.close();
            Toast.makeText(getApplicationContext(), name + " Added!", Toast.LENGTH_SHORT).show();
            frag = new MealPlanListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();

        }

    }

    public void addWorkoutPlan(View view){
        EditText edtText = (EditText)findViewById(R.id.Plan_Name);
        name = edtText.getText().toString();
        edtText = (EditText)findViewById(R.id.Plan_Descript);
        shortdescription = edtText.getText().toString();
        edtText = (EditText)findViewById(R.id.Plan_Summary);
        summary = edtText.getText().toString();

        if(!name.equals("") && !shortdescription.equals("") && !summary.equals("")) {
            db.open();
            db.addWorkoutplan(name, shortdescription, summary);
            db.close();
            Toast.makeText(getApplicationContext(), name + " Added!", Toast.LENGTH_SHORT).show();
            frag = new WorkoutPlanListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();

        }

    }


}
