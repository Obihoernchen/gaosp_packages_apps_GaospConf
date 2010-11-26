package com.android.gaospconf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class conf extends Activity {
    /** Called when the activity is first created. */
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
              
        // Define variables
        String record = null;
        String record2 = null;
        String record3 = null;
                
        Boolean overclocking = false;
        Boolean overclocking2 = false;
        int cpu_sampling = 0;
        int sensors_sampling = 0;
        Boolean ssh = false;
        //Boolean inadyn = false;
        Boolean renice = false;
        Boolean prov = false;
        Boolean vnc = false;
        Boolean swap = false;
        Boolean bootani = false;
        Boolean gallery = false;
        
        // Define objects
        Button Default_Button = (Button) findViewById(R.id.defaults);
        Button Apply_Button = (Button) findViewById(R.id.apply);
        Button Service_Button = (Button) findViewById(R.id.service);
        Button Kitchen_Button = (Button) findViewById(R.id.kitchen);
        final CheckBox Check_sampling_eco = (CheckBox) findViewById(R.id.sampling01);
        final CheckBox Check_sampling_mix = (CheckBox) findViewById(R.id.sampling02);
        final CheckBox Check_sampling_perf = (CheckBox) findViewById(R.id.sampling03);
        final ToggleButton Toggle_SSH = (ToggleButton) findViewById(R.id.ssh);
        final ToggleButton Toggle_Renice = (ToggleButton) findViewById(R.id.renice);
        final ToggleButton Toggle_Prov = (ToggleButton) findViewById(R.id.Prov);
        final ToggleButton Toggle_VNC = (ToggleButton) findViewById(R.id.vnc);
        final ToggleButton Toggle_Swap = (ToggleButton) findViewById(R.id.swap);    
        final ToggleButton Toggle_OverClocking = (ToggleButton) findViewById(R.id.oc1);
        final ToggleButton Toggle_OverClocking2 = (ToggleButton) findViewById(R.id.oc2);
        final CheckBox Check_sensors_sampling_eco = (CheckBox) findViewById(R.id.sensors04);
        final CheckBox Check_sensors_sampling_mix = (CheckBox) findViewById(R.id.sensors05);
        final CheckBox Check_sensors_sampling_perf = (CheckBox) findViewById(R.id.sensors06);
        final ToggleButton Toggle_Bootani = (ToggleButton) findViewById(R.id.bootani);
        final ToggleButton Toggle_Gallery = (ToggleButton) findViewById(R.id.gallery);
        final EditText memory1_edit = (EditText) findViewById(R.id.memory1);
        final EditText memory2_edit = (EditText) findViewById(R.id.memory2);
        final EditText memory3_edit = (EditText) findViewById(R.id.memory3);
        final EditText memory4_edit = (EditText) findViewById(R.id.memory4);
        final EditText memory5_edit = (EditText) findViewById(R.id.memory5);
        final EditText memory6_edit = (EditText) findViewById(R.id.memory6);
        final EditText lcddensity = (EditText) findViewById(R.id.lcddensity);
        TextView Descswap = (TextView) findViewById(R.id.Textswap);
		TextView Descoverclock1 = (TextView) findViewById(R.id.Textoverclock1);
		TextView Descoverclock2 = (TextView) findViewById(R.id.Textoverclock2);
		TextView Descsampling = (TextView) findViewById(R.id.Textsampling);
		TextView Descsensors = (TextView) findViewById(R.id.Textsensors);
		TextView Descssh = (TextView) findViewById(R.id.Textssh);
		TextView Descrenice = (TextView) findViewById(R.id.Textrenice);
		TextView Descprovisionned = (TextView) findViewById(R.id.Textprovisionned);
		TextView Descvnc = (TextView) findViewById(R.id.Textvnc);
		TextView Descbootani = (TextView) findViewById(R.id.Textbootani);
		TextView Descgallery = (TextView) findViewById(R.id.Textgallery);
		TextView Descservice = (TextView) findViewById(R.id.Textservice);
		TextView Desckitchen = (TextView) findViewById(R.id.Textkitchen);
		TextView Descmemorythresholds = (TextView) findViewById(R.id.Textmemorythresholds);		
		TextView Descforegroundapp = (TextView) findViewById(R.id.Textforegroundapp);
		TextView Descvisibleapp = (TextView) findViewById(R.id.Textvisibleapp);
		TextView Descsecondaryapp = (TextView) findViewById(R.id.Textsecondaryapp);
		TextView Deschiddenapp = (TextView) findViewById(R.id.Texthiddenapp);
		TextView Desccontentapp = (TextView) findViewById(R.id.Textcontentapp);
		TextView Descemptyapp = (TextView) findViewById(R.id.Textemptyapp);
		TextView Desclcddensity = (TextView) findViewById(R.id.Textlcddensity);
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        
        // Open config file
        FileReader FR = null;
		try {
			FR = new FileReader("/system/etc/gaosp.conf");
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		BufferedReader BR = new BufferedReader(FR, 8192);
		
		// Read config file
		try {
			while ((record = BR.readLine()) != null) {
			    if (record.startsWith("cpu_sampling=")) {
			    	cpu_sampling = Integer.parseInt(record.substring(13));
			    }
			    if (record.equals("sshd=no")) {
			    	ssh = false;
			    }
			    if (record.equals("sshd=yes")) {
			    	ssh = true;
			    }
			    /*
			     * if (record.equals("inadyn=no")) {
			     * 	inadyn = false;
			     * }
			     * if (record.equals("inadyn=yes")) {
			     * 	inadyn = true;			    	
			     * }
			    */
			    if (record.equals("renice=no")) {
			    	renice = false;
			    }
			    if (record.equals("renice=yes")) {
			    	renice = true;
			    }
			    if (record.equals("provisionned=no")) {
			    	prov = false;
			    }
			    if (record.equals("provisionned=yes")) {
			    	prov = true;
			    }
			    if (record.equals("vnc=no")) {
			    	vnc = false;
			    }
			    if (record.equals("vnc=yes")) {
			    	vnc = true;
			    }
			    if (record.equals("swap=no")) {
			    	swap = false;
			    }
			    if (record.equals("swap=yes")) {
			    	swap = true;
			    }
			    if (record.equals("bootani=no")) {
			    	bootani = false;
			    }
			    if (record.equals("bootani=yes")) {
			    	bootani = true;
			    }
			    if (record.equals("gallery=no")) {
			    	gallery = false;
			    }
			    if (record.equals("gallery=yes")) {
			    	gallery = true;
			    }
			    if (record.equals("overclocking=yes")) {
			    	overclocking = true;
			    }
			    if (record.equals("overclocking=no")) {
			    	overclocking = false;
			    }
			    if (record.equals("overclocking2=yes")) {
			    	overclocking2 = true;
			    }
			    if (record.equals("overclocking2=no")) {
			    	overclocking2 = false;
			    }
			    if (record.startsWith("sensors_sampling=")) {
			    	sensors_sampling = Integer.parseInt(record.substring(17));
			    }    
			 }
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		// Open minfree file
		FileReader FR2 = null;
		try {
			FR2 = new FileReader("/sys/module/lowmemorykiller/parameters/minfree");
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} 
		BufferedReader BR2 = new BufferedReader(FR2, 8192);
			
		// Read minfree file
		try {
		    while ((record2 = BR2.readLine()) != null) {
		    	Pattern p = Pattern.compile(",");
		   	   	String[] mem = p.split(record2);
		    	memory1_edit.setText(Integer.toString(Integer.parseInt(mem[0])*4/1024));
		    	memory2_edit.setText(Integer.toString(Integer.parseInt(mem[1])*4/1024));
		    	memory3_edit.setText(Integer.toString(Integer.parseInt(mem[2])*4/1024));
		    	memory4_edit.setText(Integer.toString(Integer.parseInt(mem[3])*4/1024));
		    	memory5_edit.setText(Integer.toString(Integer.parseInt(mem[4])*4/1024));
		    	memory6_edit.setText(Integer.toString(Integer.parseInt(mem[5])*4/1024));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Open build.prop file (LCD Density)
		FileReader FR3 = null;
		try {
			FR3 = new FileReader("/system/build.prop");
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} 
		BufferedReader BR3 = new BufferedReader(FR3, 8192);
			
		// Read build.prop file (LCD Density)
		try {
		    while ((record3 = BR3.readLine()) != null) {
		    	if (record3.startsWith("ro.sf.lcd_density=")) {
		    		lcddensity.setText(record3.substring(18));
		    		}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Complete and validate the fields and other
		if (cpu_sampling == 0) {
			Check_sampling_eco.setChecked(true);
		}
		if (cpu_sampling == 1) {
			Check_sampling_mix.setChecked(true);
		}
		if (cpu_sampling == 2) {
			Check_sampling_perf.setChecked(true);
		}
		if (ssh == true) {
			Toggle_SSH.setChecked(true);
		}
		if (renice == true) {
			Toggle_Renice.setChecked(true);
		}
		if (prov == true) {
			Toggle_Prov.setChecked(true);
		}
		if (vnc == true) {
			Toggle_VNC.setChecked(true);
		}
		if (swap == true) {
			Toggle_Swap.setChecked(true);
		}
		if (bootani == true) {
			Toggle_Bootani.setChecked(true);
		}
		if (gallery == true) {
			Toggle_Gallery.setChecked(true);
		}
		if (overclocking == true) {
			Toggle_OverClocking.setChecked(true);
		}
		if (overclocking2 == true) {
			Toggle_OverClocking2.setChecked(true);
		}
		// Enable only 1 Overclocking option
		if (Toggle_OverClocking.isChecked()) {
			Toggle_OverClocking2.setChecked(false);
		}
		if (Toggle_OverClocking2.isChecked()) {
			Toggle_OverClocking.setChecked(false);
		}
		if (sensors_sampling == 0) {
			Check_sensors_sampling_eco.setChecked(true);
		}
		if (sensors_sampling == 1) {
			Check_sensors_sampling_mix.setChecked(true);
		}
		if (sensors_sampling == 2) {
			Check_sensors_sampling_perf.setChecked(true);
		}
		
		// TextView Listener (Descriptions)
		Descswap.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVswap);
    		alertbox.setMessage(R.string.swap);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });  
		Descoverclock1.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVoverclock1);
    		alertbox.setMessage(R.string.overclock1);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        }); 
		Descoverclock2.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVoverclock2);
    		alertbox.setMessage(R.string.overclock2);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        }); 
		Descsampling.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVsampling);
    		alertbox.setMessage(R.string.sampling);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });
		Descsensors.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVsensors);
    		alertbox.setMessage(R.string.sensors);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        }); 
		Descssh.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVssh);
    		alertbox.setMessage(R.string.ssh);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });   
		Descrenice.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVrenice);
    		alertbox.setMessage(R.string.renice);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });   
		Descprovisionned.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVprovisionned);
    		alertbox.setMessage(R.string.provisionned);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });   
		Descvnc.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVvnc);
    		alertbox.setMessage(R.string.vnc);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        }); 
		Descbootani.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVbootani);
    		alertbox.setMessage(R.string.bootani);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });
		Desclcddensity.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v){ 	
	    	alertbox.setTitle(R.string.TVlcddensity);
	    	alertbox.setMessage(R.string.lcddensity);
	        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface arg0, int arg1) {
	               	try {
	               	return; }
	               	catch (Throwable e) {
	               	}
	            }
	        });
	    	alertbox.show();
	    	}
	    });
		Descgallery.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v){ 	
		   	alertbox.setTitle(R.string.TVgallery);
		   	alertbox.setMessage(R.string.gallery);
		    alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
		       	public void onClick(DialogInterface arg0, int arg1) {
		           	try {
		           	return; }
		           	catch (Throwable e) {
		           	}
		        }
		    });
		   	alertbox.show();
		    }
		});
		Descservice.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVservicemode);
    		alertbox.setMessage(R.string.servicemode);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });   
		Desckitchen.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v){ 	
    		alertbox.setTitle(R.string.TVkitchen);
    		alertbox.setMessage(R.string.kitchen);
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try {
                	return; }
                	catch (Throwable e) {
                	}
                }
            });
    		alertbox.show();
    		}
        });
		Descmemorythresholds.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVmemorythresholds);
	    		alertbox.setMessage(R.string.memorythresholds);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		Descforegroundapp.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVforegroundapp);
	    		alertbox.setMessage(R.string.foregroundapp);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		Descvisibleapp.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVvisibleapp);
	    		alertbox.setMessage(R.string.visibleapp);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		Descsecondaryapp.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVsecondaryapp);
	    		alertbox.setMessage(R.string.secondaryapp);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		Deschiddenapp.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVhiddenapp);
	    		alertbox.setMessage(R.string.hiddenapp);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		Desccontentapp.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVcontentapp);
	    		alertbox.setMessage(R.string.contentapp);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		Descemptyapp.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){ 	
	    		alertbox.setTitle(R.string.TVemptyapp);
	    		alertbox.setMessage(R.string.emptyapp);
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                	try {
	                	return; }
	                	catch (Throwable e) {
	                	}
	                }
	            });
	    		alertbox.show();
	    		}
	    });
		
		// Button Listener
		Service_Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	            	String encodedHash = Uri.encode("#");
	                Intent intent = new Intent("android.intent.action.DIAL",  Uri.parse("tel:*"+encodedHash+"*"+encodedHash+"197328640"+encodedHash+"*"+encodedHash+"*"));
	                startActivity(intent);
	                // Thanks Samsung to code with your foot ;)
	                // Intent intent = new Intent();
	                // intent.setClassName("com.android.serviceModeApp","com.android.serviceModeApp.serviceModeApp");
	                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
		Kitchen_Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://kitchen.yaam.mobi/"));
				startActivity(intent);
            }
        });
		
		// Default Button
		Default_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Check_sampling_eco.setChecked(false);
            	Check_sampling_mix.setChecked(false);
				Check_sampling_perf.setChecked(true);
				Toggle_SSH.setChecked(false);
				Toggle_Renice.setChecked(true);
				Toggle_Prov.setChecked(true);
				Toggle_VNC.setChecked(false);
				Toggle_OverClocking.setChecked(false);
				Toggle_OverClocking2.setChecked(false);
				Check_sensors_sampling_eco.setChecked(true);
				Check_sensors_sampling_mix.setChecked(false);
				Check_sensors_sampling_perf.setChecked(false);
				Toggle_Swap.setChecked(false);
				Toggle_Bootani.setChecked(true);
				Toggle_Gallery.setChecked(false);
				lcddensity.setText("160");
				memory1_edit.setText("6");
		    	memory2_edit.setText("8");
		    	memory3_edit.setText("16");
		    	memory4_edit.setText("36");
		    	memory5_edit.setText("40");
		    	memory6_edit.setText("40");
				Toast.makeText(getBaseContext(), R.string.defaults, Toast.LENGTH_LONG).show();
            }
        });
		
		// Apply Button
		Apply_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
					int final_sampling = 0;	
					if (Check_sampling_eco.isChecked()) {
						final_sampling = 0;
					}
					if (Check_sampling_mix.isChecked()) {
						final_sampling = 1;
					}
					if (Check_sampling_perf.isChecked()) {
						final_sampling = 2;
					}
					out.println("# CpuFreq sampling rate");
					out.println("# Set to 0 to eco mode, 1 to mixte mode, 2 to Performance mode ");
					out.println("cpu_sampling=" + final_sampling);
					out.println(" ");
	
					// Copy SSH setting to conf file
					out.println("# SSH server");
					if (Toggle_SSH.isChecked()) {
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
					if (Toggle_Renice.isChecked()) {
						out.println("renice=yes");
					}
					else {
						out.println("renice=no");
					}
					out.println(" ");
					
					// Copy Provisioned setting to conf file
					out.println("# Device provisionned");
					if (Toggle_Renice.isChecked()) {
						out.println("provisionned=yes");
					}
					else {
						out.println("provisionned=no");
					}
					out.println(" ");
					
					// Copy VNC Server setting to conf file
					out.println("# VNC Server");
					if (Toggle_VNC.isChecked()) {
						out.println("vnc=yes");
					}
					else {
						out.println("vnc=no");
					}
					out.println(" ");
					
					// Copy Swap setting to conf file
					out.println("# Swap");
					if (Toggle_Swap.isChecked()) {
						out.println("swap=yes");
					}
					else {
						out.println("swap=no");
					}
					out.println(" ");
					
					// Copy Bootanimation setting to conf file
					out.println("# Bootanimation");
					if (Toggle_Bootani.isChecked()) {
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
					
					// Copy Gallery setting to conf file
					out.println("# Gallery");
					if (Toggle_Gallery.isChecked()) {
						out.println("gallery=yes");
						if (new File("/data/app_s/Gallery.apk").exists() == false) {
				    		String[] gallerychange1 = { "/system/xbin/su -c 'mv /data/app_s/Gallery3D.apk /data/app_s/Gallery3D.no'",
				    								   	"/system/xbin/su -c 'mv /data/app_s/Gallery.no /data/app_s/Gallery.apk'",
				    								   	"echo Gallery changed"};
				    		shell.doExec(gallerychange1, true);
							}
					}
					else {
						out.println("gallery=no");
						if (new File("/data/app_s/Gallery3D.apk").exists() == false) {
				    		String[] gallerychange2 = { "/system/xbin/su -c 'mv /data/app_s/Gallery3D.no /data/app_s/Gallery3D.apk'",
				    									"/system/xbin/su -c 'mv /data/app_s/Gallery.apk /data/app_s/Gallery.no'",
				    									"echo Gallery changed"};
				    		shell.doExec(gallerychange2, true);
							}		
					}
					out.println(" ");
					
					// Copy Overclocking setting to conf file
					out.println("# Overclocking");
					if (Toggle_OverClocking.isChecked()) {
						out.println("overclocking=yes");
					}
					else {
						out.println("overclocking=no");
					}
					out.println(" ");
					
					// Copy Overclocking2 setting to conf file
					out.println("# Overclocking2");
					if (Toggle_OverClocking2.isChecked()) {
						out.println("overclocking2=yes");
					}
					else {
						out.println("overclocking2=no");
					}
					out.println(" ");
					
					// Copy Sensors setting to conf file
					int final_sensors_sampling = 0;
					if (Check_sensors_sampling_eco.isChecked()) {
						final_sensors_sampling = 0;
					}
					if (Check_sensors_sampling_mix.isChecked()) {
						final_sensors_sampling = 1;
					}
					if (Check_sensors_sampling_perf.isChecked()) {
						final_sensors_sampling = 2;
					}
					out.println("# Sensors sampling rate");
					out.println("# Set to 0 to eco mode, 1 to mixte mode, 2 to Performance mode");
					out.println("sensors_sampling=" + final_sensors_sampling);
					out.println(" ");
					
					// Copy minfree settings to conf file				
					out.println("# Minfree settings");
					out.println("mem1=" + Integer.parseInt(memory1_edit.getText().toString())*1024/4);
					out.println("mem2=" + Integer.parseInt(memory2_edit.getText().toString())*1024/4);
					out.println("mem3=" + Integer.parseInt(memory3_edit.getText().toString())*1024/4);
					out.println("mem4=" + Integer.parseInt(memory4_edit.getText().toString())*1024/4);
					out.println("mem5=" + Integer.parseInt(memory5_edit.getText().toString())*1024/4);
					out.println("mem6=" + Integer.parseInt(memory6_edit.getText().toString())*1024/4);
					out.println(" ");
					
					// Set LCD Density
					String[] lcdchange = { "density=`grep ro.sf.lcd_density= /system/build.prop | awk -F = '{print $2'}`",
										   "/system/xbin/su -c 'sed -i 's/ro.sf.lcd_density=$density/ro.sf.lcd_density="+lcddensity.getText().toString()+"/' /system/build.prop'",
										   "echo LCD Density changed"};
					shell.doExec(lcdchange, true);
					
					// Executing rc with scrolling
					new Task().execute();
					
					// Close file
					out.flush();
					out.close();
					
					// Remount in read only
					String[] ro = { "/system/xbin/su -c /system/xbin/remountro", "echo remount ro done" };
            		shell.doExec(ro, true);         		
            }
        });
		// Check for dependences
		Check_sampling_eco.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					if (Check_sampling_eco.isChecked()) {
						Check_sampling_mix.setChecked(false);
						Check_sampling_perf.setChecked(false);
					}
			}
		});
		Check_sampling_mix.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					if (Check_sampling_mix.isChecked()) {
						Check_sampling_eco.setChecked(false);
						Check_sampling_perf.setChecked(false);
					}
			}
		});
		Check_sampling_perf.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					if (Check_sampling_perf.isChecked()) {
						Check_sampling_mix.setChecked(false);
						Check_sampling_eco.setChecked(false);
					}
			}
		});
		Check_sensors_sampling_eco.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					if (Check_sensors_sampling_eco.isChecked()) {
						Check_sensors_sampling_mix.setChecked(false);
						Check_sensors_sampling_perf.setChecked(false);
					}
			}
		});
		Check_sensors_sampling_mix.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					if (Check_sensors_sampling_mix.isChecked()) {
						Check_sensors_sampling_eco.setChecked(false);
						Check_sensors_sampling_perf.setChecked(false);
					}
			}
		});
		Check_sensors_sampling_perf.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					if (Check_sensors_sampling_perf.isChecked()) {
						Check_sensors_sampling_mix.setChecked(false);
						Check_sensors_sampling_eco.setChecked(false);
					}
			}
		});
		Toggle_Swap.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
				if (Toggle_Swap.isChecked()) {
					if (new File("/dev/block/mmcblk1p2").exists() == false) {
						Toast.makeText(getBaseContext(), R.string.swapwarning, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		Toggle_OverClocking.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
				if (Toggle_OverClocking.isChecked()) {
					Toggle_OverClocking2.setChecked(false);
				}
			}
		});		
		Toggle_OverClocking2.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
				if (Toggle_OverClocking2.isChecked()) {
					Toggle_OverClocking.setChecked(false);
				}
			}
		});
	}
			

	// Create Menu
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
    	MenuItem item = menu.add("About");
    	item = menu.add("Exit");
		return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
      {
         if (item.hasSubMenu() == false)
         {
           	if (item.getTitle() == "About") {
           		// Print the about
                AlertDialog.Builder dialogBuilder = new 
                AlertDialog.Builder(this);
                dialogBuilder.setTitle("About");
                dialogBuilder.setMessage("GAOSP Configuration 2 by drakaz and Obihoernchen");
                dialogBuilder.setCancelable(true);
            	dialogBuilder.create().show();
           	}
        	if (item.getTitle() == "Exit") {
        		this.finish();
        	}
         }
         // Consume the selection event.
         return true;
       }    
    
    final class Task extends UserTask<String, Void, Void> {
    	// Create ProgressDialog
    	ProgressDialog myProgressDialog;
    	protected void onPreExecute() {
    			 myProgressDialog = ProgressDialog.show(conf.this,"", getString(R.string.wait), true, true);
    	}
    	// Execute rc
    	@Override
    	protected Void doInBackground(String... params) {
    		String[] rc = { "/system/xbin/su -c /system/bin/rc", "echo rc executed" };
    		shell.doExec(rc, true);
			return null;
    	}
    	// Success message
    	protected void onPostExecute(Void unused) {
    			
    			if (myProgressDialog.isShowing()) {
    				myProgressDialog.dismiss();
	            	Toast.makeText(getBaseContext(), R.string.applied, Toast.LENGTH_LONG).show();
    			}
    	}
    }
}