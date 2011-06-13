package com.android.gaospconf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.os.Message;
import android.util.Log;

public class shell {
	public static void doExec(String[] commands, boolean suNeeded) {
		List<String> res = new ArrayList<String>();
		Process process = null;
		DataOutputStream os = null;
		DataInputStream osRes = null;
		Message msg = null;
		int i = 1;

		try {
			if (suNeeded) {
				// Getting Root ;)
				Log.i("GaospConf", "Starting exec of su");
				process = Runtime.getRuntime().exec("su");
			} else {
				Log.i("GaospConf", "Starting exec of sh");
				process = Runtime.getRuntime().exec("sh");
			}

			os = new DataOutputStream(process.getOutputStream());

			Log.i("GaospConf", "Starting command loop");
			for (String single : commands) {
				Log.i("GaospConf", "Executing [" + single + "]");
				os.writeBytes(single + "\n");
				os.flush();
				msg = Message.obtain();
				msg.arg1 = i++;
				msg.arg2 = -1; // This because when 0 i will dismiss the progressbar.
				Thread.sleep(200);
			}

			os.writeBytes("exit\n");
			os.flush();

			process.waitFor();
			msg = Message.obtain();
			msg.arg1 = 0;
			msg.arg2 = 0;

		} catch (Exception e) {
			Log.d("GaospConf","Unexpected error - Here is what I know: " + e.getMessage());
			e.printStackTrace();
			msg = Message.obtain();
			msg.arg1 = 1;
			msg.arg2 = 0;
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