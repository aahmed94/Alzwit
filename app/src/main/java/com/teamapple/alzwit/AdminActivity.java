package com.teamapple.alzwit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.teamapple.firebase.FirebaseMethods;
import com.teamapple.models.Notification;
import com.teamapple.validators.NotificationGuard;

import java.util.ArrayList;
import java.util.Date;

public class AdminActivity extends AppCompatActivity {

    private EditText mLabel, mDescription, mDate, mStartTime, mEndTime;
    private String label, description, date, startTime, endTime;
    private Date notificationDate;
    private Button btnSaveNotification;
    private ProgressBar mProgressBar;

    private FirebaseMethods firebaseMethods = new FirebaseMethods(AdminActivity.this);

    private NotificationGuard notificationGuard = new NotificationGuard();
    private ArrayList<String> errorMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        getControls();
        hideProgressBar();
        onClickSaveButton();
    }

    private void onClickSaveButton() {
        btnSaveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                    saveNotification();
            }
        });
    }

    private void saveNotification() {
        notificationDate = getDate(date);
        Date notStartTime = getTime(startTime);
        Date notEndTime = getTime(endTime);

        Notification newNot = new Notification(label, description, notificationDate, notStartTime, notEndTime);
        if(userInputIsValid(newNot)) {
            showProgressBar();
            firebaseMethods.addNotification(newNot);
            hideProgressBar();
            clearFields();
        }
        else{
            String errorsToPrint = prepareErrorsForView();
                   Toast.makeText(AdminActivity.this, errorsToPrint,
                        Toast.LENGTH_LONG).show();
        }
    }

    private void clearFields() {
        mLabel.setText("");
        mDescription.setText("");
        mDate.setText("");
        mStartTime.setText("");
        mEndTime.setText("");
    }

    /**
     * Prepare the list of errors to print
     *
     * @return The list of errors as single string
     */
    private String prepareErrorsForView() {
        StringBuilder errors = new StringBuilder();

        for (String error : errorMessages) {
            errors.append(error);
            errors.append("\n\r");
        }

        return errors.toString().trim();
    }

    private void getUserInput() {
        label = mLabel.getText().toString();
        description = mDescription.getText().toString();
        date = mDate.getText().toString();
        startTime = mStartTime.getText().toString();
        endTime = mEndTime.getText().toString();
    }

    private Date getDate(String dateString){
        String[] dateParts = dateString.split("/");
        if(dateParts.length==3)
        {
            int year = Integer.parseInt(dateParts[2]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[0]);

            return new Date(year, month, day);
        }

        return null;
    }

    private Date getTime(String timeString){
        String[] timeParts = timeString.split(":");
        if (timeParts.length == 2)
        {
            int hour = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);

            return new Date(notificationDate.getYear(), notificationDate.getMonth(), notificationDate.getDate(), hour, minutes);
        }

        return null;
    }

    private boolean userInputIsValid(Notification not)
    {
        errorMessages = notificationGuard.validateNotification(not);

        return errorMessages.size()==0;
    }

    /**
     * Hides the progress bar.
     */
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the progress bar.
     */
    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void getControls() {
        mLabel = findViewById(R.id.addNewNotificationLabel);
        mDescription = findViewById(R.id.addNewNotificationDescription);
        mDate = findViewById(R.id.addNewNotificationDate);
        mStartTime = findViewById(R.id.addNewNotificationStartTime);
        mEndTime = findViewById(R.id.addNewNotificationEndTime);
        btnSaveNotification = findViewById(R.id.addNewNotificationButton);
        mProgressBar = findViewById(R.id.addNewNotificationProgressBar);
    }
}
