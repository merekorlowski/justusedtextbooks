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
import com.justusedtextbooks.app.data_structures.UserSession;
import com.justusedtextbooks.app.data_structures.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class EditBookActivity extends AppCompatActivity {

    private ImageView myBookImage;
    private EditText myBookTitle;
    private EditText myBookAuthor;
    private EditText myBookISBN;
    private EditText myBookCourseCode;
    private EditText myBookPrice;
    private EditText myBookDescription;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_PICTURE = 0;
    private Book myBook;
    private boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("myBookIndex");
        }

        DecimalFormat df = new DecimalFormat("#0.00");

        myBook = UserSession.getInstance().getUser().getMyBooks().get(position);

        myBookImage = (ImageView) findViewById(R.id.myBookImage);
        myBookTitle = (EditText) findViewById(R.id.myBookTitle);
        myBookAuthor = (EditText) findViewById(R.id.myBookAuthor);
        myBookISBN = (EditText) findViewById(R.id.myBookISBN);
        myBookCourseCode = (EditText) findViewById(R.id.myBookCourseCode);
        myBookPrice = (EditText)findViewById(R.id.myBookPrice);
        myBookDescription = (EditText)findViewById(R.id.myBookDescription);

        myBookImage.setImageBitmap(myBook.getImage());
        myBookTitle.setText(myBook.getTitle());
        myBookAuthor.setText(myBook.getAuthor());
        myBookISBN.setText(myBook.getISBN());
        myBookCourseCode.setText(myBook.getCourseCode());
        myBookPrice.setText("" + df.format(myBook.getPrice()));
        myBookDescription.setText(myBook.getDescription());

        result = Utility.checkPermission(EditBookActivity.this);

    }

    public void submitChanges(View view) {

        myBook.setImage(((BitmapDrawable)myBookImage.getDrawable()).getBitmap());
        myBook.setTitle(myBookTitle.getText().toString());
        myBook.setAuthor(myBookAuthor.getText().toString().substring(3));
        myBook.setISBN(myBookISBN.getText().toString());
        myBook.setCourseCode(myBookCourseCode.getText().toString());
        myBook.setPrice(Double.parseDouble(myBookPrice.getText().toString()));
        myBook.setDescription(myBookDescription.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currentFragment", 1);
        startActivity(intent);
    }

    public void takePhoto(View view) {
        if(result) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void upload(View view) {
        if(result) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_PICTURE);
        }
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
        myBookImage.setImageBitmap(bm);
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
        myBookImage.setImageBitmap(thumbnail);
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
