package com.teamapple.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.teamapple.alzwit.About;
import com.teamapple.alzwit.AdminActivity;
import com.teamapple.alzwit.LoginActivity;
import com.teamapple.alzwit.R;

import static android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;

public class Menu {

    private Button signOutMenuButton;
    private Activity activity;
    private Context context;

    public Menu(Activity activity) {
        this.activity = activity;
    }

    public void setUpMenu(final DrawerLayout drawerLayout, final Context context){

        this.context = context;

        NavigationView navigationView = activity.findViewById(R.id.nav_view);

        signOutMenuButton = activity.findViewById(R.id.MenuSignOut);
        signOutMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, LoginActivity.class);

                //Clearing the back button stack, so that user cannot click on back button after signing out
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                activity.finish();
            }
        });

        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // set item as selected to persist highlighted
                item.setChecked(true);

                // close drawer when item is tapped
                drawerLayout.closeDrawers();

                // Code here to update the UI based on the item selected
                // For example, swap UI fragments here

                switch(item.getItemId()){
                    case R.id.AdminPanel:
                        Intent intent = new Intent(context, AdminActivity.class);
                        context.startActivity(intent);
                        break;

                    case R.id.About:
                        Intent intent2 = new Intent(context, About.class);
                        context.startActivity(intent2);
                        break;
                }

                return true;
            }
        });
    }

}
