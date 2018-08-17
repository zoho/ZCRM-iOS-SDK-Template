package com.crm.sample_contacts_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zoho.crm.sdk.android.zcrmandroid.activity.ZCRMBaseActivity;


public class HomeActivity extends ZCRMBaseActivity implements DataHandler{

    private ImageView myImage;
    private TextView userNameView;
    private LinearLayout contactsView;
    private LinearLayout tasksView;
    private static DataProvider dataProvider = DataProvider.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        loadViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page, menu);
//        loadViews();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.signout:
                new AlertDialog.Builder(this)
                        .setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                            }
                        })
                        .show();
                return true;
            default:
                return false;
        }
    }



    public void logout()
    {
        super.logout(new OnLogoutListener() {
            @Override
            public void onLogoutSuccess() {
                System.out.println(">> Logout Success");
            }

            @Override
            public void onLogoutFailed() {
                System.out.println(">> Logout Failed");
            }
        });
    }

    private void loadViews() {


        myImage =  findViewById(R.id.imageView);
        userNameView = findViewById(R.id.userName);
        contactsView = (LinearLayout)  findViewById(R.id.contactsRecords);
        tasksView = (LinearLayout)  findViewById(R.id.tasksRecords);
        LinearLayout linearLayout = findViewById(R.id.mainView);
        if (linearLayout == null){
            System.out.println();
        }
        if (contactsView == null) {
            System.out.println();
        }
        if (tasksView == null) {
            System.out.println();
        }
        setOnclickListener();

    }


    private void setOnclickListener() {

        contactsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity("Contacts");
            }
        });

        tasksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity("Tasks");
            }
        });

        dataProvider.getUserImage(this);
    }

    private void startActivity(String moduleApiName) {

        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.putExtra("module", moduleApiName);
        startActivity(intent);

    }



    public void setUserImage(final Bitmap bMap) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (bMap != null) {
                    myImage.setImageBitmap(bMap);
                } else {
                    myImage.setImageResource(R.drawable.ic_user);
                }
            }
        });
        dataProvider.getUserName(this);
    }

    public void setUserName(final String userName) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String userNameWithMsg = "Welcome " + userName + "!";
                userNameView.setText(userNameWithMsg);
            }
        });
        dataProvider.getOrganisationName(this);
    }


    public void setOrganizationName(final String orgName) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setTitle(orgName);
            }
        });
    }

}
