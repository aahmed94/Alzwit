package com.teamapple.alzwit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamapple.menu.Menu;
import com.teamapple.models.Notification;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.toString();
    private static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Context context = this;
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    private String userID = FirebaseAuth.getInstance().getUid();
    private ArrayList<Notification> notificationList = new ArrayList<>();
    private Button passportButton;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        viewPassportOnClick();
        populateList();
       // Notification not = new Notification("Label", "Description", new Date(), new Date(), new Date());
      //  createNotificationChannel();
       // sendPushNotification(not);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notificationList.clear();
        populateList();
    }

    private void setUpToolbar() {
            Toolbar toolbar = findViewById(R.id.toolbartop);
            setSupportActionBar(toolbar);
            passportButton = findViewById(R.id.viewPassport);
            mDrawerLayout = findViewById(R.id.mainActivityDrawer);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            Menu menu= new Menu(this);
            menu.setUpMenu(mDrawerLayout, context);
        }
    private void viewPassportOnClick() {
        passportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PassportActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alzwit task is waiting!";
            String description = "Alzwit pending tasks.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Alzwit", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void populateList() {
        mRef.child("notifications").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        notificationList.add(ds.getValue(Notification.class));
                        Log.d("THIS IS ME", dataSnapshot.toString());
                    }
                    setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("This is the list", notificationList.toString());
    }

    private void setupRecyclerView() {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,1);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            CardViewAdapter cardViewAdapter = new CardViewAdapter(notificationList, context);
            recyclerView.setAdapter(cardViewAdapter);
    }

    public void sendPushNotification(Notification card){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Alzwit")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(card.getLabel())
                .setContentText(card.getDescription())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(card.getDescription()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    //Menu toggle button handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
