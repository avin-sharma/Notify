package com.avinsharma.notify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class GroupsActivity extends AppCompatActivity {

    Notifications currentNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Fragment fragment = new GroupDetailsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.notification_container, new GroupDetailsFragment());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Log.v("GroupActivity","onbackpressed!! " + getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }


    public Notifications getCurrentNotification() {
        return currentNotification;
    }

    public void setCurrentNotification(Notifications currentNotification) {
        this.currentNotification = currentNotification;
    }
}
