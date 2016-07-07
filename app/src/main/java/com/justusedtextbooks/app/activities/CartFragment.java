package com.justusedtextbooks.app.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.adapters.CartBooksAdapter;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.UserSession;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    private ListView booksInCartList;
    private TextView total;
    private View checkoutLayout;
    private ArrayList<Book> books;

    public CartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DecimalFormat df = new DecimalFormat("#0.00");

        books = UserSession.getInstance().getUser().getBooksInCart();
        total = (TextView) getView().findViewById(R.id.total);
        checkoutLayout = getView().findViewById(R.id.checkoutLayout);
        booksInCartList = (ListView) getView().findViewById(R.id.booksInCart);
        booksInCartList.setEmptyView(getView().findViewById(R.id.noBooksInCart));
        booksInCartList.setAdapter(new CartBooksAdapter(getActivity(), books));

        if(books.size() == 0)
            checkoutLayout.setVisibility(View.GONE);
        else
            checkoutLayout.setVisibility(View.VISIBLE);

        double tempTotal = 0;
        for(int i = 0; i < books.size(); i++)
            tempTotal += books.get(i).getPrice();

        tempTotal = Math.round(tempTotal * 100) / 100;

        total.setText("Total: $" + df.format(tempTotal));

    }

}
