package com.software.arielb.myalphabetpuzzle;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.software.arielb.myalphabetpuzzle.ui.AddWordsActivity;
import com.software.arielb.myalphabetpuzzle.ui.GameScreenActivity;
import com.software.arielb.myalphabetpuzzle.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends FragmentActivity{
    public static final String TAG = MainActivity.class.getSimpleName();
    Button mPlayButton;
    Button mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        mPlayButton=(Button)findViewById(R.id.playButton);
        mAddButton=(Button)findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddWordsActivity.class);
                startActivity(intent);
            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"insde button ");
                Intent intent=new Intent(MainActivity.this, GameScreenActivity.class);
                startActivity(intent);
            }
        });
        ParseAnalytics.trackAppOpened(getIntent());

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
        }
        else {
            Log.i(TAG, currentUser.getUsername());
        }

    }
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogin();
                break;
            case R.id.action_settings:

                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
