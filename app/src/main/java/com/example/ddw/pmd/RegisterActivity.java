package com.example.ddw.pmd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registration");
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}
