package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.justusedtextbooks.app.Database;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.data_structures.User;
import com.justusedtextbooks.app.data_structures.UserSession;
import com.justusedtextbooks.app.adapters.BooksAdapter;

import java.util.ArrayList;

public class BrowseFragment extends Fragment {

    private ArrayList<Book> books;
    private ArrayList<User> users;
    private User currentUser;
    private ListView searchResultsList;

    public BrowseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currentUser = UserSession.getInstance().getUser();
        books = new ArrayList<>();
        users = Database.getInstance().getUsers();
        searchResultsList = (ListView) getView().findViewById(R.id.books);

        // create list of all the books other than the current user's books

        for(int i = 0; i < users.size(); i++) {
            for(int j = 0; j < users.get(i).getMyBooks().size(); j++) {
                if(users.get(i) != currentUser)
                    books.add(users.get(i).getMyBooks().get(j));
            }
        }

        // bind the list to the view

        searchResultsList.setAdapter(new BooksAdapter(getActivity(), books));
        searchResultsList.setEmptyView(getView().findViewById(R.id.resultsEmpty));
        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);

            }
        });

    }

}
