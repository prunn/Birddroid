package com.prunn.birddroid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import com.prunn.birddroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;



public class BirdDroid extends Activity {
	Timer timer = new Timer();
	Settings settings = new Settings();
	class Settings 
	{ 
		public String ip;
		public String port;
		Settings()
		{
			 
		}
	}
	void loadSettings()
	{
		settings = new Settings();
		SharedPreferences preferences = this.getSharedPreferences("myPrefs",MODE_PRIVATE);
	    settings.ip = preferences.getString("ip", "192.168.0.0");
	    settings.port = preferences.getString("port", "50136");
	}
	/*public class RemoteControlReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
	            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
	            Log.wtf("BirdDroid", event.getKeyCode()+"erw" );
            	
	            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
	                // Handle key press.
	            	Log.w("BirdDroid", "Key press" );
	            	send_command("playpause");
	            }
	        }
	    }
	}*/
	//ComponentName remoteControlReceiver;
	/*protected void onStop()
	{
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		am.unregisterMediaButtonEventReceiver(new ComponentName(getPackageName(), RemoteControlReceiver.class.getName()));
	}*/
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, Preferences.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	class send_command_task extends TimerTask {
	    
	    public void run() {
	    	send_command("status");
	    	//Log.wtf("commande", "status");
	  }
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_droid);
		addListenerOnButton();
		
		//ContextWrapper mContext = null;
	    //AudioManager manager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	    //manager.registerMediaButtonEventReceiver(RemoteControlReceiver);
	    //((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(this,RemoteControlReceiver.class));
    }
	
	@Override
	public void onPause() {
        super.onPause();
        timer.cancel();
	}
	/*public void onStop(Bundle savedInstanceState) {
        //super.onStop();
        timer.cancel();
	}*/
	
	@Override
	protected void onResume() {
	    super.onResume();
	    loadSettings();
	    timer = new Timer();
	    timer.scheduleAtFixedRate(new send_command_task(), 0, 4000);
	    // Normal case behavior follows
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bird_droid, menu);
        //timer.scheduleAtFixedRate(new send_command_task(), 0, 4000);
	    //((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(this,RemoteControlReceiver.class));
	    // Start listening for button presses
	    //AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	    //am.registerMediaButtonEventReceiver(remoteControlReceiver);
	    //am.registerMediaButtonEventReceiver(receiver);
	    return true;
    }
    
    public void send_command(String cmd)
    {
    	//SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",MODE_PRIVATE);
        //String prefName = myPrefs.getString("ip_address", "192.168.0.0");
        
    	//Log.wtf("Bird control", "http://" + settings.ip + ":" + settings.port + "/ctl/" + cmd);
    	
        //Log.wtf("Bird control", "http://" + prefName + ":50136/ctl/" + cmd);
    	URL url1 = null;
		try {
			//url1 = new URL("http://" + prefName + ":50136/ctl/" + cmd);
			url1 = new URL("http://" + settings.ip + ":" + settings.port + "/ctl/" + cmd);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		SendCtrl ctrl = (SendCtrl) new SendCtrl();
		TextView title=(TextView)findViewById(R.id.title);
		TextView artist=(TextView)findViewById(R.id.artist);
		TextView album=(TextView)findViewById(R.id.album);
		ImageButton playpause = (ImageButton) findViewById(R.id.play);
    	ctrl.setInput(title,artist,album,playpause,cmd);
		ctrl.execute(url1);
    }
    
    
    
    public void addListenerOnButton() {
    	
    	//Button dg = (Button) findViewById(android.R.intent.action.MEDIA_BUTTON);
		
    	//NEXT BUTTON
		ImageButton ButtonNext = (ImageButton) findViewById(R.id.next);
		ButtonNext.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0){
				send_command("next");
			}
 
		});
		
		
		//previous BUTTON
		ImageButton BtnPrevious = (ImageButton) findViewById(R.id.previous);
		BtnPrevious.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				send_command("prev");
			}
 
		});
 
		//play BUTTON
		ImageButton BtnPlay = (ImageButton) findViewById(R.id.play);
		BtnPlay.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				send_command("playpause");
			}
 
		});
	}
}