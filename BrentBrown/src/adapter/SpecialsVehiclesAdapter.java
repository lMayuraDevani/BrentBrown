package adapter;

import utility.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import com.interactive360.brentbrown.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpecialsVehiclesAdapter extends AnimatedExpandableListAdapter {
	private LayoutInflater inflater;
	Context context;

	private String[] groups = { "New Specials", "User Specials",
			"Lease Specials", "Lease Specials" };

	private String[][] children = {

	{ "New Special 1", "New Special 2", "New Special 3" },

	{ "User Specials 1", "User Specials 2", "User Specials 3" },

	{ "Lease Specials 1", "Lease Specials 2", "Lease Specials 3" },
			{ "Lease Specials 1", "Lease Specials 2", "Lease Specials 3" }  };

	// private List<GroupItem> items;

	public SpecialsVehiclesAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	/*
	 * public void setData(List<GroupItem> items) { this.items = items; }
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return children[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getRealChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.special_vehicle_list_item_group, parent, false);
		}

		TextView textView = (TextView) convertView.findViewById(R.id.txtSplVehicleListGroup);
		textView.setText(getChild(groupPosition, childPosition).toString());
		return convertView;
	}

	@Override
	public int getRealChildrenCount(int groupPosition) {
		return children[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return groups.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.special_vehicle_list_item_group, parent, false);
		}
		
		LinearLayout llMain = (LinearLayout) convertView.findViewById(R.id.llMain);
		
		TextView textView = (TextView) convertView
				.findViewById(R.id.txtSplVehicleListGroup);
		textView.setText(getGroup(groupPosition).toString());
		
		
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
