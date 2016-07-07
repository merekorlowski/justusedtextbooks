package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.justusedtextbooks.app.Database;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.User;
import com.justusedtextbooks.app.data_structures.UserSession;

import java.util.ArrayList;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private EditText usernameOrEmail;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        usernameOrEmail = (EditText) findViewById(R.id.usernameOrEmail);
        users = Database.getInstance().getUsers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_logOut:
                UserSession.getInstance().setUser(null);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void recover(View view) {

        User user = null;

        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(usernameOrEmail.getText().toString())
                    || users.get(i).getEmail().equals(usernameOrEmail.getText().toString()))
                user = users.get(i);
        }

        if(user != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Used Textbooks - Password Recovery");
            intent.putExtra(Intent.EXTRA_TEXT, "Your password is " + user.getPassword());

            try {
                startActivity(intent);
                Toast.makeText(PasswordRecoveryActivity.this, "Password sent.", Toast.LENGTH_SHORT).show();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(PasswordRecoveryActivity.this, "No email client installed.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(PasswordRecoveryActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
        }

    }

}
