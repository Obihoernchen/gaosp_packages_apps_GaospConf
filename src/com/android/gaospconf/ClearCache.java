package com.android.gaospconf;

import android.app.IntentService;
import android.content.Intent;

public class ClearCache extends IntentService {
	public ClearCache() {
		super("ClearCache");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
    	// Drop cache
		String[] dropcache =
		{
				"/system/xbin/su -c 'sync'",
				"/system/xbin/su -c 'echo 3 > /proc/sys/vm/drop_caches'",
				"echo Dropped cache"
		};
		shell.doExec(dropcache, true);
    }
}
