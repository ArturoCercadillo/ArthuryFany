package com.example.arthurfany;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Estefanía
 * @author Arturo
 * 
 */
public class MainActivity extends ListActivity {
	protected String [] array;
	final static int NUMBER_OF_POST =20;
	final static String TAG = MainActivity.class.getName();
	final static String URL_JSON="http://alejandratoledov.blogspot.com/feeds/posts/default?alt=json";
	static URL blogFeedUrl;
	public JSONObject mBlogData;
	TextView texto;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = (TextView) findViewById(R.id.textView1);
        GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
        if (isNetworkAvailable())
        getBlogPostsTask.execute();
        else
        {
        	Toast.makeText(this,R.string.NC,Toast.LENGTH_LONG).show();

        }
        
		
      /*  array=getResources().getStringArray(R.array.GOT);
        if (array!=null)
        	texto.setVisibility(View.INVISIBLE);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        setListAdapter(adapter);*/
        
    }


    private boolean isNetworkAvailable() {
		// TODO Auto-generated method stub
    	boolean isAvailable=false;
    	ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    	if (networkInfo!=null)
    		isAvailable=networkInfo.isConnected();
		return isAvailable;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

class GetBlogPostsTask extends AsyncTask<Object,Void,JSONObject>{
	int responseCode=-1;
	@Override
	protected JSONObject doInBackground(Object... params) {
		
		JSONObject jsonResponse=null;
		try {
			MainActivity.blogFeedUrl= new URL(MainActivity.URL_JSON);
			HttpURLConnection connection = (HttpURLConnection) MainActivity.blogFeedUrl.openConnection();
			connection.connect();
			responseCode = connection.getResponseCode();
			if (responseCode!=HttpURLConnection.HTTP_OK){
				Log.i(MainActivity.TAG,"Code:"+responseCode);
			}else{
				InputStream is = connection.getInputStream();
				BufferedReader bf = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String linea;
				linea=bf.readLine();
				while(linea!=null){
					sb.append(linea);
					if (bf.ready())
					linea=bf.readLine();
					else
					break;	
				}
				String texto = sb.toString();
				jsonResponse=new JSONObject(texto);
				/*
				JSONObject json = new JSONObject(texto);
				JSONObject jsonfeed = (JSONObject) json.get("feed");
				JSONArray jsonaentry = jsonfeed.getJSONArray("entry");
				
				for(int i=0;i<jsonaentry.length();i++)
				{
					JSONObject json2=jsonaentry.getJSONObject(i);
					JSONObject json3=json2.getJSONObject("title");
					Log.i(MainActivity.TAG,json3.getString("$t"));
					
				}
				System.out.println(texto);
				Log.i(MainActivity.TAG,texto);*/
			}
			Log.i(MainActivity.TAG,"Code:"+responseCode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.i(MainActivity.TAG,e.toString());
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
        	Log.i(MainActivity.TAG,e.toString()+"holaaaa");
		}
        catch (Exception e) {
			// TODO Auto-generated catch block
        	Log.i(MainActivity.TAG,e.toString());
		}
		
		return jsonResponse;
	}
	@Override
    protected void onPostExecute(JSONObject result){
		mBlogData = result;
		updateList();
    }
}

private void updateList() {
    if(mBlogData!=null)
    {

        try {

        JSONObject feed = mBlogData.getJSONObject("feed");
        JSONArray entry = feed.getJSONArray("entry");

        array= new String[entry.length()];
                for(int i =0;i<entry.length();i++)
                {
                    JSONObject objeto = entry.getJSONObject(i);
                    JSONObject title = objeto.getJSONObject("title");
                    array[i] = Html.fromHtml(title.getString("$t")).toString();
                }
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array);
         if(array !=null)
        {
            texto.setVisibility(View.INVISIBLE);
            setListAdapter(adapter);
        }
        }
        catch (JSONException e) {
        e.printStackTrace();
    }
    }
}
}


