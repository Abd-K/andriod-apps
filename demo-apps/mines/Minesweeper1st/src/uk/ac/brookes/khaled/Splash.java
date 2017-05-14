package uk.ac.brookes.khaled;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends Activity {
	

	@Override
	protected void onCreate(Bundle toz) {
		// TODO Auto-generated method stub
		super.onCreate(toz);
		setContentView(R.layout.splash);
	  MediaPlayer intromusic = MediaPlayer.create(Splash.this, R.raw.introsound);
		
		intromusic.start();
		intromusic.setVolume(3,3);

		
		Toast display = Toast.makeText(this, "Have Fun", Toast.LENGTH_LONG);
		display.show();
		Thread timer = new Thread(){
			
			public void run(){
				try {
					
					sleep(3000);
				
					
				} catch (InterruptedException e){
					e.printStackTrace();
					
				} finally {
					Intent openmenu = new Intent("uk.ac.brookes.khaled.MENU");
					startActivity(openmenu);
				}
			}
		};
		timer.start();
	}
	
@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}


	}

