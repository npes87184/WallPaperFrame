package com.npes87184.wallpaperframe;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;

public class FrameService extends Service {
	
	IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
	BroadcastReceiver mReceiver = new ReceiverScreen();
	private static final String KEY_CHECKBOX = "CHECK";
	private SharedPreferences prefs;
	
	@Override
    public void onCreate() {
        prefs = getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS);
        super.onCreate();
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		System.out.println("Start FrameService");
		
		if(!prefs.getBoolean(KEY_CHECKBOX, false)) {
			System.out.println("kill itself");
			stopSelf();
		}
		
		try {
			registerReceiver(mReceiver, filter);
		} catch(Exception e) {
			
		}
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(mReceiver);
	}
	
    @Override  
    public IBinder onBind(Intent intent) {  
        return null;  
    }  
}
