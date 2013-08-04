package com.prunn.birddroid;

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.TextView;


class SendCtrl extends AsyncTask<URL, Integer, Long> {
    
	public static JSONObject message = new JSONObject();
	public static TextView outputtitle = null;
	public static TextView outputartist = null;
	public static TextView outputalbum = null;
	public static ImageButton outputplaypause = null;
	public static String outputcmd = null;
	public void setInput(TextView title,TextView artist,TextView album,ImageButton playpause,String cmd)
	{
		outputtitle = title;
		outputartist = artist;
		outputalbum = album;
		outputplaypause = playpause;
		outputcmd = cmd;
	}
    protected Long doInBackground(URL... urls) {
    	try {
	    	URL myurl = null;
	    	URLConnection myconn = null;
	        myurl = urls[0];
        
            myconn = myurl.openConnection();
            myconn.setConnectTimeout(4000);
            
            InputStream in= new BufferedInputStream(myconn.getInputStream());
            InputStreamReader reader = new InputStreamReader(in,"ISO-8859-1");
            
            BufferedReader br = new BufferedReader(reader);
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!=null)
            {
                sb.append(line);
            }
            try {
				message=new JSONObject(sb.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Log.w("BirdDroid","Erreur");
    		
        }
        return (long) 0;
    }
   
    protected void onPostExecute(Long result) {
    	//String str2 = new String(str1.getBytes(),Charset.forName("UTF-8"));
    	String track = "";
		String artist = "";
		String album = "";
		try {
			
			if(message.get("track") != JSONObject.NULL)
				track = message.get("track") + "\n";
			else
				track = "";
			if(message.get("artist") != JSONObject.NULL)
				artist = message.get("artist") + "\n";
			else
				artist = "";
			if(message.get("album") != JSONObject.NULL)
				album = message.get("album") + "\n";
			else
				album = "";
			
			if(outputcmd == "status")  
            {  
                if(message.get("playing").toString() != "false")
                	outputplaypause.setImageResource(android.R.drawable.ic_media_pause);
                else
                	outputplaypause.setImageResource(android.R.drawable.ic_media_play);
            } 
            else 
            {  
                if(message.get("playing").toString() == "false")
                	outputplaypause.setImageResource(android.R.drawable.ic_media_pause);
                else
                	outputplaypause.setImageResource(android.R.drawable.ic_media_play);
            }
			/*if(message.get("playing") != JSONObject.NULL)
				Log.w("BirdDroid", message.get("playing").toString());*/
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//outputtitle.setText(track + "\n" + artist + "\n" + album);
		outputtitle.setText(track);
		outputartist.setText(artist);
		outputalbum.setText(album);
		
	}
}
