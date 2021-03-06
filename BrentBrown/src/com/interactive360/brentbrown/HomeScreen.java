package com.interactive360.brentbrown;

import java.util.ArrayList;
import java.util.Locale;

import utility.ActivityBase;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class HomeScreen extends ActivityBase implements ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	public DrawerLayout mSearchDrawLayout;
	ArrayList<Integer> myImageList = new ArrayList<Integer>();
	
//	Drawable[] s= {R.drawable.garage_icon,R.drawable.showroom_icon,R.drawable.specials_icon,R.drawable.tools_icon,R.drawable.menu_icon};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		myImageList.add(R.drawable.garage_icon);
		myImageList.add(R.drawable.showroom_icon);
		myImageList.add(R.drawable.specials_icon);
		myImageList.add(R.drawable.tools_icon);
		myImageList.add(R.drawable.menu_icon);
		
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setIcon(myImageList.get(i))
					.setTabListener(this));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.home_screen, menu);
	//	Log.v("TAG", "here...in OncreateOptionMenu");
		if (mViewPager.getCurrentItem() != 1) {
			MenuItem item = (MenuItem) menu.findItem(R.id.action_search);
			item.setVisible(false);

		}

		return true;
	}
	static public Drawable getAndroidDrawable(String pDrawableName){
	    int resourceId=Resources.getSystem().getIdentifier(pDrawableName, "drawable", "android");
	    if(resourceId==0){
	        return null;
	    } else {
	        return Resources.getSystem().getDrawable(resourceId);
	    }
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_search) {
			if (mSearchDrawLayout != null) {
				if (mSearchDrawLayout.isDrawerOpen(Gravity.RIGHT)) {
					mSearchDrawLayout.closeDrawers();
				} else {
					mSearchDrawLayout.openDrawer(Gravity.RIGHT);
				}
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {

		// To Hide and Show Search Bar
		invalidateOptionsMenu();

		// To close search Drawer when moving to other tabs
		if (mSearchDrawLayout != null) {
			mSearchDrawLayout.closeDrawers();
		}

		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			if (position == 0) {

				return new GarageFragment();
			}
			if (position == 1) {

				return new ShowroomFragment();

			}
			if (position == 2) {

				return new SpecialsFragment();

			}
			if (position == 3) {
				return new ToolsFragment();
			}
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_garage).toUpperCase(l);
			case 1:
				return getString(R.string.title_showroom).toUpperCase(l);
			case 2:
				return getString(R.string.title_specials).toUpperCase(l);
			case 3:
				return getString(R.string.title_tools).toUpperCase(l);
			case 4:
				return getString(R.string.title_extra).toUpperCase(l);
			}
			return null;
		}
	}

	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_garage_screen,
					container, false);
			return rootView;
		}
	}

}
