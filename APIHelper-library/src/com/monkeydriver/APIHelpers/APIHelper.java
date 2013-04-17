package com.monkeydriver.APIHelpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class APIHelper {
	
	//TODO: minimize duplicated code between public api methods
	
	public static JSONObject sJsonGet = null;
	public static JSONObject sJsonPost = null;
	public static JSONObject sJsonPut = null;
	public static JSONObject sJsonDelete = null;
	
	public static final String EXTRA_MESSAGE = "extraMessage";
	public static final String MSG_SUCCESS = "msgSuccess";
	public static final String MSG_HTTP_FAILURE = "msgHttpFailure";
	public static final String MSG_API_FAILURE = "msgApiFailure";
	public static final String MSG_JSON_FAILURE = "msgJsonFailure";
	
	private static final String LOG_TAG = "APIHelper";
	
	/**
	 * GETs to a URL and broadcasts an event on result.
	 * @param context
	 * @param filterName - intent filter
	 * @param urlStr
	 * @param params
	 */
	public static void apiGet(Context context, String filterName, String urlStr, ArrayList<NameValuePair> params) {
		InputStream inputStream = null;
		String result = null;
		JSONObject jsonObject = null;
		
		// create parameter string
		String paramStr = URLEncodedUtils.format(params, "utf-8");
		urlStr += "?"+paramStr;
		
		// http get
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urlStr);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			
			inputStream = httpEntity.getContent();
			
			/* FOR FUTURE USE */
			//int statusCode = httpResponse.getStatusLine().getStatusCode();
		} catch(Exception e) {
			handleHttpError(context, filterName, e);
			return;
		}
		
		result = getStringFromResponse(inputStream);
		
		if(result == null) {
			sendMessage(context, filterName, MSG_API_FAILURE);
			return;
		} 
		
		jsonObject = getJsonFromString(result);
		
		if(jsonObject != null) {
			sJsonGet = jsonObject;
			sendMessage(context, filterName, MSG_SUCCESS);
		} else {
			sendMessage(context, filterName, MSG_JSON_FAILURE);
		}
	}
	
	/**
	 * POSTs to a URL and broadcasts an event on result.
	 * @param context
	 * @param filterName
	 * @param urlStr
	 * @param params
	 */
	public static void apiPost(Context context, String filterName, String urlStr, ArrayList<NameValuePair> params) {
		InputStream inputStream = null;
		String result = null;
		JSONObject jsonObject = null;
		
		// http post
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlStr);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			
			inputStream = httpEntity.getContent();
			
			/* FOR FUTURE USE */
			//int statusCode = httpResponse.getStatusLine().getStatusCode();
		} catch(Exception e) {
			handleHttpError(context, filterName, e);
			return;
		}
		
		result = getStringFromResponse(inputStream);
		
		if(result == null) {
			sendMessage(context, filterName, MSG_API_FAILURE);
			return;
		}
		
		jsonObject = getJsonFromString(result);
		
		if(jsonObject != null) {
			sJsonPost = jsonObject;
			sendMessage(context, filterName, MSG_SUCCESS);
		} else {
			sendMessage(context, filterName, MSG_JSON_FAILURE);
		}
	}
	
	/**
	 * PUTs to a URL and broadcasts and event on result.
	 * @param context
	 * @param filterName
	 * @param urlStr
	 * @param params
	 */
	public static void apiPut(Context context, String filterName, String urlStr, ArrayList<NameValuePair> params) {
		InputStream inputStream = null;
		String result = null;
		JSONObject jsonObject = null;
		
		// http put
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(urlStr);
			httpPut.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = httpClient.execute(httpPut);
			HttpEntity httpEntity = httpResponse.getEntity();
			
			inputStream = httpEntity.getContent();
			
			/* FOR FUTURE USE */
			//int statusCode = httpResponse.getStatusLine().getStatusCode();
		} catch(Exception e) {
			handleHttpError(context, filterName, e);
			return;
		}
		
		result = getStringFromResponse(inputStream);
		
		if(result == null) {
			sendMessage(context, filterName, MSG_API_FAILURE);
			return;
		}
		
		jsonObject = getJsonFromString(result);
		
		if(jsonObject != null) {
			sJsonPut = jsonObject;
			sendMessage(context, filterName, MSG_SUCCESS);
		} else {
			sendMessage(context, filterName, MSG_JSON_FAILURE);
		}
	}
	
	public static void apiDelete(Context context, String filterName, String urlStr, ArrayList<NameValuePair> params) {
		InputStream inputStream = null;
		String result = null;
		JSONObject jsonObject = null;
		
		// http delete
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpDelete httpDelete = new HttpDelete(urlStr);
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			HttpEntity httpEntity = httpResponse.getEntity();
			
			inputStream = httpEntity.getContent();
			
			/* FOR FUTURE USE */
			//int statusCode = httpResponse.getStatusLine().getStatusCode();
		} catch(Exception e) {
			handleHttpError(context, filterName, e);
			return;
		}
		
		result = getStringFromResponse(inputStream);
		
		if(result == null) {
			sendMessage(context, filterName, MSG_API_FAILURE);
			return;
		}
		
		jsonObject = getJsonFromString(result);
		
		if(jsonObject != null) {
			sJsonPut = jsonObject;
			sendMessage(context, filterName, MSG_SUCCESS);
		} else {
			sendMessage(context, filterName, MSG_JSON_FAILURE);
		}
	}
	
	/**
	 * Converts and returns String from API response.
	 * @param inputStream
	 * @return
	 */
	private static String getStringFromResponse(InputStream inputStream) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"),8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line+"\n");
			}
			inputStream.close();
			
			return stringBuilder.toString();
		} catch(Exception e) {
			Log.d(LOG_TAG, "Error converting result " + e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Converts and returns JSONObject from String.
	 * @param str
	 * @return
	 */
	private static JSONObject getJsonFromString(String str) {
		Log.d(LOG_TAG, str);
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(str);
			return jsonObject;
		} catch(JSONException e) {
			Log.d(LOG_TAG, "Error parsing data " + e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param filterName
	 * @param e
	 */
	private static void handleHttpError(Context context, String filterName, Exception e) {
		Log.d(LOG_TAG,"Error in http connection " + e.toString());
		e.printStackTrace();
		sendMessage(context, filterName, MSG_HTTP_FAILURE);
	}
	
	/**
	 * Broadcasts event.
	 * @param context
	 * @param msg
	 * @param value
	 */
	private static void sendMessage(Context context, String filterName, String msg) {
		Intent intent = new Intent(filterName);
		intent.putExtra(EXTRA_MESSAGE, msg);
		
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
	
}
