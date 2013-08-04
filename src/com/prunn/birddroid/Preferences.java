package com.prunn.birddroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Preferences extends Activity {
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        addListenerOnButton();
		
        
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",MODE_PRIVATE);
        String ip = myPrefs.getString("ip", "192.168.0.0");
        String port = myPrefs.getString("port", "50136");
        
        EditText text_ip = (EditText) findViewById(R.id.editIP);
        text_ip.setText(ip);
        
        EditText text_Port = (EditText) findViewById(R.id.editPort);
        text_Port.setText(port);
 
        /*getActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_preferences, menu);
        
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void save_prefs()
    {
    	SharedPreferences preferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        
        EditText ip = (EditText) findViewById(R.id.editIP);
        EditText port = (EditText) findViewById(R.id.editPort);
        
 
        prefsEditor.putString("ip", ip.getText().toString());
        prefsEditor.putString("port", port.getText().toString());
        prefsEditor.commit();
    }
    
    public void addListenerOnButton() {
    	
    	//Save BUTTON
    	Button ButtonSave = (Button) findViewById(R.id.save);
		ButtonSave.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0){
				
				save_prefs();
				
				Toast.makeText(Preferences.this,
						"settings saved", Toast.LENGTH_SHORT).show();
			}
 
		});
		
	}
}
