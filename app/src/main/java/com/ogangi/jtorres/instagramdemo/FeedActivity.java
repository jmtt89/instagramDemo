package com.ogangi.jtorres.instagramdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.engine.InstagramKitConstants;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity implements MediaFragment.OnListFragmentInteractionListener, SearchView.OnQueryTextListener{

    private String search;
    private ImageView profileImage;
    private TextView username;
    private TextView fullName;
    private TextView bio;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InstagramEngine.getInstance(getApplicationContext()).getUserDetails(instagramUserResponseCallback);

        profileImage = (ImageView) findViewById(R.id.profile_image);
        username = (TextView) findViewById(R.id.username);
        fullName = (TextView) findViewById(R.id.full_name);
        bio = (TextView) findViewById(R.id.bio);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
        @Override
        public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "User:" + responseObject.getUsername() + ", User Id: " + responseObject.getId());

            username.setText(responseObject.getUsername());
            fullName.setText(responseObject.getFullName());
            bio.setText(responseObject.getBio());
            Picasso.with(getApplicationContext()).load(responseObject.getProfilePictureURL()).into(profileImage);

            Toast.makeText(getApplicationContext(), "Username: " + responseObject.getUsername(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };


    @Override
    public void onListFragmentInteraction(IGMedia media) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);


        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (findViewById(R.id.fragment_container) != null) {
            // Create a new Fragment to be placed in the activity layout
            MediaFragment fragment = MediaFragment.newInstance(2,query);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()>0){
            searchView.setSubmitButtonEnabled(true);
        }else{
            searchView.setSubmitButtonEnabled(false);
        }
        return false;
    }
}
