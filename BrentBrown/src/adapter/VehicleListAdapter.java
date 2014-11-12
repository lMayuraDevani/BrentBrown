package adapter;

import java.util.List;

import com.interactive360.brentbrown.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VehicleListAdapter extends ArrayAdapter<String> {

	Context context;
	Boolean isGarage;
	List<String> mListItems;
	List<String> vmileage;
	List<String> img_url;
	
	public VehicleListAdapter(Context context,
			Boolean isGarage, List<String> mListItems, List<String> vmileage, List<String> img_url) {
		
		
		super(context, R.layout.activity_home_screen ,mListItems);
		Log.v("TAG","Atleast");
		this.context = context;
		this.isGarage = isGarage;
		this.mListItems = mListItems;
		this.vmileage = vmileage;
		this.img_url = img_url;
		
		Log.v("TAG","Constructor set array length:"+mListItems.size());
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater
				.inflate(R.layout.vehicle_list_item, parent, false);
		float height = 190 * (context.getResources().getDisplayMetrics().widthPixels) / 1080;
		//View rowView = convertView;

		// reuse views
		/*	LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.vehicle_list_item, null);*/
			TextView vname = (TextView) rowView.findViewById(R.id.vehical_nametxt);
			TextView vmileagetxtview = (TextView) rowView.findViewById(R.id.vmileage);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.vehicle_image);
			
			vname.setText(mListItems.get(position));
			vmileagetxtview.setText(vmileage.get(position));
			if(img_url!=null)
			{
			String full_path =img_url.get(position);
			 if(full_path!=null)
			 {
				
				Picasso.with(context).load(full_path).centerCrop()
			//	.error(R.drawable.user)
						.resize((int) height, (int) height, false).into(imageView);
			 }
			}
			if(isGarage)
			{
				LinearLayout lldisplayforshowroom = (LinearLayout)rowView.findViewById(R.id.displayforshowroom);
				lldisplayforshowroom.setVisibility(View.GONE);
			}
			// configure view holder


		return rowView;
	}

}
