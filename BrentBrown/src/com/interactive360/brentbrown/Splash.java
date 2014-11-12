package com.interactive360.brentbrown;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

public class Splash extends ActionBarActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.splash);
		 
		 
		   	 new Handler().postDelayed(new Runnable(){
		            @Override
		            public void run() {
		                Intent mainIntent = new Intent(Splash.this,RegisterActivity.class);
		                Splash.this.startActivity(mainIntent);
		                Splash.this.finish();
		            }
		        }, 500);	
		 
	}
	
	/*public static boolean isOnline(Context c) {  
		ConnectivityManager conectivtyManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);  
    if (conectivtyManager.getActiveNetworkInfo() != null  
            && conectivtyManager.getActiveNetworkInfo().isAvailable()  
            && conectivtyManager.getActiveNetworkInfo().isConnected()) {  
            return true;  
        } else {  
            return false;  
        }
    }*/
	
}
