package com.justusedtextbooks.app.data_structures;

import android.graphics.Bitmap;

/**
 * Created by merek on 25/06/16.
 */
public class Book {

    private User user;
    private Bitmap image;
    private String title;
    private String author;
    private String ISBN;
    private String courseCode;
    private double price;
    private String description;

    public Book(User user, Bitmap image, String title, String author, String ISBN, String courseCode, double price, String description) {
        this.user = user;
        this.image = image;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.courseCode = courseCode;
        this.price = price;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getCourseCode() { return courseCode; }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double newPrice) {
        price = newPrice;
    }

}
