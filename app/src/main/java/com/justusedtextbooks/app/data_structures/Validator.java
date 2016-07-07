package com.justusedtextbooks.app.data_structures;

import com.justusedtextbooks.app.Database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by merek on 25/06/16.
 */
public class Validator {

    public Validator(){}

    public static boolean validateFullName(String fullName) {

        Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher fullNameMatcher = pattern.matcher(fullName);
        boolean fullNameOnlyContainsLetters = fullNameMatcher.find();

        if(fullName != null
                && fullName != ""
                && fullNameOnlyContainsLetters)
            return true;
        else
            return false;


    }

    public static boolean validateEmail(String email) {
        if((email.endsWith(".com")
                || email.endsWith(".ca"))
                && email.contains("@"))
            return true;
        else
            return false;

    }

    public static boolean validateUsername(String username) {

        Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher usernameMatcher = pattern.matcher(username);
        boolean usernameContainsSpecialCharacters = usernameMatcher.find();

        if(username != null
                && username.length() <= 12
                && username.length() >= 6
                && ((username.charAt(0) >= 'A'
                && username.charAt(0) <= 'Z')
                || username.charAt(0) >= 'a'
                && username.charAt(0) <= 'z')
                && !usernameContainsSpecialCharacters)
            return true;
        else
            return false;

    }

    public static boolean validatePassword(String password) {

        if(password != null
                && password.length() <= 12
                && password.length() >= 6
                && (password.contains("0")
                || password.contains("1")
                || password.contains("2")
                || password.contains("3")
                || password.contains("4")
                || password.contains("5")
                || password.contains("6")
                || password.contains("7")
                || password.contains("8")
                || password.contains("9")))
            return true;
        else
            return false;

    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {

        if(confirmPassword != null && password.equals(confirmPassword))
            return true;
        else
            return false;

    }

    public static boolean validateLogin(String username, String password) {
        UserSession session = UserSession.getInstance();
        User user = Database.getInstance().getUser(username, password);
        if(user != null) {
            session.setUser(user);
            return true;
        } else {
            return false;
        }
    }


}
