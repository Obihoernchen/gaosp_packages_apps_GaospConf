package com.android.gaospconf;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WidgetConfigure extends Activity {

    private int appWidgetId;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		
        // get the appWidgetId of the appWidget being configured
        Intent launchIntent = getIntent();
        Bundle extras = launchIntent.getExtras();
        appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        // set the result for cancel first if the user cancels, then the appWidget
        Intent cancelResultValue = new Intent();
        cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, cancelResultValue);
        
        // show the user interface of configuration
        setContentView(R.layout.widgetpreferences);
        final Button OK_Button = (Button) findViewById(R.id.ok);
        final Button Cancel_Button = (Button) findViewById(R.id.cancel);
        
        // Button listener
        View.OnClickListener clickListener = new View.OnClickListener() {
        	public void onClick(View v) {
        		if (v.equals(OK_Button)) {        		
                        // change the result to OK
                        Intent resultValue = new Intent();
                        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                        setResult(RESULT_OK, resultValue);
                        // Close activity
                        finish();
                }
        		else if (v.equals(Cancel_Button)) {  
        			// Close activity
        			finish();      
        		}
        	}
        };
    	OK_Button.setOnClickListener(clickListener);
    	Cancel_Button.setOnClickListener(clickListener);
	}       	
}