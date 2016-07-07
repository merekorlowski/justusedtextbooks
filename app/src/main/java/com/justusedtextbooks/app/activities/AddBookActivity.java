package com.justusedtextbooks.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.data_structures.User;
import com.justusedtextbooks.app.data_structures.UserSession;
import com.justusedtextbooks.app.data_structures.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddBookActivity extends AppCompatActivity {

    private ImageView bookImage;
    private EditText bookTitle;
    private EditText bookAuthor;
    private EditText bookISBN;
    private EditText bookCourseCode;
    private EditText bookPrice;
    private EditText bookDescription;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_PICTURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        bookImage = (ImageView) findViewById(R.id.newBookImage);
        bookTitle = (EditText) findViewById(R.id.newBookTitle);
        bookAuthor = (EditText) findViewById(R.id.newBookAuthor);
        bookISBN = (EditText) findViewById(R.id.newBookISBN);
        bookCourseCode = (EditText) findViewById(R.id.bookCourseCode);
        bookPrice = (EditText) findViewById(R.id.newBookPrice);
        bookDescription = (EditText) findViewById(R.id.newBookDescription);
    }

    public void addBook(View view) {
        User user = UserSession.getInstance().getUser();
        user.addBook(new Book(
                user,
                ((BitmapDrawable)bookImage.getDrawable()).getBitmap(),
                bookTitle.getText().toString(),
                bookAuthor.getText().toString(),
                bookISBN.getText().toString(),
                bookCourseCode.getText().toString(),
                Double.parseDouble(bookPrice.getText().toString()),
                bookDescription.getText().toString()
        ));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currentFragment", 1);
        startActivity(intent);
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void upload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_IMAGE_CAPTURE)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bookImage.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookImage.setImageBitmap(thumbnail);
        Bundle extras = data.getExtras();

        // get bitmap
        Bitmap bitMap = (Bitmap) extras.get("data");
        bookImage.setImageBitmap(bitMap);
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

}
