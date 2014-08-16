package com.npes87184.wallpaperframe;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class FrameService extends Service {
	
	IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
	BroadcastReceiver mReceiver = new ReceiverScreen();
	private static final String KEY_CHECKBOX = "CHECK";
	private SharedPreferences prefs;
	
/*	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(!prefs.getBoolean(KEY_CHECKBOX, false)) {
			System.out.println("kill itself");
			stopSelf();
		}
		
		System.out.println("Start Service");
		try {
			registerReceiver(mReceiver, filter);
		} catch(Exception e) {
			
		}
	}*/

	@Override
    public void onCreate() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate();
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(!prefs.getBoolean(KEY_CHECKBOX, false)) {
			System.out.println("kill itself");
			stopSelf();
		}
		
		System.out.println("Start Service");
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
