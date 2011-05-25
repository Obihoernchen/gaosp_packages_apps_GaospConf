package com.android.gaospconf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class conf extends PreferenceActivity {	

	private CheckBoxPreference Swap;
	private CheckBoxPreference Renice;
	private CheckBoxPreference Bootanimation;
	private CheckBoxPreference Provisionned;
	private CheckBoxPreference SSHServer;
	private CheckBoxPreference VNC;
	private EditTextPreference Swappiness;
	private EditTextPreference SDReadCache;
	private EditTextPreference LCDDensity;
	private EditTextPreference Wifi;
	private EditTextPreference Mem1;
	private EditTextPreference Mem2;
	private EditTextPreference Mem3;
	private EditTextPreference Mem4;
	private EditTextPreference Mem5;
	private EditTextPreference Mem6;
	private ListPreference CPUSampling;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // PreferenceView
        addPreferencesFromResource(R.xml.preferences);
        // Buttons
        setContentView(R.layout.buttons);

        // Links to Layout
        final Preference ClearCache = findPreference("Clear Cache");            
        final Preference Calibration = findPreference("GPS Calibration");           
        final Preference Servicemode = findPreference("Servicemode");   
        final Preference Gmail = findPreference("Gmail");  
        Swap = (CheckBoxPreference) findPreference("Swap");
        Renice = (CheckBoxPreference) findPreference("Renice");
        Bootanimation = (CheckBoxPreference) findPreference("Bootanimation");
        Provisionned = (CheckBoxPreference) findPreference("Provisionned");
        SSHServer = (CheckBoxPreference) findPreference("SSH Server");
        VNC = (CheckBoxPreference) findPreference("VNC");
        Swappiness = (EditTextPreference) findPreference("Swappiness");
        SDReadCache = (EditTextPreference) findPreference("SD Read Cache");
        LCDDensity = (EditTextPreference) findPreference("LCD Density");
        Wifi = (EditTextPreference) findPreference("Wifi Scan");
        Mem1 = (EditTextPreference) findPreference("Mem1");
        Mem2 = (EditTextPreference) findPreference("Mem2");
        Mem3 = (EditTextPreference) findPreference("Mem3");
        Mem4 = (EditTextPreference) findPreference("Mem4");
        Mem5 = (EditTextPreference) findPreference("Mem5");
        Mem6 = (EditTextPreference) findPreference("Mem6");
        CPUSampling = (ListPreference) findPreference("CPU Sampling");
        final ListPreference Presets = (ListPreference) findPreference("Presets"); 
        final Button Default_Button = (Button) findViewById(R.id.defaults);
        final Button Apply_Button = (Button) findViewById(R.id.apply);        
        
        // Open config file
        String[] result = new String[3];
        String record = null;
        BufferedReader BR = null;
        try {
        	BR = new BufferedReader(new FileReader("/system/etc/gaosp.conf"), 8192);
        }
        catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
		// Read config file and set preferences
		try {
			while ((record = BR.readLine()) != null) {
				if (Parser.parse(record, result)) {
					if (result[1].equals("cpu_sampling="))
						CPUSampling.setValueIndex(Integer.parseInt(result[2]));
					else if (result[1].equals("sshd"))
						SSHServer.setChecked(result[2].equals("yes"));
					else if (result[1].equals("renice"))
						Renice.setChecked(result[2].equals("yes"));
					else if (result[1].equals("provisionned"))
						Provisionned.setChecked(result[2].equals("yes"));
					else if (result[1].equals("vnc"))
						VNC.setChecked(result[2].equals("yes"));
					else if (result[1].equals("swap"))
						Swap.setChecked(result[2].equals("yes"));
					else if (result[1].equals("swappiness"))
						Swappiness.setText(result[2]);
					else if (result[1].equals("bootani"))
						Bootanimation.setChecked(result[2].equals("yes"));
					else if (result[1].equals("mem1"))
						Mem1.setText(Integer.toString(Integer.parseInt(result[2]) * 4 / 1024));
					else if (result[1].equals("mem2"))
						Mem2.setText(Integer.toString(Integer.parseInt(result[2]) * 4 / 1024));
					else if (result[1].equals("mem3"))
						Mem3.setText(Integer.toString(Integer.parseInt(result[2]) * 4 / 1024));
					else if (result[1].equals("mem4"))
						Mem4.setText(Integer.toString(Integer.parseInt(result[2]) * 4 / 1024));
					else if (result[1].equals("mem5"))
						Mem5.setText(Integer.toString(Integer.parseInt(result[2]) * 4 / 1024));
					else if (result[1].equals("mem6"))
						Mem6.setText(Integer.toString(Integer.parseInt(result[2]) * 4 / 1024));
					else if (result[1].equals("sdcache"))
						SDReadCache.setText(result[2]);
				}
			}
			BR.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// Open build.prop file (LCD Density)
		try {
			BR = new BufferedReader(new FileReader("/system/build.prop"), 8192);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Read build.prop file (LCD Density)
		try	{
			while ((record = BR.readLine()) != null) {
				if (Parser.parse(record, result)) {
					if (result[1].equals("ro.sf.lcd_density"))
						LCDDensity.setText(result[2]);
					else if (result[1].equals("wifi.supplicant_scan_interval"))
						Wifi.setText(result[2]);
				}
			}
			BR.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// Open scaling_governor file (CPU Sampling)
		try	{
			BR = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"), 8192);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		// Read scaling_governor file (CPU Sampling)
		try	{
			while ((record = BR.readLine()) != null)	{
				if (record.equals("ondemand"))
					CPUSampling.setEnabled(true);
			}
			BR.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
          
        // Custom preferences listener
		OnPreferenceClickListener prefClickListener = new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference)	{
				if (preference.equals(ClearCache)) {
					// Drop cache
					String[] dropcache =
					{
						"/system/xbin/su -c 'sync'",
						"/system/xbin/su -c 'echo 3 > /proc/sys/vm/drop_caches'",
						"echo Dropped cache"
					};
					shell.doExec(dropcache, true);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.equals(Calibration)) {
					// Delete old calibration file
					String[] delcalibration =
					{
						"/system/xbin/su -c 'rm /data/misc/akmd_set.txt'", "echo deleted akmd_set.txt"
					};
					shell.doExec(delcalibration, true);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.equals(Servicemode)) {
					// Open Servicemode app via dialer
					String encodedHash = Uri.encode("#");
					Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:*" + encodedHash + "*" + encodedHash + "197328640" + encodedHash + "*" + encodedHash + "*"));
					startActivity(intent);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.equals(Gmail)) {
					// Set provider to T-Mobile Austria
					String[] bypassgmail =
					{
						"/system/xbin/su -c 'setprop gsm.sim.operator.numeric 23203'",
						"/system/xbin/su -c 'killall com.android.venedig'",
						"/system/xbin/su -c 'rm -rf /data/data/com.android.vending/cache/*'",
						"echo bypassed GMail restriction"
					};
					shell.doExec(bypassgmail, true);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				return true;
			}
		};
		ClearCache.setOnPreferenceClickListener(prefClickListener);
		Calibration.setOnPreferenceClickListener(prefClickListener);
		Servicemode.setOnPreferenceClickListener(prefClickListener);
		Gmail.setOnPreferenceClickListener(prefClickListener);
        	
		// Memory thresholds preset and Swap listener
		OnPreferenceChangeListener prefChangeListener = new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (preference.equals(Presets)) {
					// Set memory thresholds
					final String val = newValue.toString();
	        		int index = Presets.findIndexOfValue(val);
					switch (index) {
	        		case 0:
						Mem1.setText("6");
						Mem2.setText("8");
						Mem3.setText("16");
						Mem4.setText("17");
						Mem5.setText("18");
						Mem6.setText("19");
						break;
					case 1:
						Mem1.setText("6");
						Mem2.setText("8");
						Mem3.setText("16");
						Mem4.setText("20");
						Mem5.setText("22");
						Mem6.setText("24");
						break;
					case 2:
						Mem1.setText("6");
						Mem2.setText("8");
						Mem3.setText("16");
						Mem4.setText("25");
						Mem5.setText("30");
						Mem6.setText("35");
						break;	
					case 3:
						Mem1.setText("6");
						Mem2.setText("8");
						Mem3.setText("16");
						Mem4.setText("25");
						Mem5.setText("30");
						Mem6.setText("35");
						break;
					case 4:
						Mem1.setText("6");
						Mem2.setText("8");
						Mem3.setText("16");
						Mem4.setText("22");
						Mem5.setText("24");
						Mem6.setText("30");
						break;
					case 5:
						Mem1.setText("6");
						Mem2.setText("8");
						Mem3.setText("16");
						Mem4.setText("36");
						Mem5.setText("40");
						Mem6.setText("40");
						break;			
					}
					return false;
				}
				else if (preference.equals(Swap)) {
					// Check Swappartition
					if (newValue.equals(true)) {
						if (new File("/dev/block/mmcblk1p2").exists() == false) {
							Toast.makeText(getBaseContext(), R.string.swapwarning, Toast.LENGTH_LONG).show();
							return false;
						}
						else
							return true;
					}
					else
						return true;
				}
				else
					return true;
			}
		};
		Presets.setOnPreferenceChangeListener(prefChangeListener);
		Swap.setOnPreferenceChangeListener(prefChangeListener);

        // Button listener
        View.OnClickListener clickListener = new View.OnClickListener() {
            public void onClick(View v) {
                // Default Button
            	if (v.equals(Default_Button)) {
            		CPUSampling.setValueIndex(2);
            		SSHServer.setChecked(false);
            		Renice.setChecked(true);
            		Provisionned.setChecked(true);
            		VNC.setChecked(false);
            		Swappiness.setText("15");
            		Swap.setChecked(false);
            		Bootanimation.setChecked(true);
            		LCDDensity.setText("160");
					SDReadCache.setText("128");
					Presets.setValueIndex(4);  	
					Wifi.setText("15");
					Toast.makeText(getBaseContext(), R.string.defaults, Toast.LENGTH_LONG).show();
            	}
        		// Apply Button
            	else if (v.equals(Apply_Button)) {
            		// Save settings and execute rc
            		new Task().execute();
            	}
            }
        };
	Default_Button.setOnClickListener(clickListener);
	Apply_Button.setOnClickListener(clickListener);
	}
	
	// Create Menu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
    	return true;
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
  		switch (item.getItemId()) {
  		case R.id.About:
  			// Print the about
    		AlertDialog.Builder about = new AlertDialog.Builder(this);
    		about.setTitle("About");
    		about.setMessage("GAOSP Configuration 3 by Obihoernchen");
    		about.setCancelable(true);
    		about.create().show();
    		break;
  		case R.id.Exit:
  			// Close
    		this.finish();
    		break;
    	}
        return true;
    }
    
	// AsyncTask to execute rc
	private class Task extends AsyncTask<String, Void, Void> {
		ProgressDialog PleaseWait;
		@Override
		protected void onPreExecute() {
	    	PleaseWait = ProgressDialog.show(conf.this, getString(R.string.waittitle), getString(R.string.wait), true, true);
	    }
	    @Override
	    protected Void doInBackground(String... params) {
	    	PrintWriter out = null;
        	
        	// Remount in read/write
        	String[] rw = { "/system/xbin/su -c /system/xbin/remountrw", "echo remount rw done"};
        	shell.doExec(rw, true);
        	
        	try {
				out  = new PrintWriter(new FileWriter("/system/etc/gaosp.conf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			out.println("################################");
			out.println("##### Gaosp config file ########");
			out.println("################################");
			out.println(" ");
							
			// Copy CPU sampling setting to conf file
			out.println("# CpuFreq sampling rate");
			out.println("# Set to 0 to eco mode, 1 to mixte mode, 2 to Performance mode ");
			out.println("cpu_sampling=" + CPUSampling.getValue());
			out.println(" ");
			
			// Copy SSH setting to conf file
			out.println("# SSH server");
			if (SSHServer.isChecked()) {
				out.println("sshd=yes");
			}
			else {
				out.println("sshd=no");
			}
			out.println(" ");
			
			// Copy inadyn setting to conf file
			out.println("# Inadyn");
			out.println("inadyn=no");
			out.println(" ");
			
			// Copy Renice setting to conf file
			out.println("# Renice");
			if (Renice.isChecked()) {
				out.println("renice=yes");
			}
			else {
				out.println("renice=no");
			}
			out.println(" ");
			
			// Copy Provisioned setting to conf file
			out.println("# Device provisionned");
			if (Provisionned.isChecked()) {
				out.println("provisionned=yes");
			}
			else {
				out.println("provisionned=no");
			}
			out.println(" ");
			
			// Copy VNC Server setting to conf file
			out.println("# VNC Server");
			if (VNC.isChecked()) {
				out.println("vnc=yes");
			}
			else {
				out.println("vnc=no");
			}
			out.println(" ");
			
			// Copy Swap setting to conf file
			out.println("# Swap");
			if (Swap.isChecked()) {
				out.println("swap=yes");
			}
			else {															
				out.println("swap=no");					
			}
			out.println(" ");
			
			// Copy Swappiness setting to conf file
			out.println("# Swappiness");
			out.println("swappiness="+Swappiness.getText());
			out.println(" ");
			
			// Copy Bootanimation setting to conf file
			out.println("# Bootanimation");
			if (Bootanimation.isChecked()) {
				out.println("bootani=yes");
				if (new File("/system/media/bootanimation.zip").length() > 3000000 ) {
			   		String[] bootchange = { "/system/xbin/su -c 'mv /system/media/bootanimation.zip /system/media/bootanimation_temp.zip'",
			   								"/system/xbin/su -c 'mv /system/media/bootanimation_old.zip /system/media/bootanimation.zip'",
			   								"/system/xbin/su -c 'mv /system/media/bootanimation_temp.zip /system/media/bootanimation_old.zip'",
			   								"echo Bootanimation changed"};
			   		shell.doExec(bootchange, true);
					}
			}
			else {
				out.println("bootani=no");
				if (new File("/system/media/bootanimation.zip").length() < 3000000 ) {
			   		String[] bootchange = { "/system/xbin/su -c 'mv /system/media/bootanimation.zip /system/media/bootanimation_temp.zip'",
			   								"/system/xbin/su -c 'mv /system/media/bootanimation_old.zip /system/media/bootanimation.zip'",
			   								"/system/xbin/su -c 'mv /system/media/bootanimation_temp.zip /system/media/bootanimation_old.zip'",
			   								"echo Bootanimation changed"};
			   		shell.doExec(bootchange, true);
					}
			}
			out.println(" ");
																			
			// Copy minfree settings to conf file
			out.println("# Minfree settings");
			out.println("mem1=" + Integer.parseInt(Mem1.getText())*1024/4);
			out.println("mem2=" + Integer.parseInt(Mem2.getText())*1024/4);
			out.println("mem3=" + Integer.parseInt(Mem3.getText())*1024/4);
			out.println("mem4=" + Integer.parseInt(Mem4.getText())*1024/4);
			out.println("mem5=" + Integer.parseInt(Mem5.getText())*1024/4);
			out.println("mem6=" + Integer.parseInt(Mem6.getText())*1024/4);
			out.println(" ");
								
			// Copy SD Cache setting to conf file
			out.println("# SD Read Cache");
			out.println("sdcache=" + SDReadCache.getText());
			out.println(" ");
			
			// Set LCD Density
			String[] lcdchange = { "density=`grep ro.sf.lcd_density= /system/build.prop | awk -F = '{print $2'}`",
								   "/system/xbin/su -c 'sed -i 's/ro.sf.lcd_density=$density/ro.sf.lcd_density="+LCDDensity.getText()+"/' /system/build.prop'",
								   "echo LCD Density changed"};
			shell.doExec(lcdchange, true);
			
			// Set Wifi scan interval
			String[] wifichange = { "wifi=`grep wifi.supplicant_scan_interval= /system/build.prop | awk -F = '{print $2'}`",
								   "/system/xbin/su -c 'sed -i 's/wifi.supplicant_scan_interval=$wifi/wifi.supplicant_scan_interval="+Wifi.getText()+"/' /system/build.prop'",
								   "echo Wifi scan interval changed"};
			shell.doExec(wifichange, true);
			
			// Close file
			out.flush();
			out.close();

			// Remount in read only
			String[] ro = { "/system/xbin/su -c /system/xbin/remountro", "echo remount ro done" };
        	shell.doExec(ro, true);
	    	
	    	String[] rc = { "/system/xbin/su -c /system/bin/rc", "echo rc executed" };
	    	shell.doExec(rc, true);
	    	return null;
	    }
	    @Override
	    protected void onPostExecute(Void unused) {    		
	    	if (PleaseWait.isShowing()) {
	    		PleaseWait.dismiss();
		       	Toast.makeText(getBaseContext(), R.string.applied, Toast.LENGTH_LONG).show();
	    	}
	    }
	}
}
