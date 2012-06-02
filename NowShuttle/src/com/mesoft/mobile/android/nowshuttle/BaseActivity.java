package com.mesoft.mobile.android.nowshuttle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class BaseActivity extends Activity {

	public String mainActivity = "com.mesoft.mobile.android.nowshuttle.MainActivity";
	public String prefencesActivity = "com.mesoft.mobile.android.nowshuttle.PreferencesActivity";
	public String loginActivity = "com.mesoft.mobile.android.nowshuttle.LoginActivity";
	public String myTransfers = "com.mesoft.mobile.android.nowshuttle.MyTransfersActivity";
	public String transferList = "com.mesoft.mobile.android.nowshuttle.TransferListActivity";
	public String shuttlePref = "com.mesoft.mobile.android.nowshuttle.ShuttlePrefActivity";
	

	public void startActivity(String name) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(
				"com.mesoft.mobile.android.nowshuttle", name));
		startActivity(intent);
	}

	public String getSystemUserId() {
		return getSharedPreferences(Constants.PREFS_NAME, 0).getString(
				Constants.PREF_USER_ID, null);
	}

	public SharedPreferences getSetting() {
		return getSharedPreferences(Constants.PREFS_NAME, 0);
	}

	public void showMessage(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

}
