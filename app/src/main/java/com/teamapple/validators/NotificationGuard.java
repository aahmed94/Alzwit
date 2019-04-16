package com.teamapple.validators;

import com.teamapple.models.Notification;

import java.util.ArrayList;
import java.util.Date;

public class NotificationGuard {

    public ArrayList<String> validateNotification(Notification notification){
        ArrayList<String> errorMessages = new ArrayList<>();

        if(notification.getLabel().isEmpty())
            errorMessages.add("Please enter label.");
        if(notification.getDate().before(new Date()))
            errorMessages.add("This date has passed.");
        if(notification.getStartTime().after(notification.getEndTime()))
            errorMessages.add("Please enter valid timing.");

        return errorMessages;
    }

}
