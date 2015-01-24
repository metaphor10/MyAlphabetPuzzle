package com.software.arielb.myalphabetpuzzle;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.software.arielb.myalphabetpuzzle.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnDragListener, View.OnTouchListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<TextView> word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpened(getIntent());

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
        }
        else {
            Log.i(TAG, currentUser.getUsername());
        }
//        TextView textView=(TextView)findViewById(R.id.helloWorld);
//        textView.setOnTouchListener(this);
//        //textView.setOnDragListener(this);
//        textView.setText("L");
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
//        //textView.setOnDragListener(this);
//        TextView innerText=(TextView)findViewById(R.id.inside_linear_layout);
        word=new ArrayList<TextView>();
        String wordOfChoice="hello";
        Log.i(TAG,"before first loop");
        for (int i=0;i<wordOfChoice.length();i++){
            Log.i(TAG,"inside loop");
            TextView mFirst=new TextView(this);
            mFirst.setText("L");
            mFirst.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            mFirst.setOnTouchListener(this);
            word.add(mFirst);
        }


        RelativeLayout rl=(RelativeLayout)findViewById(R.id.main_activity_layout);
        //rl.addView(word.get(0));
        Log.i(TAG,"beofre second loop");
        for (int i=0;i<word.size();i++){
            rl.addView(word.get(i));
        }
        rl.setOnDragListener(this);
        //innerText.setOnDragListener(this);
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

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            String place=event.getLocalState().toString();
            Toast.makeText(MainActivity.this,place,Toast.LENGTH_LONG).show();
            //v.setVisibility(View.INVISIBLE);
            //TextView newView=(TextView)findViewById(R.id.inside_linear_layout);
            float yCoor =event.getY();
            float xCoor=event.getX();

           v.setX(xCoor);
            v.setY(yCoor);
            //oldText.invalidate();
            //newView.invalidate();


        }


        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null,shadowBuilder,v,0);
            //Toast.makeText(MainActivity.this,"touch",Toast.LENGTH_LONG).show();

        }



        return true;
    }
}
