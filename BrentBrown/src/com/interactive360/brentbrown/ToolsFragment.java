package com.interactive360.brentbrown;

import java.util.List;

import adapter.VehicleListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ToolsFragment extends Fragment implements OnClickListener{

	ListView VehicleGarageList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_tools,
				container, false);

		LinearLayout park_my_car = (LinearLayout) rootView.findViewById(R.id.Park_my_car);
		 park_my_car.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Park_my_car:
			Intent homeIntent = new Intent(this.getActivity(), FindMyCarActivity.class);
			startActivity(homeIntent);
			this.getActivity().finish();
			break;

		default:
			break;
		}
		
	}

}
