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
				"sync",
				"echo 3 > /proc/sys/vm/drop_caches"
		};
		shell.doExec(dropcache, true);
    }
}
