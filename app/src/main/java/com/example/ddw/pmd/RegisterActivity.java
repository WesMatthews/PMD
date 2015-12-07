package com.example.ddw.pmd;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private DBAdapter db;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBAdapter(this);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        if(checkValues()) {
            db.open();
            db.addUser(username, password, firstname, lastname, "Client", email);
            db.close();
            Toast.makeText(this.getApplicationContext(), "Account created!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }

    private boolean checkValues(){
        Cursor c;
        TextView txtError = (TextView)findViewById(R.id.txtError);
        EditText currText = (EditText)findViewById(R.id.txtFirstName);
        firstname = currText.getText().toString();
        currText = (EditText)findViewById(R.id.txtLastName);
        lastname = currText.getText().toString();
        currText = (EditText)findViewById(R.id.txtEmail);
        email = currText.getText().toString();
        currText = (EditText)findViewById(R.id.txtUserName);
        username = currText.getText().toString();
        currText = (EditText)findViewById(R.id.txtPassword);
        password = currText.getText().toString();
        currText = (EditText)findViewById(R.id.txtConfirm);
        confirmpassword = currText.getText().toString();

        if(firstname.matches("") || lastname.matches("") || email.matches("") || username.matches("") || password.matches("") || confirmpassword.matches("")) {
            txtError.setText("Please fill out all fields!");
            return false;
        }

        if(!email.contains("@")){
            txtError.setText("Invalid Email!");
            return false;
        }

        db.open();
        c = db.getUserByEmail(email);
        if (c.getCount() > 0) {
            txtError.setText("Email already in use!");
            db.close();
            return false;
        }
        db.close();

        db.open();
        c = db.getUserByUsername(username);
        if(c.getCount() > 0) {
            txtError.setText("Username already in use!");
            db.close();
            return false;
        }
        db.close();

        if(password.length() < 6){
            txtError.setText("Password is too short!");
            return false;
        }
        else if(!password.equals(confirmpassword)) {
            txtError.setText("Password does not match Confirm!");
            return false;
        }

        return true;
    }

}
