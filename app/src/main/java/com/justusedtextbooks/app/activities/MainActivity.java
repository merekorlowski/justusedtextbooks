package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.justusedtextbooks.app.Database;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.adapters.BooksAdapter;
import com.justusedtextbooks.app.data_structures.UserSession;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchBar;
    private ListView searchResultsList;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("currentFragment");
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(position);

        Drawable search = getResources().getDrawable(R.drawable.ic_search_white_24dp);
        search.setColorFilter(new
                PorterDuffColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY));

        Drawable book = getResources().getDrawable(R.drawable.ic_import_contacts_white_24dp);
        book.setColorFilter(new
                PorterDuffColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY));

        Drawable shoppingCart = getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp);
        shoppingCart.setColorFilter(new
                PorterDuffColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundResource(R.color.material_grey_100);
        tabLayout.setTabTextColors(0xFF3F51B5, 0xFF303F9F);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_import_contacts_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_shopping_cart_white_24dp);

        books = Database.getInstance().getBooks();

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
        }

        return super.onOptionsItemSelected(item);
    }

    public void search(View view) {

        searchBar = (EditText) findViewById(R.id.searchBar);
        searchResultsList = (ListView) findViewById(R.id.books);

        String query = searchBar.getText().toString();

        ArrayList<Book> queriedBooks = new ArrayList<>();

        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getTitle().contains(query)
                    || books.get(i).getAuthor().contains(query)
                    || books.get(i).getISBN().contains(query))
                queriedBooks.add(books.get(i));
        }

        searchResultsList.setAdapter(new BooksAdapter(this, queriedBooks));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BrowseFragment(), "Browse");
        adapter.addFragment(new MyBooksFragment(), "My Books");
        adapter.addFragment(new CartFragment(), "Cart");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}
