package com.example.testing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button button;
	TextView httpStuff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button1);
        httpStuff = (TextView) findViewById(R.id.textView1);
        
        button.setOnClickListener(new View.OnClickListener() {
			
        	
			@Override
			public void onClick(View arg0) {
				
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							BufferedReader in = null;
							try {
								in = MainActivity.getRequest();
								String message = in.readLine();
								
								Log.v("Message : ", message);
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
					});
					thread.start();
			}
		});
    }
    public static BufferedReader getRequest() {
    	BufferedReader in = null;
    	try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet();
			String login = "group14";
			String pass = login;
			client.getCredentialsProvider().setCredentials(new AuthScope("http://192.168.43.203", 80), new UsernamePasswordCredentials(login, pass));
			URI website = new URI("http://192.168.43.203/PhpProject1/index.php?order_id=12&user_id=33&order_value=3");

			get.setURI(website);
			HttpResponse response = client.execute(get);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			System.out.println(in.readLine());	
    	}
    	catch(Exception e) {
    		
    	}
    	return in;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
