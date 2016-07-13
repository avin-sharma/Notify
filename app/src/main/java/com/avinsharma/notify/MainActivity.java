package com.avinsharma.notify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private AccessTokenTracker accessTokenTracker;
    private String profilePicUrl;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();
    Notifications currentNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        if (!isLoggedIn()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        setContentView(R.layout.activity_main);



        //For Reference see this https://github.com/roughike/BottomBar

        mBottomBar = BottomBar.attach(findViewById(R.id.container), savedInstanceState);
        mBottomBar.setMaxFixedTabs(3);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new GroupsListViewFragment())
                                .commit();

                }
                if (menuItemId == R.id.bottomBarItemTwo) {
                    // The user selected item number one.
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new Profile())
                            .commit();
                }
                if (menuItemId == R.id.bottomBarItemThree) {
                    // The user selected item number one.
                    getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new BottomNotificationFragment() )
                                .commit();
                }

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

        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    // set profile image to imageview using Picasso or Native methods

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
            LoginManager.getInstance().logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        accessTokenTracker.stopTracking();
        super.onDestroy();
    }


    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken == null) {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    public String getProfilePicUrl(){
        return profilePicUrl;
    }

    @Override
    public void onBackPressed() {
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setIcon(R.drawable.reminder_1x)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
    public Notifications getCurrentNotification() {
        return currentNotification;
    }

    public void setCurrentNotification(Notifications currentNotification) {
        this.currentNotification = currentNotification;
    }
}
