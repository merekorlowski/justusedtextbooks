package com.justusedtextbooks.app.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.justusedtextbooks.app.activities.EditBookActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by merek on 26/06/16.
 */
public class MyBooksAdapter extends ArrayAdapter<Book> {

    public MyBooksAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int index = position;

        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_book, parent, false);
        }

        DecimalFormat df = new DecimalFormat("#.00");

        ListView myBookList = (ListView) convertView.findViewById(R.id.myBooks);

        ImageView myBookImage = (ImageView) convertView.findViewById(R.id.myBookImage);
        TextView myBookTitle = (TextView) convertView.findViewById(R.id.myBookTitle);
        TextView myBookAuthor = (TextView) convertView.findViewById(R.id.myBookAuthor);
        TextView myBookPrice = (TextView) convertView.findViewById(R.id.myBookPrice);
        ImageView deleteMyBook = (ImageView) convertView.findViewById(R.id.deleteMyBook);
        ImageView editMyBook = (ImageView) convertView.findViewById(R.id.editMyBook);

        myBookImage.setImageBitmap(book.getImage());
        myBookTitle.setText(book.getTitle());
        myBookAuthor.setText("by " + book.getAuthor());
        myBookPrice.setText("$" + df.format(book.getPrice()));

        deleteMyBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ArrayList<Book> myBooks = UserSession.getInstance().getUser().getMyBooks();
                myBooks.remove(index);
                notifyDataSetChanged();
            }

        });

        editMyBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditBookActivity.class);
                intent.putExtra("myBookIndex", index);
                getContext().startActivity(intent);
                notifyDataSetChanged();
            }

        });

        return convertView;
    }

}
