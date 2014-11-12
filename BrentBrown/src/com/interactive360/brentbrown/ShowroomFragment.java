package com.interactive360.brentbrown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.androidsurya.customviews.PullToRefreshListView;
import com.androidsurya.customviews.PullToRefreshListView.OnRefreshListener;
import com.interactive360.brentbrown.HomeScreen.SectionsPagerAdapter;

import adapter.VehicleListAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShowroomFragment extends Fragment implements ActionBar.TabListener{

	PullToRefreshListView showroomGarageList;
	private List<String> mListItems;
	private FragmentTabHost tabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_showroom_screen,
				container, false);
		mListItems = new ArrayList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		
		
		showroomGarageList= (PullToRefreshListView) rootView.findViewById(android.R.id.list); 
		showroomGarageList.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new GetDataTask().execute();
			}
		} );

		mListItems = new ArrayList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		showroomGarageList.setAdapter(new VehicleListAdapter(getActivity().getApplicationContext(),true, mListItems,mListItems,null));
		
		
		/*VehicleGarageList = (ListView) rootView.findViewById(R.id.listVehicle);
		VehicleGarageList.setAdapter(new VehicleListAdapter(getActivity(),
				false, mListItems));*/
		((HomeScreen)getActivity()).mSearchDrawLayout = (DrawerLayout)rootView.findViewById(R.id.drawer_layout);
		((HomeScreen)getActivity()).mSearchDrawLayout.closeDrawers();

		tabHost = new FragmentTabHost(getActivity());
		tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_showroom_screen);

		Bundle arg1 = new Bundle();
		arg1.putInt("new", 1);
		tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("New"),
		    GarageFragment.class, arg1);

		Bundle arg2 = new Bundle();
		arg2.putInt("used", 1);
		tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Used"),
		    GarageFragment.class, arg2);
		
		tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.actionbar_tab_indicator);
		tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.actionbar_tab_indicator);

		return tabHost;
		
	}


	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.add(0, "Added 1 Showroom...");
			mListItems.add(0, "Added 2 Showroom...");
			
			
			Log.v("TAG","Added items after refresh..");
			// Call onRefreshComplete when the list has been refreshed.
			showroomGarageList.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private String[] mStrings = { "Showroom1",
			"Show room2", "Showroom3"};

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
