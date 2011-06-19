package com.android.gaospconf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ListAdapter;
import android.widget.Toast;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;

public class conf extends PreferenceActivity {	

	private CheckBoxPreference Swap;
	private CheckBoxPreference Bootanimation;
	private CheckBoxPreference Provisionned;
	private CheckBoxPreference SSHServer;
	private CheckBoxPreference VNC;
	private CheckBoxPreference IgnoreNiceLoad;
	private EditTextPreference Swappiness;
	private EditTextPreference Fudgeswap;
	private EditTextPreference SDReadCache;
	private EditTextPreference LCDDensity;
	private EditTextPreference Wifi;
	private EditTextPreference Mem1;
	private EditTextPreference Mem2;
	private EditTextPreference Mem3;
	private EditTextPreference Mem4;
	private EditTextPreference Mem5;
	private EditTextPreference Mem6;
	private EditTextPreference Bias;
	private EditTextPreference Threshold;	
	private ListPreference CPUSampling;
	private ProgressDialog DownloadProgress;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private String old_DensityValue;
	private String old_WifiValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // PreferenceView
        addPreferencesFromResource(R.xml.preferences);
        // Buttons
        setContentView(R.layout.buttons);

        // Links to Layout
        Swap = (CheckBoxPreference) findPreference("Swap");
        Bootanimation = (CheckBoxPreference) findPreference("Bootanimation");
        Provisionned = (CheckBoxPreference) findPreference("Provisionned");
        SSHServer = (CheckBoxPreference) findPreference("SSH Server");
        VNC = (CheckBoxPreference) findPreference("VNC");
        IgnoreNiceLoad = (CheckBoxPreference) findPreference("Ignore Nice Load");
        Swappiness = (EditTextPreference) findPreference("Swappiness");
        SDReadCache = (EditTextPreference) findPreference("SD Read Cache");
        LCDDensity = (EditTextPreference) findPreference("LCD Density");
        Fudgeswap = (EditTextPreference) findPreference("Fudgeswap");
        Wifi = (EditTextPreference) findPreference("Wifi Scan");
        Mem1 = (EditTextPreference) findPreference("Mem1");
        Mem2 = (EditTextPreference) findPreference("Mem2");
        Mem3 = (EditTextPreference) findPreference("Mem3");
        Mem4 = (EditTextPreference) findPreference("Mem4");
        Mem5 = (EditTextPreference) findPreference("Mem5");
        Mem6 = (EditTextPreference) findPreference("Mem6");
        Bias = (EditTextPreference) findPreference("Powersave Bias");
        Threshold = (EditTextPreference) findPreference("Up Threshold");
        CPUSampling = (ListPreference) findPreference("CPU Sampling");
        final ListPreference Presets = (ListPreference) findPreference("Presets");   
		
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
					if (result[1].equals("cpu_sampling"))
						CPUSampling.setValueIndex(Integer.parseInt(result[2]));
					else if (result[1].equals("sshd"))
						SSHServer.setChecked(result[2].equals("yes"));
					else if (result[1].equals("ignoreniceload"))
						IgnoreNiceLoad.setChecked(result[2].equals("yes"));
					else if (result[1].equals("threshold"))
						Threshold.setText(result[2]);
					else if (result[1].equals("bias"))
						Bias.setText(result[2]);
					else if (result[1].equals("provisionned"))
						Provisionned.setChecked(result[2].equals("yes"));
					else if (result[1].equals("vnc"))
						VNC.setChecked(result[2].equals("yes"));
					else if (result[1].equals("swap"))
						Swap.setChecked(result[2].equals("yes"));
					else if (result[1].equals("swappiness"))
						Swappiness.setText(result[2]);
					else if (result[1].equals("fudgeswap"))
						Fudgeswap.setText(Integer.toString(Integer.parseInt(result[2]) * 4));
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

		// Open build.prop file (LCD Density, wifi scan interval)
		try {
			BR = new BufferedReader(new FileReader("/system/build.prop"), 8192);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Read build.prop file (LCD Density, wifi scan interval)
		try	{
			while ((record = BR.readLine()) != null) {
				if (Parser.parse(record, result)) {
					if (result[1].equals("ro.sf.lcd_density")) {
						LCDDensity.setText(result[2]);
						old_DensityValue = result[2];
					}
					else if (result[1].equals("wifi.supplicant_scan_interval")) {
						Wifi.setText(result[2]);
						old_WifiValue = result[2];
					}
				}
			}
			BR.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// Open scaling_governor file (CPU governor settings)
		try	{
			BR = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"), 8192);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		// Read scaling_governor file (CPU governor settings)
		try	{
			while ((record = BR.readLine()) != null) {
				if (record.equals("ondemand")) {
					CPUSampling.setEnabled(true);
					Threshold.setEnabled(true);	
					Bias.setEnabled(true);
					IgnoreNiceLoad.setEnabled(true);
				}
			}
			BR.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
          
        // Custom preferences listener
		OnPreferenceClickListener prefClickListener = new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference)	{
				if (preference.getKey().equals("Clear Cache")) {
					// Drop cache
					String[] dropcache =
					{
						"sync",
						"echo 3 > /proc/sys/vm/drop_caches"
					};
					shell.doExec(dropcache, true);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.getKey().equals("Compass Calibration")) {
					// Delete old calibration file
					String[] delcalibration =
					{
						"rm /data/misc/akmd_set.txt"
					};
					shell.doExec(delcalibration, true);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.getKey().equals("Servicemode")) {
					// Open Servicemode app via dialer
					String encodedHash = Uri.encode("#");
					Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:*" + encodedHash + "*" + encodedHash + "197328640" + encodedHash + "*" + encodedHash + "*"));
					startActivity(intent);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.getKey().equals("Gmail")) {
					// Set provider to T-Mobile Austria
					String[] bypassgmail =
					{
						"setprop gsm.sim.operator.numeric 23203",
						"killall com.android.venedig",
						"rm -rf /data/data/com.android.vending/cache/*"
					};
					shell.doExec(bypassgmail, true);
					Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
				}
				else if (preference.getKey().equals("GPU RAM Hack")) {
					new DownloadFile().execute("http://obihoernchen.androidcodex.com/downloads/GAOSP_RAM_Hack_Kernel/boot.img");				
				}
				else {
					// Delete system app
					deleteSystemApp(preference.getTitle());
					preference.setEnabled(false);
				}
				return true;
			}
		};
		// Set custom preferences listener
		((Preference) findPreference("Clear Cache")).setOnPreferenceClickListener(prefClickListener);
		((Preference) findPreference("Compass Calibration")).setOnPreferenceClickListener(prefClickListener);
		((Preference) findPreference("Servicemode")).setOnPreferenceClickListener(prefClickListener);
		((Preference) findPreference("Gmail")).setOnPreferenceClickListener(prefClickListener);
		((Preference) findPreference("GPU RAM Hack")).setOnPreferenceClickListener(prefClickListener);
		// Set system apps listener
		final ListAdapter adapter = ((PreferenceScreen) findPreference("Systemapps")).getRootAdapter();
        for (int app = 0; app < adapter.getCount(); app++) {
        	Object object = adapter.getItem(app);
       		if (new File("/data/app_s/"+((Preference)object).getTitle()).exists()) {
       			((Preference)object).setEnabled(true);
       			((Preference)object).setOnPreferenceClickListener(prefClickListener);
        	}
        }
		
		// Memory thresholds preset and Swap listener
		OnPreferenceChangeListener prefChangeListener = new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (preference.equals(Presets)) {
					// Set memory thresholds
					switch (Presets.findIndexOfValue(newValue.toString())) {
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
            	if (v.getId() == (R.id.defaults)) {
            		CPUSampling.setValueIndex(2);
            		SSHServer.setChecked(false);
            		Provisionned.setChecked(true);
            		VNC.setChecked(false);
            		Swappiness.setText("15");
            		Fudgeswap.setText("2048");
            		Swap.setChecked(false);
            		Bootanimation.setChecked(true);
            		LCDDensity.setText("160");
					SDReadCache.setText("128");
					Presets.setValueIndex(4);  	
					Wifi.setText("15");
					Threshold.setText("35");
					Bias.setText("0");
					IgnoreNiceLoad.setChecked(true);
					Toast.makeText(getBaseContext(), R.string.defaults, Toast.LENGTH_LONG).show();
            	}
        		// Apply Button
            	else if (v.getId() == (R.id.apply)) {
            		// Save settings and execute rc
            		new Task().execute();
            	}
            }
        };
        ((Button) findViewById(R.id.defaults)).setOnClickListener(clickListener);
		((Button) findViewById(R.id.apply)).setOnClickListener(clickListener);
	}

	// Delete System apps
	private void deleteSystemApp(CharSequence appname) {
		String[] delete =
		{
			"/system/xbin/remountrw",
			"rm -rf /data/app_s/"+appname,
			"/system/xbin/remountro"
		};
		shell.doExec(delete, true);
		Toast.makeText(getBaseContext(), R.string.done, Toast.LENGTH_LONG).show();
	}
	
	// Create Menu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		((MenuInflater) getMenuInflater()).inflate(R.layout.menu, menu);
    	return true;
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
  		if (item.getItemId() == R.id.About) {
  			// Print the about
    		AlertDialog.Builder about = new AlertDialog.Builder(this);
    		about.setTitle("About");
    		about.setMessage("GAOSP Configuration 3 by Obihoernchen");
    		about.setCancelable(true);
    		about.create().show();
  		}
  		else if (item.getItemId() == R.id.Exit) {
  			// Close
    		finish();
    	}
        return true;
	}
    
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
            	DownloadProgress = new ProgressDialog(this);
            	DownloadProgress.setMessage("Downloading and flashing kernel...");
            	DownloadProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            	DownloadProgress.setCancelable(false);
            	DownloadProgress.show();
                return DownloadProgress;
            default:
                return null;
        }
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
        	String[] rw = { "/system/xbin/remountrw" };
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
			out.println("cpu_sampling=" + CPUSampling.getValue());
			out.println(" ");

			// Copy powersave bias setting to conf file
			out.println("# Powersave bias");
			out.println("bias=" + Bias.getText());
			out.println(" ");
			
			// Copy Ignore nice load setting to conf file
			out.println("# Ignore nice load");
			if (IgnoreNiceLoad.isChecked()) {
				out.println("ignoreniceload=yes");
			}
			else {
				out.println("ignoreniceload=no");
			}
			out.println(" ");
			
			// Copy threshold setting to conf file
			out.println("# Threshold");
			out.println("threshold=" + Threshold.getText());
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
			out.println("swappiness=" + Swappiness.getText());
			out.println(" ");
			
			// Copy Fudgeswap setting to conf file
			out.println("# Fusgeswap");
			out.println("fudgeswap=" + Integer.parseInt(Fudgeswap.getText())/4);
			out.println(" ");
			
			// Copy Bootanimation setting to conf file
			out.println("# Bootanimation");
			if (Bootanimation.isChecked()) {
				out.println("bootani=yes");
				if (new File("/system/media/bootanimation.zip").length() > 3000000 ) {
			   		String[] bootchange = { "mv /system/media/bootanimation.zip /system/media/bootanimation_temp.zip",
			   								"mv /system/media/bootanimation_old.zip /system/media/bootanimation.zip",
			   								"mv /system/media/bootanimation_temp.zip /system/media/bootanimation_old.zip"
			   							  };
			   		shell.doExec(bootchange, true);
				}
			}
			else {
				out.println("bootani=no");
				if (new File("/system/media/bootanimation.zip").length() < 3000000 ) {
			   		String[] bootchange = { "mv /system/media/bootanimation.zip /system/media/bootanimation_temp.zip",
			   								"mv /system/media/bootanimation_old.zip /system/media/bootanimation.zip",
			   								"mv /system/media/bootanimation_temp.zip /system/media/bootanimation_old.zip"
			   							  };
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
						
			// Close file
			out.flush();
			out.close();
			
			// Set LCD Density
			String[] lcdchange = { "sed -i 's/ro.sf.lcd_density="+old_DensityValue+"/ro.sf.lcd_density="+LCDDensity.getText()+"/' /system/build.prop" };
			shell.doExec(lcdchange, true);
			
			// Set Wifi scan interval
			String[] wifichange = { "sed -i 's/wifi.supplicant_scan_interval="+old_WifiValue+"/wifi.supplicant_scan_interval="+Wifi.getText()+"/' /system/build.prop" };
			shell.doExec(wifichange, true);

			// Remount in read only
			String[] ro = { "/system/xbin/remountro" };
        	shell.doExec(ro, true);
	    	
	    	String[] rc = { "/system/bin/rc" };
	    	shell.doExec(rc, true);
	    	return null;
	    }
	    @Override
	    protected void onPostExecute(Void unused) {    		
    		PleaseWait.dismiss();
	       	Toast.makeText(getBaseContext(), R.string.applied, Toast.LENGTH_LONG).show();
	    }
	}
	
	// AsyncTask to download and flash bootimage
	private class DownloadFile extends AsyncTask<String, String, String>{
       
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
		
		@Override
	    protected String doInBackground(String... path) {
	    	int count;
	    	try {
		    	// Download
	    		URL url = new URL(path[0]);
	            URLConnection connection = url.openConnection();
	            connection.connect();

	            int lenghtOfFile = connection.getContentLength();
	            
	            InputStream input = connection.getInputStream();
	            File bootimage = new File("/sdcard/","boot.img");
	            FileOutputStream output = new FileOutputStream(bootimage);
	            
	            byte buffer[] = new byte[1024];
	            long total = 0;
	            
                while ((count = input.read(buffer)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(buffer, 0, count);
                }
                
	            output.flush();
	            output.close();
	            input.close();
	            
	            // TODO: MD5 Check
	            
	            // Flash
				String[] flash =
				{
					"flash_image boot /sdcard/boot.img"
				};
				shell.doExec(flash, true);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return null;
	    }
		
		@Override
		protected void onProgressUpdate(String... progress) {
	        DownloadProgress.setProgress(Integer.parseInt(progress[0]));
	    }
		
		@Override
	    protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	    }
	}
}
