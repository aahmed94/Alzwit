package com.teamapple.alzwit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.teamapple.firebase.FirebaseMethods;
import com.teamapple.models.Notification;
import java.util.Date;

public class AdminActivity extends AppCompatActivity {

    private EditText mLabel, mDescription, mDate, mStartTime, mEndTime;
    private String label, description, date, startTime, endTime;
    private Date notificationDate;
    private Button btnSaveNotification;

    private FirebaseMethods firebaseMethods = new FirebaseMethods(AdminActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        getControls();
        onClickRegisterButton();
    }

    private void onClickRegisterButton() {
        btnSaveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();

                if (true) {
                    saveNotification();
                    //resetFields();
                } else {
//                    String errorsToPrint = prepareErrorsForView();
//                    Toast.makeText(RegisterActivity.this, errorsToPrint,
//                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveNotification() {
        notificationDate = getDate(date);
        Date notStartTime = getTime(startTime);
        Date notEndTime = getTime(endTime);

        Notification newNot = new Notification(label, description, notificationDate, notStartTime, notEndTime);
        firebaseMethods.addNotification(newNot);
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

    private void getControls() {
        mLabel = findViewById(R.id.addNewNotificationLabel);
        mDescription = findViewById(R.id.addNewNotificationDescription);
        mDate = findViewById(R.id.addNewNotificationDate);
        mStartTime = findViewById(R.id.addNewNotificationStartTime);
        mEndTime = findViewById(R.id.addNewNotificationEndTime);
        btnSaveNotification = findViewById(R.id.addNewNotificationButton);
    }
}
