package com.monkeydriver.APIHelpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		NetworkStatusHelper.handleConnectivityChange(context);
	}
	
}
