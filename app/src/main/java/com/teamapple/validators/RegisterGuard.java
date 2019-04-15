package com.teamapple.validators;

import com.teamapple.models.EmergencyUser;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterGuard {

    public RegisterGuard() {
    }

    public ArrayList<String> validateUser(String password, String firstName, String lastName, String address, Date birtday, String phoneNumber, EmergencyUser emergencyUser){
        ArrayList<String> errorMessages = new ArrayList<>();

        if(password.isEmpty())
            errorMessages.add("Please enter password.");
        if(password.length()<6)
            errorMessages.add("Password must be at least 6 characters long.");
        if(firstName.isEmpty())
            errorMessages.add("Please enter first name.");
        if(lastName.isEmpty())
            errorMessages.add("Please enter last name.");
        if(address.isEmpty())
            errorMessages.add("Please enter address.");
        if(!birtday.before(new Date()))
            errorMessages.add("Please enter valid birtday.");
        if(phoneNumber.isEmpty())
            errorMessages.add("Please enter phone number.");

        if(!validateEmergencyUser(emergencyUser))
            errorMessages.add("Please enter valid emergency contact.");

        return errorMessages;
    }

    public boolean validateEmergencyUser(EmergencyUser emergencyUser) {
        if(emergencyUser.getFullName().isEmpty())
            return false;
        if(emergencyUser.getEmail().isEmpty())
            return false;
        if(emergencyUser.getPhoneNumber().isEmpty())
            return false;

        return true;
    }
}
