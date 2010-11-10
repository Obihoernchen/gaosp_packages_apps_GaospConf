package com.android.gaospconf;

import android.util.Log;

public class NativeTask {
    static {
        try {
            Log.i("JNI", "Trying to load libRUN");
            System.load("/system/lib/librun.so");
            Log.i("JNI", "libRUN loaded");
        }
        catch (UnsatisfiedLinkError ule) {
            Log.e("JNI", "WARNING: Could not load /system/lib/libun.so");
        }
    }
    public static native int runCommand(String command);
}