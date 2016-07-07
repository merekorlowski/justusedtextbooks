package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.justusedtextbooks.app.Database;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.User;
import com.justusedtextbooks.app.data_structures.Validator;

public class SignUpActivity extends AppCompatActivity {

    private TextView signUpError;
    private EditText fullName;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        signUpError = (TextView) findViewById(R.id.signUpError);
        fullName = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch(item.getItemId()) {
            case R.id.action_about:
                //intent = new Intent(this, AboutActivity.class);
                return true;
            case R.id.action_contactUs:
                intent = new Intent(this, ContactUsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_termsOfUse:
                intent = new Intent(this, TermsOfUseActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void signUp(View view) {

        boolean valid = true;

        if(!Validator.validateFullName(fullName.getText().toString())) {
            fullName.setError("Name must only contain letters and can not be empty.");
            valid = false;
        }

        if(!Validator.validateEmail(email.getText().toString())) {
            email.setError("Invalid email.");
            valid = false;
        }

        if(!Validator.validateUsername(username.getText().toString())) {
            username.setError("Username must contain between 6 and 12 characters and contain no special characters.");
            valid = false;
        }

        if(!Validator.validatePassword(password.getText().toString())) {
            password.setError("Password must contain between 6 and 12 characters and contain at least 1 number.");
            valid = false;
        }

        if(!Validator.validateConfirmPassword(password.getText().toString(), confirmPassword.getText().toString())) {
            confirmPassword.setError("Must match password.");
            valid = false;
        }

        if(valid) {

            boolean exists = Database.getInstance().addUser(new User(
                    fullName.getText().toString(),
                    email.getText().toString(),
                    username.getText().toString(),
                    password.getText().toString()));

            if(exists) {
                signUpError.setText("Username or email already exists.");
            } else {
                signUpError.setText("");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }



        }

    }

}
