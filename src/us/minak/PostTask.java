package us.minak;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

class PostTask extends AsyncTask<String, String, String>{

	private SettingsActivity mThis;
	
    @Override
    protected String doInBackground(String... uri) {
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("https://lukeshu.com/minak-server");
	    HttpResponse response = null;
	    try {
	    //FileInputStream stream = new FileInputStream(SettingsUtil.getGestureFile(this));
	        httppost.setEntity(new FileEntity(SettingsUtil.getGestureFile(mThis), "application/octet-stream"));
	        response = httpclient.execute(httppost);

			Toast toast = Toast.makeText(mThis, response.toString(), Toast.LENGTH_LONG);
			toast.show();
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	    if (response != null){
	    	return response.toString();
	    } else {
	    	return "";
	    }
    }
    
    @Override
    protected void onPostExecute(String result) {
        //Do anything with response..
    	;
    }
    
    public void setActivity(Activity ack) {
    	mThis = (SettingsActivity) ack;
    }
    
}