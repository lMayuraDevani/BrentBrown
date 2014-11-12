package com.interactive360.brentbrown;

import java.util.ArrayList;
import java.util.List;

import utility.AnimatedExpandableListView;
import utility.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import adapter.SpecialsVehiclesAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class SpecialsFragment extends Fragment {

	AnimatedExpandableListView expandListVehicles;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_specials_screen,
				container, false);

		SpecialsVehiclesAdapter adapter = new SpecialsVehiclesAdapter(
				getActivity());
		expandListVehicles = (AnimatedExpandableListView) rootview
				.findViewById(R.id.expandListVehicles);
		expandListVehicles.setAdapter(adapter);

		expandListVehicles.expandGroup(0);

		expandListVehicles.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// We call collapseGroupWithAnimation(int) and
				// expandGroupWithAnimation(int) to animate group
				// expansion/collapse.
				if (expandListVehicles.isGroupExpanded(groupPosition)) {
					expandListVehicles
							.collapseGroupWithAnimation(groupPosition);
				} else {
					expandListVehicles.expandGroupWithAnimation(groupPosition);
				}
				return true;
			}

		});

		return rootview;
	}

	private static class GroupItem {
		String title;
		List<ChildItem> items = new ArrayList<ChildItem>();
	}

	private static class ChildItem {
		String title;
		String hint;
	}

	private static class ChildHolder {
		TextView title;
		// TextView hint;
	}

	private static class GroupHolder {
		TextView title;
	}

	public class ExampleAdapter extends AnimatedExpandableListAdapter {

		private LayoutInflater inflater;

		private List<GroupItem> items;

		public ExampleAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void setData(List<GroupItem> items) {
			this.items = items;
		}

		@Override
		public ChildItem getChild(int groupPosition, int childPosition) {
			return items.get(groupPosition).items.get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getRealChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ChildHolder holder;
			ChildItem item = getChild(groupPosition, childPosition);
			if (convertView == null) {
				holder = new ChildHolder();
				convertView = inflater.inflate(
						R.layout.special_vehicle_list_item_group, parent, false);
				holder.title = (TextView) convertView
						.findViewById(R.id.txtSplVehicleListGroup);
				// holder.hint = (TextView)
				// convertView.findViewById(R.id.textHint);
				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}

			holder.title.setText(item.title);
			// holder.hint.setText(item.hint);

			return convertView;
		}

		@Override
		public int getRealChildrenCount(int groupPosition) {
			return items.get(groupPosition).items.size();
		}

		@Override
		public GroupItem getGroup(int groupPosition) {
			return items.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return items.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupHolder holder;
			GroupItem item = getGroup(groupPosition);
			if (convertView == null) {
				holder = new GroupHolder();
				convertView = inflater
						.inflate(R.layout.special_vehicle_list_item_group,
								parent, false);
				holder.title = (TextView) convertView
						.findViewById(R.id.txtSplVehicleListGroup);
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}

			holder.title.setText(item.title);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}

	}

}
