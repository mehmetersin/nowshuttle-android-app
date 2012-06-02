package com.mesoft.mobile.android.nowshuttle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mesoft.mobile.android.nowshuttle.om.DataProvider;
import com.mesoft.mobile.android.nowshuttle.om.User;

public class PreferencesActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);

		User u = DataProvider.getUserByUserId(getSystemUserId());

		EditText preferencesName = (EditText) findViewById(R.id.preferencesDisplayName);
		preferencesName.setText(u.getDisplayName());

		final Button save = (Button) findViewById(R.id.savePreferenes);
		save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				EditText preferancesName = (EditText) findViewById(R.id.preferencesDisplayName);

				DataProvider.updateUserDisplayName(getSystemUserId(),
						preferancesName.getText().toString());
				startActivity(mainActivity);
				return;
			}
		});
	}
}
