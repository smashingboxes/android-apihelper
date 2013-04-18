package com.example.apihelperexample;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.monkeydriver.APIHelpers.APIHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MainActivity extends Activity {
	
	private final String LOG_TAG = "MainActivity";
	private final String API_URL = "https://api.twitter.com/1/statuses/user_timeline.json?count=1";
	private final String FILTER_NAME = this.getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(FILTER_NAME));
	
		testApiGet();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
	}
	
	private void testApiGet() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("include_entities", "true"));
		params.add(new BasicNameValuePair("include_rts", "true"));
		params.add(new BasicNameValuePair("screen_name", "smashing777"));
		params.add(new BasicNameValuePair("count", "1"));
		
		APIHelper.apiGet(this, FILTER_NAME, API_URL, params);
	}
	
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra(APIHelper.EXTRA_MESSAGE);
			
			Log.d(LOG_TAG, "! message received : "+msg);
			
			if(msg.equals(APIHelper.MSG_SUCCESS)) {
				Log.d(LOG_TAG, "! Success");
				Log.d(LOG_TAG, "@ APIHelper.sJsonResult = "+APIHelper.sJsonResult);
			} else if(msg.equals(APIHelper.MSG_HTTP_FAILURE)){
				Log.d(LOG_TAG, "! HTTP failure");
				Log.d(LOG_TAG, "@ APIHelper.sJsonResult = "+APIHelper.sJsonResult);
			} else if(msg.equals(APIHelper.MSG_API_FAILURE)) {
				Log.d(LOG_TAG, "! API failure");
				Log.d(LOG_TAG, "@ APIHelper.sJsonResult = "+APIHelper.sJsonResult);
			} else if(msg.equals(APIHelper.MSG_JSON_FAILURE)) {
				Log.d(LOG_TAG, "! JSON failure");
				Log.d(LOG_TAG, "@ APIHelper.sJsonResult = "+APIHelper.sJsonResult);
			}
		}
	};

}
