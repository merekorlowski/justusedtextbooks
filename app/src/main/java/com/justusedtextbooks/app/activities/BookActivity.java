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
import android.widget.ImageView;
import android.widget.TextView;

import com.justusedtextbooks.app.Database;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.data_structures.User;
import com.justusedtextbooks.app.data_structures.UserSession;

import java.text.DecimalFormat;

public class BookActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title;
    private TextView author;
    private TextView ISBN;
    private TextView seller;
    private TextView price;
    private TextView description;
    private User currentUser;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        image = (ImageView) findViewById(R.id.bookImage);
        title = (TextView) findViewById(R.id.bookTitle);
        author = (TextView) findViewById(R.id.bookAuthor);
        ISBN = (TextView) findViewById(R.id.bookISBN);
        seller = (TextView) findViewById(R.id.bookSeller);
        price = (TextView) findViewById(R.id.bookPrice);
        description = (TextView) findViewById(R.id.bookDescription);

        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            position = extras.getInt("index");

        DecimalFormat df = new DecimalFormat("#0.00");

        currentUser = UserSession.getInstance().getUser();
        book = Database.getInstance().getBooks().get(position);
        image.setImageBitmap(book.getImage());
        title.setText(book.getTitle());
        author.setText("by " + book.getAuthor());
        ISBN.setText("ISBN: " + book.getISBN());
        seller.setText("Seller: " + book.getUser().getFullName());
        price.setText("$" + df.format(book.getPrice()));
        description.setText(book.getDescription());

    }

    public void addToCart(View view) {
        currentUser.addBookToCart(book);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currentFragment", 2);
        startActivity(intent);
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
