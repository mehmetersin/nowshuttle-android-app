package com.mesoft.mobile.android.nowshuttle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mesoft.mobile.android.nowshuttle.om.DataProvider;

public class MainActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		validateUser();

		final Button preferences = (Button) findViewById(R.id.preferences);
		preferences.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startActivity(prefencesActivity);
				return;
			}
		});

		final Button addTsl = (Button) findViewById(R.id.informMyTransfers);
		addTsl.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {

				startActivity(myTransfers);
				return;
			}
		});

		final Button tlB = (Button) findViewById(R.id.transferList);
		tlB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				startActivity(transferList);
				return;
			}
		});

		

	}

	private void validateUser() {

		boolean isValid = DataProvider.checkUserInSystem(getSetting());
		if (!isValid) {
			startActivity(loginActivity);
			return;
		}
	}

}