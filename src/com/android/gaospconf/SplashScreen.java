package com.android.gaospconf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
    protected int _splashTime = 1000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splash);
      new Handler().postDelayed(new Runnable(){
          public void run() {
            finish();
            startActivity(new Intent("com.android.gaospconf.splashscreen.conf"));
          }
      }, _splashTime);
    }
}