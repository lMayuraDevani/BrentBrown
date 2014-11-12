package com.interactive360.brentbrown;

import credentials.commons;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		SharedPreferences preferences = getSharedPreferences(
				commons.shared_pref, MODE_PRIVATE);
		
		boolean isAppLaunchFirstTime = preferences.getBoolean("IsAppLaunch",
				true);
		if (isAppLaunchFirstTime) {
	
			Intent homeIntent = new Intent(this, Splash.class);
			startActivity(homeIntent);
			finish();

		} else {
	
			Intent homeIntent = new Intent(this, HomeScreen.class);
			startActivity(homeIntent);
			finish();
		
		}
	}
}