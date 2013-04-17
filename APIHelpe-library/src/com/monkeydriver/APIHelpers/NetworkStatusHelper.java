package com.monkeydriver.APIHelpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkStatusHelper {

	/**
	 * Returns true if device is connected to a network, false if device is not connected to a network.
	 * @param context
	 * @return true | false
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager)(context.getSystemService(Context.CONNECTIVITY_SERVICE));
		NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		
		if(activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
}
