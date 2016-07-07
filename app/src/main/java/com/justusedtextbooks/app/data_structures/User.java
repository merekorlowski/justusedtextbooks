package com.justusedtextbooks.app.data_structures;

import java.util.ArrayList;

/**
 * Created by merek on 25/06/16.
 */
public class User {

    private String fullName;
    private String email;
    private String username;
    private String password;
    private ArrayList<Book> myBooks;
    private ArrayList<Book> booksInCart;

    public User(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        myBooks = new ArrayList<>();
        booksInCart = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() { return email; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Book> getMyBooks() {
        return myBooks;
    }

    public ArrayList<Book> getBooksInCart() { return booksInCart; }

    public void addBook(Book book) {
        myBooks.add(book);
    }

    public void removeBook(int index) {
        myBooks.remove(index);
    }

    public void editBook(int index, Book book) {
        myBooks.set(index, book);
    }

    public void addBookToCart(Book book) { booksInCart.add(book); }

    public void removeBookFromCart(int index) { booksInCart.remove(index); }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

}
