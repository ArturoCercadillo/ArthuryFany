package com.example.arthurfany;




import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
	final static String URL_JSON="http://alejandratoledov.blogspot.com/";
	static URL blogFeedUrl;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView texto = (TextView) findViewById(R.id.textView1);
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
}
class GetBlogPostsTask extends AsyncTask{

	@Override
	protected Object doInBackground(Object... params) {
		
		int responseCode=-1;
		try {
			MainActivity.blogFeedUrl= new URL(MainActivity.URL_JSON);
			HttpURLConnection connection = (HttpURLConnection) MainActivity.blogFeedUrl.openConnection();
			connection.connect();
			responseCode = connection.getResponseCode();
			Log.i(MainActivity.TAG,"Code:"+responseCode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.i(MainActivity.TAG,e.toString());
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
        	Log.i(MainActivity.TAG,e.toString());
		}
        catch (Exception e) {
			// TODO Auto-generated catch block
        	Log.i(MainActivity.TAG,e.toString());
		}
		return "Code "+responseCode;
	}
	
}
