package com.teamapple.alzwit;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.teamapple.menu.Menu;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.toString();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
    }

    private void setUpToolbar() {
            Toolbar toolbar = findViewById(R.id.toolbartop);
            setSupportActionBar(toolbar);
            mDrawerLayout = findViewById(R.id.nav_view);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            Menu menu= new Menu(this);
            menu.setUpMenu(mDrawerLayout, context);
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
