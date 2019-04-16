package com.teamapple.alzwit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teamapple.menu.Menu;

import java.net.PasswordAuthentication;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.toString();
    private static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Context context = this;
    private Button passportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        viewPassportOnClick();
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

    //Menu toggle button handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
