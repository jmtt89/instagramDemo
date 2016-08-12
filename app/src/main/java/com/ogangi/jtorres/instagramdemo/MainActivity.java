package com.ogangi.jtorres.instagramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.instagram.instagramapi.activities.InstagramAuthActivity;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.widgets.InstagramLoginButton;

public class MainActivity extends AppCompatActivity {

    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.PUBLIC_ACCESS};

        InstagramLoginButton instagramLoginButton = (InstagramLoginButton) findViewById(R.id.instagramLoginButton);
        instagramLoginButton.setInstagramLoginCallback(instagramLoginCallbackListener);


        //if you dont specify scopes, you will have basic access.
        instagramLoginButton.setScopes(scopes);
    }

    InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
        @Override
        public void onSuccess(IGSession session) {
            Toast.makeText(getApplicationContext(), "Wow!!! User trusts you :) ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(intent);
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Oh Crap!!! Canceled.", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onError(InstagramException error) {
            Toast.makeText(getApplicationContext(), "User does not trust you :(\n " + error.getMessage(), Toast.LENGTH_LONG).show();

        }
    };

}
