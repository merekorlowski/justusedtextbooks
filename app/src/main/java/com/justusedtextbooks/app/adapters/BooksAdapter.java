package com.justusedtextbooks.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BooksAdapter extends ArrayAdapter<Book> {
    public BooksAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book, parent, false);
        }
        DecimalFormat df = new DecimalFormat("#.00");

        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookImage);
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        TextView bookPrice = (TextView) convertView.findViewById(R.id.bookPrice);

        bookImage.setImageBitmap(book.getImage());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("by " + book.getAuthor());
        bookPrice.setText("$" + df.format(book.getPrice()));

        return convertView;
    }
}