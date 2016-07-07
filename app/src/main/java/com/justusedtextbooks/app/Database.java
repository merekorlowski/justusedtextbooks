package com.justusedtextbooks.app;

import com.justusedtextbooks.app.data_structures.Book;
import com.justusedtextbooks.app.data_structures.User;

import java.util.ArrayList;

/**
 * Created by merek on 25/06/16.
 */
public class Database {

    private ArrayList<User> users;
    private static Database instance = null;

    private Database() {
        users = new ArrayList<>();

        User merek = new User("Merek Orlowski", "morlo088@uottawa.ca", "morlo088", "Woodlergh17!");
       // merek.addBook(new Book(merek, , "Mécanique pour ingénieurs", "Ferdinand P. Beer & E. Russell Johnston, Jr. & David F.Mazurek & Elliot R. Eisenberg", "978-2-7651-0620-3", 50.00, "In very good condition."));
        User sandra = new User("Sandra Brancatelli", "sbran037@uottawa.ca", "sbran037", "password1!");
       // sandra.addBook(new Book(sandra, R.drawable.physique1, "Physique 1", "Harris Benson", "978-2-7613-2546-2", 30.00, "Great condition, very useful book."));
        User gerardo = new User("Gerardo Perez", "gpere038@uottawa.ca", "gpere038", "password1!");
        //gerardo.addBook(new Book(gerardo, R.drawable.physique2, "Physique 2", "Harris Benson", "978-2-7613-2547-9", 30.00, "Very good condition. Great price."));
        User rodolfo = new User("Rodolfo Vilchis", "rvilc085@uottawa.ca", "rvilc085", "password1!");
        //rodolfo.addBook(new Book(rodolfo, R.drawable.computer, "Computer Science", "Pearson custom", "978-1-256-18914-5", 25.00, "Mint condition."));

        users.add(merek);
        users.add(sandra);
        users.add(gerardo);
        users.add(rodolfo);

    }

    public static Database getInstance() {
        if(instance == null)
            instance = new Database();

        return instance;
    }

    public User getUser(String username, String password) {

        User user = null;

        for(int i = 0; i < users.size(); i++) {
            if(username.equals(users.get(i).getUsername()) && password.equals(users.get(i).getPassword()))
                user = users.get(i);
        }

        return user;
    }

    public ArrayList<Book> getBooks() {

        ArrayList<Book> books = new ArrayList<>();

        for(int i = 0; i < users.size(); i++) {
            for(int j = 0; j < users.get(i).getMyBooks().size(); j++) {
                if(users.get(i).getMyBooks().get(j) != null)
                    books.add(users.get(i).getMyBooks().get(j));
            }
        }

        return books;

    }

    public boolean addUser(User user) {

        boolean exists = false;

        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername() == user.getUsername()
                    || users.get(i).getEmail() == user.getEmail())
                exists = true;
        }

        if(!exists)
            users.add(user);

        return exists;

    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
