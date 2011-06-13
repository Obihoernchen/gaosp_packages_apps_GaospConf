package com.android.gaospconf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class shell {
	public static void doExec(String[] commands, boolean suNeeded) {
		List<String> res = new ArrayList<String>();
		Process process = null;
		DataOutputStream os = null;
		DataInputStream osRes = null;

		try {
			if (suNeeded) {
				// Getting Root ;)
				Log.i("GaospConf", "Starting exec of su");
				process = Runtime.getRuntime().exec("/system/xbin/su");
			} else {
				Log.i("GaospConf", "Starting exec of sh");
				process = Runtime.getRuntime().exec("/system/xbin/sh");
			}

			os = new DataOutputStream(process.getOutputStream());

			Log.i("GaospConf", "Starting command loop");
			for (String single : commands) {
				Log.i("GaospConf", "Executing [" + single + "]");
				os.writeBytes(single + "\n");
				os.flush();
				Thread.sleep(200);
			}

			os.writeBytes("exit\n");
			os.flush();

			process.waitFor();

		} catch (Exception e) {
			Log.d("GaospConf","Unexpected error - Here is what I know: " + e.getMessage());
			e.printStackTrace();
			res.add(e.getMessage());
		} finally {
			try {
				if (os != null)
					os.close();
				if (osRes != null)
					osRes.close();
				process.destroy();
			} catch (Exception e) {
				// nothing
			}
		}
	}
}