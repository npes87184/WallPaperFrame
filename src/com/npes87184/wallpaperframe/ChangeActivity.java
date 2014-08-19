package com.npes87184.wallpaperframe;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class ChangeActivity extends Service {
	
	private WallpaperManager wallpaperManager;
	private int index = 0;
	private SharedPreferences prefs;
	private static final String KEY_CHECKBOX = "CHECK";
	private long time = 10*1000;
	private static final String KEY_TIME = "time";
    private Timer timer;
    private TimerTask task;
    private boolean isStop = true;
	private static final String KEY_CHOOSE_NUMBER = "choose_number";
	private static final String KEY_INDEX = "index";
	 
	@Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        prefs = getSharedPreferences("prefs", MODE_MULTI_PROCESS);
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		System.out.println("Start ChangeService");
		time = prefs.getLong(KEY_TIME, 60*60*1000);
		System.out.println(time);
		index = prefs.getInt(KEY_INDEX, 0);
		final int count = prefs.getInt(KEY_CHOOSE_NUMBER, 1);
		index = index%count;
		
		if(!prefs.getBoolean(KEY_CHECKBOX, false)) {
			System.out.println("kill itself");
			stopSelf();
		}
		
        if (isStop) {
        	if (task == null) {
        		task = new TimerTask() {
        			@Override
                    public void run() {
                            // TODO Auto-generated method stub
        				wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        				System.out.println("want to change");
        				try{  
        					System.out.println("here");
        					File file = new File(prefs.getString(String.valueOf(index), "file:///android_asset/a.JPG"));
        					InputStream iStream = new FileInputStream(file);
        					wallpaperManager.setStream(iStream);
        				} catch(Exception ex) {  
        					System.out.println("WTF");
        				}
        				index++;
        				index = index%count;
        				prefs.edit().putInt(KEY_INDEX, index).commit();
                    }
        		};
        	}
        	timer.schedule(task, 0, time);
			isStop = false;
        }
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		isStop = true;
		task = null;
        timer.cancel();
	}
	
    @Override  
    public IBinder onBind(Intent intent) {  
        return null;  
    }  

}
