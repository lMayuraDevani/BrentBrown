package com.interactive360.brentbrown;

import java.util.List;

import adapter.AppointmentListAdapter;
import adapter.ServiceListAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AppointmentListFragment extends Fragment {
	
	ListView listAppointments;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_appointment_list,
				container, false);

	
		listAppointments = (ListView)rootView.findViewById(R.id.listAppointments);
		AppointmentListAdapter adapter = new AppointmentListAdapter(getActivity());
		listAppointments.setAdapter(adapter);
		
		return rootView;
	}
}
