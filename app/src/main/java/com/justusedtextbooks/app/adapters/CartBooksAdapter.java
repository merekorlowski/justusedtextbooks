package com.justusedtextbooks.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.R;
import com.justusedtextbooks.app.data_structures.UserSession;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by merek on 26/06/16.
 */
public class CartBooksAdapter extends ArrayAdapter<Book> {

    public CartBooksAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int index = position;

        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_book, parent, false);
        }

        DecimalFormat df = new DecimalFormat("#.00");

        ListView cartBookList = (ListView) convertView.findViewById(R.id.booksInCart);

        ImageView cartBookImage = (ImageView) convertView.findViewById(R.id.cartBookImage);
        TextView cartBookTitle = (TextView) convertView.findViewById(R.id.cartBookTitle);
        TextView cartBookAuthor = (TextView) convertView.findViewById(R.id.cartBookAuthor);
        TextView cartBookPrice = (TextView) convertView.findViewById(R.id.cartBookPrice);
        ImageView removeFromCart = (ImageView) convertView.findViewById(R.id.removeFromCart);

        cartBookImage.setImageBitmap(book.getImage());
        cartBookTitle.setText(book.getTitle());
        cartBookAuthor.setText("by " + book.getAuthor());
        cartBookPrice.setText("$" + df.format(book.getPrice()));

        removeFromCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ArrayList<Book> cartBooks = UserSession.getInstance().getUser().getBooksInCart();
                cartBooks.remove(index);
                notifyDataSetChanged();
            }

        });

        return convertView;
    }

}
