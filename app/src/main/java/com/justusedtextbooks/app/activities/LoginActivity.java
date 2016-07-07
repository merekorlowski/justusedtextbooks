package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.Validator;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

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
        }

        return super.onOptionsItemSelected(item);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, PasswordRecoveryActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        TextView loginError = (TextView) findViewById(R.id.loginError);

        if(Validator.validateLogin(username.getText().toString(), password.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            loginError.setText("Unable to log in. Invalid username or password.");
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
