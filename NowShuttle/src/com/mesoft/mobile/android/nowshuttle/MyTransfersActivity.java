package com.mesoft.mobile.android.nowshuttle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.mesoft.mobile.android.nowshuttle.om.DataProvider;
import com.mesoft.mobile.android.nowshuttle.om.Shuttle;

public class MyTransfersActivity extends BaseActivity implements
		OnItemSelectedListener {

	List shuttleList = new ArrayList<Shuttle>();
	Spinner spin = null;
	Button infortTransferStatus;
	private int transferState = 1;
	String shuttleId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mytransfers);

		shuttleList = DataProvider.getShuttleList();

		spin = (Spinner) findViewById(R.id.shuttlelist);
		spin.setOnItemSelectedListener(this);

		ArrayAdapter aa = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, shuttleList);

		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);

		infortTransferStatus = (Button) findViewById(R.id.informTransferStatus);
		infortTransferStatus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
				Calendar c = Calendar.getInstance();
				c.set(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth());

				Date d = c.getTime();
				
				boolean re = DataProvider.informTransferStatus(shuttleId,
						getSystemUserId(), String.valueOf(transferState),String.valueOf(d.getTime()));

				if (re) {
					showMessage(Constants.succesMessage);
				} else {
					showMessage(Constants.errorMessage);
				}

			}
		});

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGrp);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.rdbExist) {
					transferState = 1;
				} else {
					transferState = 2;
				}

			}
		});

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		shuttleId = ((Shuttle) shuttleList.get(position)).getId();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
