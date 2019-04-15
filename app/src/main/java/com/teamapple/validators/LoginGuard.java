package com.teamapple.validators;

import java.util.ArrayList;

public class LoginGuard {

    public ArrayList<String> validateLogin(String username, String password){
        ArrayList<String> errorMessages = new ArrayList<>();

        if(username.isEmpty())
            errorMessages.add("Please enter username.");
        if(password.isEmpty())
            errorMessages.add("Please enter password.");

        return errorMessages;
    }
}
