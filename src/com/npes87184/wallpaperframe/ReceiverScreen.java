package com.npes87184.wallpaperframe;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class ReceiverScreen extends BroadcastReceiver {
	
	private WallpaperManager wallpaperManager;
	private int index = 0;
	private SharedPreferences prefs;
	private static final String KEY_CHOOSE_NUMBER = "choose_number";
	private static final String KEY_INDEX = "index";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		prefs = context.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS);
		index = prefs.getInt(KEY_INDEX, 0);
		int count = prefs.getInt(KEY_CHOOSE_NUMBER, 1);
		index = index%count;
		
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent intent1 = new Intent();
			intent1.setClass(context, FrameService.class);
			context.stopService(intent1);
			context.startService(intent1);
		} else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			wallpaperManager = WallpaperManager.getInstance(context);
			try{  
				File file = new File(prefs.getString(String.valueOf(index), "file:///android_asset/a.JPG"));
				InputStream iStream = new FileInputStream(file);
	            wallpaperManager.setStream(iStream);
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }
			index++;
			index = index%count;
			prefs.edit().putInt(KEY_INDEX, index).commit();
		}
		System.out.println("rece");
	}
}
