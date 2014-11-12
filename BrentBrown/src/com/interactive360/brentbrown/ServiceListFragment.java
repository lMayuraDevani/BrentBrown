package com.interactive360.brentbrown;

import java.util.List;

import adapter.ServiceListAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ServiceListFragment extends Fragment {
	
	ListView listServiceHistory;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_service_detail,
				container, false);

	
		listServiceHistory = (ListView)rootView.findViewById(R.id.listServiceHistory);
		ServiceListAdapter adapter = new ServiceListAdapter(getActivity());
		listServiceHistory.setAdapter(adapter);
		
		return rootView;
	}
}
