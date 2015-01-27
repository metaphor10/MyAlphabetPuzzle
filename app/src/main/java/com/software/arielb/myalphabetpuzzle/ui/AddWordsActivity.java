package com.software.arielb.myalphabetpuzzle.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.software.arielb.myalphabetpuzzle.R;
import com.software.arielb.myalphabetpuzzle.utils.ParseConstants;

import java.util.ArrayList;
import java.util.List;

public class AddWordsActivity extends Activity {
    public static final String TAG = GameScreenActivity.class.getSimpleName();

    ArrayList<String> words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
        }
        else {
            Log.i(TAG, currentUser.getUsername());
        }
        final ListView mListView=(ListView)findViewById(R.id.listView);
        words=new ArrayList<String>();

        ParseObject object=new ParseObject(ParseConstants.CLASS_WORDS);
         words.add("Cheese");
         words.add("Pepperoni");
        words.add("Black Olives");
        object.put(ParseConstants.KEY_WORD,words);
        object.saveInBackground();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,words);

        mListView.setAdapter(arrayAdapter);


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
        getMenuInflater().inflate(R.menu.menu_add_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
