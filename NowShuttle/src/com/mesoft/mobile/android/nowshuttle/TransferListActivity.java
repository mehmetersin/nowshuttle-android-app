package com.mesoft.mobile.android.nowshuttle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import com.mesoft.mobile.android.nowshuttle.om.DataProvider;
import com.mesoft.mobile.android.nowshuttle.om.Shuttle;
import com.mesoft.mobile.android.nowshuttle.om.TransferStatus;

public class TransferListActivity extends BaseActivity implements
		OnItemSelectedListener {

	List shuttleList = new ArrayList<Shuttle>();
	Spinner spin = null;
	String shuttleId;
	ArrayAdapter<TransferStatus> transferAdapter;
	ListView list;
	
	List<TransferStatus> gettedList = new ArrayList<TransferStatus>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transferlist);

		shuttleList = DataProvider.getShuttleList();

		spin = (Spinner) findViewById(R.id.shuttlelist);
		spin.setOnItemSelectedListener(this);

		ArrayAdapter shuttleAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, shuttleList);
		
		transferAdapter = new ArrayAdapter<TransferStatus>(this,
				android.R.layout.simple_dropdown_item_1line, gettedList);

		shuttleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(shuttleAdapter);

		list = (ListView) findViewById(R.id.transferList);
		list.setAdapter(transferAdapter);
		
		

		Button listBtn = (Button) findViewById(R.id.listTransferList);
		listBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				getTransferList();
				transferAdapter.notifyDataSetChanged();
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

	private void getTransferList() {
		DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
		Calendar c = Calendar.getInstance();
		c.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());

		Date d = c.getTime();

		List<TransferStatus> list = DataProvider.getTodayTransferList(shuttleId,
				String.valueOf(d.getTime()));
		
		gettedList.clear();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TransferStatus transferStatus = (TransferStatus) iterator.next();
			gettedList.add(transferStatus);
		}
		
		if (gettedList.size()==0){
			showMessage(Constants.noTransferList);
		}
		
	}
}
