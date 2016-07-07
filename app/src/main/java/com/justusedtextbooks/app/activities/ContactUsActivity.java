package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.UserSession;

import org.w3c.dom.Text;

public class ContactUsActivity extends AppCompatActivity {

    private ImageView phoneImage;
    private TextView phoneNumber;
    private ImageView emailImage;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        phoneImage = (ImageView) findViewById(R.id.phoneImage);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        emailImage = (ImageView) findViewById(R.id.emailImage);
        email = (TextView) findViewById(R.id.email);

        Drawable phoneDrawable = getResources().getDrawable(R.drawable.ic_local_phone_white_24dp);
        phoneDrawable.setColorFilter(new
                PorterDuffColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY));

        Drawable emailDrawable = getResources().getDrawable(android.R.drawable.ic_dialog_email);
        emailDrawable.setColorFilter(new
                PorterDuffColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY));

        phoneImage.setImageDrawable(phoneDrawable);
        emailImage.setImageDrawable(emailDrawable);

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
            case R.id.action_termsOfUse:
                intent = new Intent(this, TermsOfUseActivity.class);
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

    public void callUs(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.getText().toString()));
        startActivity(intent);
    }

    public void emailUs(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUsActivity.this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

}
