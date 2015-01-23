package com.software.arielb.myalphabetpuzzle;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.software.arielb.myalphabetpuzzle.utils.ParseConstants;

/**
 * Created by arielb on 1/22/2015.
 */
public class AlphabetApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                "LFcdoinhzXvDAdd3X8aTIoUYFNaXNqf8keufB8Gk", //apllication ID
                "9aW46KBsujkMuMzd5hkHUTxwnSBFOWpsIOWRSuAg");//apllication key

        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //ParseAnalytics.trackAppOpened(getIntent());

    }
    public static void updateParseInstallation(ParseUser user){
        ParseInstallation installation=ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID,user.getObjectId());
        installation.saveInBackground();
    }
}
