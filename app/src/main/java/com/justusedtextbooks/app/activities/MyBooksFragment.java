package com.justusedtextbooks.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.data_structures.UserSession;
import com.justusedtextbooks.app.adapters.MyBooksAdapter;

import java.util.ArrayList;

public class MyBooksFragment extends Fragment {

    private ArrayList<Book> myBooks;
    private ListView myBookList;
    private UserSession session;

    public MyBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_books, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton addBook = (FloatingActionButton) getView().findViewById(R.id.addBookBtn);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivity(intent);

            }
        });

        session = UserSession.getInstance();

        myBookList = (ListView) getView().findViewById(R.id.myBooks);
        ArrayList<Book> myBooks = session.getUser().getMyBooks();
        myBookList.setAdapter(new MyBooksAdapter(getActivity(), myBooks));

        myBookList.setEmptyView(getView().findViewById(R.id.myBooksEmpty));

    }

}
