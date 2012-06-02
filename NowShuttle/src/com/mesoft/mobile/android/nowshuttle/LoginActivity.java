package com.mesoft.mobile.android.nowshuttle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mesoft.mobile.android.nowshuttle.om.DataProvider;
import com.mesoft.mobile.android.nowshuttle.om.User;

public class LoginActivity extends BaseActivity {

	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.login);

		final Button button = (Button) findViewById(R.id.login);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				EditText email = (EditText) findViewById(R.id.email);
				EditText password = (EditText) findViewById(R.id.password);
				String emailText = email.getText().toString();
				String passwordText = password.getText().toString();
				if (DataProvider.checkUser(emailText, passwordText)) {
					User u = DataProvider.getUserByEmail(emailText);
					DataProvider.persistUser(u.getUserId(), getSetting());
					startActivity(mainActivity);
				} else {
					TextView message = (TextView) findViewById(R.id.message);
					message.setText("The email or password you entered is incorrect.");
				}
			}
		});

		final Button signup = (Button) findViewById(R.id.signup);
		signup.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				EditText email = (EditText) findViewById(R.id.email);
				TextView message = (TextView) findViewById(R.id.message);
				boolean result = DataProvider
						.signUp(email.getText().toString());
				if (result) {
					message.setText("Please check email adress for password.");
				} else {
					message.setText("Please try later.");
				}

			}
		});
	}

}
