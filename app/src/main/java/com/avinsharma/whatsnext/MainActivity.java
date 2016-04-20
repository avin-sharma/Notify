package com.avinsharma.whatsnext;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;


public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;


    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);


        //For Reference see this https://github.com/roughike/BottomBar

        mBottomBar = BottomBar.attach(findViewById(R.id.container), savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new GroupsListView())
                                .commit();

                    //TODO:Add icons and fragments/activities for switching between screens
                }
                /*if (menuItemId == R.id.bottomBarItemTwo) {
                    // The user selected item number one.
                    //TODO:Add icons and fragments/activities for switching between screens
                }
                if (menuItemId == R.id.bottomBarItemThree) {
                    // The user selected item number one.
                    //TODO:Add icons and fragments/activities for switching between screens
                }
                if (menuItemId == R.id.bottomBarItemFour) {
                    // The user selected item number one.
                    //TODO:Add icons and fragments/activities for switching between screens
                }*/
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                    //TODO:What to do when user clicks the item again like giving a toast message
                }
                if (menuItemId == R.id.bottomBarItemTwo) {
                    // The user selected item number one.
                    //TODO:What to do when user clicks the item again like giving a toast message
                }
                if (menuItemId == R.id.bottomBarItemThree) {
                    // The user selected item number one.
                    //TODO:What to do when user clicks the item again like giving a toast message
                }

            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.

        mBottomBar.mapColorForTab(0, "#7B1FA2");
        mBottomBar.mapColorForTab(1, "#7B1FA2");
        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


   /* @Override
    protected void onDestroy() {
        accessTokenTracker.stopTracking();
        super.onDestroy();
    }*/

    //TODO: OVERRIDE ON DESTROY FOR FACEBOOK ACCESS TOKEn


}
