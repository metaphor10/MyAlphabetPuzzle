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
import java.util.Random;


public class MainActivity extends FragmentActivity implements View.OnDragListener, View.OnTouchListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    TextView currentText;
    ArrayList<TextView> word;
    ArrayList<TextView> target;
    int numberOfWords;
    Random mRandom;

    WindowManager wm;
    Display display;
    Point size;
    float screenWidth, screenHeight;
    private static final String IMAGEVIEW_TAG = "icon bitmap";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        mRandom=new Random();
        ParseAnalytics.trackAppOpened(getIntent());
        currentText=null;
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
        }
        else {
            Log.i(TAG, currentUser.getUsername());
        }
        wm = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();

        DisplayMetrics metrics=this.getResources().getDisplayMetrics();
        size = new Point();
        display.getSize(size);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
//        TextView textView=(TextView)findViewById(R.id.helloWorld);
//        textView.setOnTouchListener(this);
//        //textView.setOnDragListener(this);
//        textView.setText("L");
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
//        //textView.setOnDragListener(this);
//        TextView innerText=(TextView)findViewById(R.id.inside_linear_layout);
        word=new ArrayList<TextView>();
        target=new ArrayList<TextView>();
        String wordOfChoice="hello";
        Log.i(TAG,"before first loop");
        for (int i=0;i<wordOfChoice.length();i++){
            Log.i(TAG,"inside loop");
            TextView mFirst=new TextView(this);
            mFirst.generateViewId();
            mFirst.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            mFirst.setY(mRandom.nextInt(700));
            mFirst.setX(mRandom.nextInt(700));

           mFirst.setText(wordOfChoice.substring(i,i+1));

            mFirst.setOnTouchListener(this);


            word.add(mFirst);
        }
        numberOfWords=wordOfChoice.length();
        for (int i=0;i<wordOfChoice.length();i++){
            TextView mFirst=new TextView(this);
            mFirst.setId(mFirst.generateViewId());
            mFirst.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            mFirst.setText(wordOfChoice.substring(i,i+1));

            mFirst.setOnDragListener(this);
            target.add(mFirst);
        }
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.main_activity_layout);
        RelativeLayout.LayoutParams layoutParams;
        for (int y=0;y<target.size();y++){
            if (y==0){
                layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,R.id.main_activity_layout);
                rl.addView(target.get(0),layoutParams);
            }else {

                Log.i(TAG,"id"+target.get(y).generateViewId());


                layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                layoutParams.addRule(RelativeLayout.RIGHT_OF,target.get(y-1).getId());

                rl.addView(target.get(y),layoutParams);
            }
        }






        //rl.addView(word.get(0));
        Log.i(TAG,"beofre second loop");

        for (int i=0;i<word.size();i++){
           // RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)word.get(i).getLayoutParams();
            //layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            rl.addView(word.get(i));
        }
        //rl.setOnDragListener(this);
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
//            String place=event.getLocalState().toString();
//            Toast.makeText(MainActivity.this,place,Toast.LENGTH_LONG).show();
            //v.setVisibility(View.INVISIBLE);
            //TextView newView=(TextView)findViewById(R.id.inside_linear_layout);

            float yCoor =event.getY();
            float xCoor=event.getX();
            TextView temp=(TextView)v;
            Log.i(TAG,"view" + temp.getText()+"Send view "+currentText.getText());
            String temp1=temp.getText().toString();
            if (temp.getText().equals(currentText.getText()))
            {
                //Toast.makeText(MainActivity.this,"same",Toast.LENGTH_SHORT).show();
                currentText.setVisibility(View.GONE);
                if (numberOfWords>0){
                    numberOfWords--;
                    if (numberOfWords==0){
                        finish();
                    }
                }

            }
//           currentText.setY(yCoor);
//            currentText.setX(xCoor);
//            String number = "Y "+event.getY()+", X "+event.getX();
           // Toast.makeText(MainActivity.this,number,Toast.LENGTH_LONG).show();
//            currentText=null;
//           v.setX(xCoor);
//            v.setY(yCoor);
            //oldText.invalidate();
            //newView.invalidate();


        }


        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            String number="Y "+v.getY();
            number+="X "+v.getX();
            //Toast.makeText(MainActivity.this,number,Toast.LENGTH_LONG).show();
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            currentText=(TextView)v;
            //v.setVisibility(View.INVISIBLE);
            v.startDrag(null,shadowBuilder,v,0);
            //Toast.makeText(MainActivity.this,"touch",Toast.LENGTH_LONG).show();

        }



        return true;
    }
}
