package com.interactive360.brentbrown;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rest.ApiException;
import rest.RestException;
import rest.RestServiceClient;
import utility.RayMenu;
import adapter.VehicleListAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidsurya.customviews.PullToRefreshListView;
import com.androidsurya.customviews.PullToRefreshListView.OnRefreshListener;

public class GarageFragment extends Fragment {

	PullToRefreshListView VehicleGarageList;
	RayMenu ray_menu;
	private List<String> mListItems;
	String json;
	private RestServiceClient restClient = null;
	private static final String USER_AGENT = "Android App %s";
	List<String> vname = new ArrayList<String>();
	List<String> vmileage = new ArrayList<String>();
	List<String> img_url = new ArrayList<String>();
	
	
	int[] drawables = {R.drawable.ic_launcher,R.drawable.arrow};
	int pageindex=1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_garage_screen,
				container, false);

		ray_menu = (RayMenu)rootView.findViewById(R.id.ray_menu);
		
		json = "{\"Certified\":false,\"StockType\":\"\",\"criteria\":{\"DealerSiteID\":103,\"PageIndex\":"+pageindex+",\"PageSize\":10}}";
		
		try
		{
		Bundle var = getArguments();
		if(var.containsKey("new"))
		{
			Log.v("TAg","new");
			json = "{\"Certified\":false,\"StockType\":\"new\",\"criteria\":{\"DealerSiteID\":103,\"PageIndex\":"+pageindex+",\"PageSize\":10}}";
			
		}
		else if(var.containsKey("used"))
		{
			Log.v("TAg","used");
			json = "{\"Certified\":false,\"StockType\":\"used\",\"criteria\":{\"DealerSiteID\":103,\"PageIndex\":"+pageindex+",\"PageSize\":10}}";
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	//	SetRayMenu();
		String userAgent = String.format(USER_AGENT,
				getAppVersion(getActivity()));
		this.restClient = new RestServiceClient(userAgent);
		new GetDataTask().execute();
		
		VehicleGarageList= (PullToRefreshListView) rootView.findViewById(android.R.id.list); 
		VehicleGarageList.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				pageindex++;
				new GetDataTask().execute();
			}
		});

		mListItems = new ArrayList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		
		
	/*	JSONObject ff=new JSONObject();
		try {
			ff.put("Certified", "false");
			ff.put("StockType", "");
			ff.put("Certified", "false");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
//		VehicleGarageList.setAdapter(new VehicleListAdapter(getActivity().getApplicationContext(),true, mListItems));
	/*	VehicleGarageList = (ListView) rootView.findViewById(R.id.listView1);
		VehicleGarageList
				.setAdapter(new VehicleListAdapter(getActivity(), true));*/
		VehicleGarageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Log.d("TAG","Clicked");
				GarageFragmentDetail newFragment = new GarageFragmentDetail();
				Bundle args = new Bundle();
				// args.putInt(ShowroomFragment.ARG_POSITION, position);s
				// newFragment.setArguments(args);

				FragmentTransaction transaction = getActivity()
						.getSupportFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this
				// fragment,
				// and add the transaction to the back stack so the user can
				// navigate back
				transaction.replace(R.id.fragment_container, newFragment);
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();

			}
		});

	
		
		return rootView;
	}

	private void SetRayMenu() {
		for(int i=0;i<drawables.length;i++)
		{
				ImageView item = new ImageView(getActivity());
				item.setImageResource(drawables[i]);
			   
				final int pos = i;
			   ray_menu.addItem(item, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(pos == 0)
					{
						Toast.makeText(getActivity(), "pos = 0", Toast.LENGTH_SHORT).show();
					}
					if(pos == 1)
					{
						Toast.makeText(getActivity(), "pos = 1", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@SuppressLint("NewApi")
		@Override
		protected String[] doInBackground(Void... params) {
			//	Thread.sleep(1000);
			//	json = "{\"Certified\":false,\"StockType\":\"new\",\"criteria\":{\"DealerSiteID\":103,\"PageIndex\":"+pageindex+",\"PageSize\":10}}";
				try {
					
					String response = handleMap2(runPost(credentials.commons.URL_FOR_WEBSERVICE, json));
					String finalresponse = removeChr(response,'\\');
					Log.v("TAG","-------------Final response-----------------:\n"+finalresponse);
					
					/*String ex2 = "[{\"ID\":1,\"RooftopID\":1,\"VIN\":\"JF1ZNAA16D1726926\",\"StockType\":\"Sold\",\"STOCK_NO\":\"S11152\",\"ENGINE\":\"2.0L\",\"TRANSMISSION\":\"6-Speed Manual\",\"COLOR\":\"RED\",\"DRIVE_TRAIN\":\"RWD\",\"TRIM\":\"\",\"MILEAGE\":10,\"STYLE\":\"2dr Car\",\"YEAR\":2013,\"MAKE_NAME\":\"Scion\",\"SERIES\":\"FR-S\",\"DAYS_IN_STOCK\":271,\"ORDER_CODE\":\"6253\",\"LIST_PRICE\":25885.0,\"MSRP\":25885.0,\"COST\":24491.52,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":0.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$25,885\",\"SalePrice\":\"$25,885\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":2,\"RooftopID\":1,\"VIN\":\"JTDJTUD37ED573789\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35316\",\"ENGINE\":\"1.5L\",\"TRANSMISSION\":\"4-Speed Automatic\",\"COLOR\":\"RED\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"L\",\"MILEAGE\":8,\"STYLE\":\"2dr Car\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Yaris\",\"DAYS_IN_STOCK\":207,\"ORDER_CODE\":\"1422\",\"LIST_PRICE\":16209.0,\"MSRP\":16209.0,\"COST\":15879.52,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":0.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$16,209\",\"SalePrice\":\"$16,209\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":3,\"RooftopID\":1,\"VIN\":\"4T3BK3BB9EU095762\",\"StockType\":\"New\",\"STOCK_NO\":\"T35413\",\"ENGINE\":\"3.5L V6 268hp 246ft. lbs.\",\"TRANSMISSION\":\"Automatic\",\"COLOR\":\"Magnetic Gray Metallic\",\"DRIVE_TRAIN\":\"AWD\",\"TRIM\":\"Limited\",\"MILEAGE\":16,\"STYLE\":\"AWD Limited V6 4dr Crossover\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Venza\",\"DAYS_IN_STOCK\":446,\"ORDER_CODE\":\"2846A\",\"LIST_PRICE\":41174.0,\"MSRP\":41174.0,\"COST\":38266.52,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":2500.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$41,174\",\"SalePrice\":\"$41,174\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":4,\"RooftopID\":1,\"VIN\":\"5TFDM5F1XEX051135\",\"StockType\":\"New\",\"STOCK_NO\":\"T35548\",\"ENGINE\":\"4.6L V8 310hp 327ft. lbs.\",\"TRANSMISSION\":\"Automatic\",\"COLOR\":\"Barcelona Red Metallic\",\"DRIVE_TRAIN\":\"4X4\",\"TRIM\":\"SR5\",\"MILEAGE\":11,\"STYLE\":\"4x4 SR5 4dr CrewMax Cab Pickup SB (4.6L V8)\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Tundra\",\"DAYS_IN_STOCK\":430,\"ORDER_CODE\":\"8359A\",\"LIST_PRICE\":39005.0,\"MSRP\":39005.0,\"COST\":36784.52,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":1000.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$39,005\",\"SalePrice\":\"$39,005\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":5,\"RooftopID\":1,\"VIN\":\"4T1BF1FK0EU737684\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35666\",\"ENGINE\":\"2.5L\",\"TRANSMISSION\":\"6-Speed Automatic\",\"COLOR\":\"WHITE\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"SE\",\"MILEAGE\":16,\"STYLE\":\"4dr Car\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Camry\",\"DAYS_IN_STOCK\":157,\"ORDER_CODE\":\"2546\",\"LIST_PRICE\":29888.0,\"MSRP\":29888.0,\"COST\":27284.52,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":1750.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$29,888\",\"SalePrice\":\"$29,888\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":6,\"RooftopID\":1,\"VIN\":\"4T1BK1EB2DU032719\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35878\",\"ENGINE\":\"3.5L\",\"TRANSMISSION\":\"6-Speed Automatic\",\"COLOR\":\"SILVER\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"XLE\",\"MILEAGE\":306,\"STYLE\":\"4dr Car\",\"YEAR\":2013,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Avalon\",\"DAYS_IN_STOCK\":130,\"ORDER_CODE\":\"3548\",\"LIST_PRICE\":36520.0,\"MSRP\":36520.0,\"COST\":33884.2,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":1500.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$36,520\",\"SalePrice\":\"$36,520\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":7,\"RooftopID\":1,\"VIN\":\"4T4BF1FK0ER351689\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35787\",\"ENGINE\":\"2.5L\",\"TRANSMISSION\":\"6-Speed Automatic\",\"COLOR\":\"RED\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"LE\",\"MILEAGE\":10,\"STYLE\":\"4dr Car\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Camry\",\"DAYS_IN_STOCK\":143,\"ORDER_CODE\":\"2532\",\"LIST_PRICE\":24155.0,\"MSRP\":24155.0,\"COST\":22411.24,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":0.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$24,155\",\"SalePrice\":\"$24,155\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":8,\"RooftopID\":1,\"VIN\":\"4T1BK1EB9EU078050\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35854\",\"ENGINE\":\"2GR7350238\",\"TRANSMISSION\":\"Automatic\",\"COLOR\":\"Classic Silver Metallic\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"Limited\",\"MILEAGE\":16,\"STYLE\":\"4dr Car\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Avalon\",\"DAYS_IN_STOCK\":384,\"ORDER_CODE\":\"4432A\",\"LIST_PRICE\":41334.0,\"MSRP\":41334.0,\"COST\":37910.24,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":2000.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$41,334\",\"SalePrice\":\"$41,334\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":9,\"RooftopID\":1,\"VIN\":\"4T4BF1FK2ER353153\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35850\",\"ENGINE\":\"2.5L\",\"TRANSMISSION\":\"6-Speed Automatic\",\"COLOR\":\"GREEN\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"XLE\",\"MILEAGE\":16,\"STYLE\":\"4dr Car\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Camry\",\"DAYS_IN_STOCK\":135,\"ORDER_CODE\":\"2540\",\"LIST_PRICE\":30004.0,\"MSRP\":30004.0,\"COST\":27370.24,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":0.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$30,004\",\"SalePrice\":\"$30,004\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false},{\"ID\":10,\"RooftopID\":1,\"VIN\":\"5YFBURHE0EP025284\",\"StockType\":\"Sold\",\"STOCK_NO\":\"T35818\",\"ENGINE\":\"1.8L\",\"TRANSMISSION\":\"Automatic\",\"COLOR\":\"SILVER\",\"DRIVE_TRAIN\":\"FWD\",\"TRIM\":\"LE Plus\",\"MILEAGE\":12,\"STYLE\":\"4dr Car\",\"YEAR\":2014,\"MAKE_NAME\":\"Toyota\",\"SERIES\":\"Corolla\",\"DAYS_IN_STOCK\":135,\"ORDER_CODE\":\"1854\",\"LIST_PRICE\":20794.0,\"MSRP\":20794.0,\"COST\":19401.24,\"InternetPrice\":null,\"Special_Price\":0.0,\"REBATE\":0.0,\"DEALER_CASH\":0.0,\"RegionalDiscountAmount\":0.00,\"DBWebPrice\":0.00,\"MinimumPriceDisplay\":10000.00,\"ShowRegionalDiscount\":true,\"RetailPriceText\":\"Internet Price: \",\"SalePriceText\":\"Sale Price: \",\"RetailPrice\":\"$20,794\",\"SalePrice\":\"$20,794\",\"RebatesText\":null,\"Rebates\":null,\"DiscountsText\":\"Dealer Discounts: \",\"Discounts\":\"$00\",\"WebPriceText\":null,\"WebPrice\":null,\"BigPrice\":null,\"RetailPriceText_Visible\":true,\"SalePriceText_Visible\":false,\"RetailPrice_Visible\":true,\"SalePrice_Visible\":false,\"RebatesText_Visible\":false,\"Rebates_Visible\":false,\"DiscountsText_Visible\":false,\"Discounts_Visible\":false,\"WebPriceText_Visible\":false,\"WebPrice_Visible\":false,\"ePriceButton_Visible\":false}]";
					String ex = removeChr(ex2,'\\');
					Log.v("TAG","-------------Final response-----------------:\n"+finalresponse);
					Log.v("TAG","-----------EX1-----------------------:\n"+ex);
			  	    Log.v("TAG","l1="+fs.length()+"   l2="+ex.length());
					*/
					Log.v("TAG","-----------EX1-----------------------:\n");
					String fs = finalresponse.substring(1, finalresponse.length()-1);
					
					JSONArray responsear = new JSONArray(fs);
					int l=responsear.length();
				
					
					for(int i=0;i<l;i++)
					{
						// Year		//	Make	//	Model	//	TRIM
						//+responsear.getJSONObject(i).getString("MODEL")  SERIES
						
						String vnameString= responsear.getJSONObject(i).getString("YEAR")+" "+responsear.getJSONObject(i).getString("MAKE_NAME")+responsear.getJSONObject(i).getString("SERIES")+" "+responsear.getJSONObject(i).getString("TRIM");
						vname.add(vnameString);
						vmileage.add(responsear.getJSONObject(i).getString("MILEAGE"));
						img_url.add(responsear.getJSONObject(i).getString("IMG_URL"));
					}
					
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							VehicleGarageList.setAdapter(new VehicleListAdapter(getActivity().getApplicationContext(),true, vname,vmileage,img_url));
						}
					});
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return mStrings;
		}
		private InputStream runPost(String url, String json) throws ApiException, FileNotFoundException
		{
			try
			{
				InputStream isRaw = restClient.postBinary(url, json);
				Log.v("TAG", "ISTRING:"+isRaw.toString());
				return isRaw;
			} catch (RestException e)
			{
				throw new ApiException("Failed to run GetAllView", e);
			}
		}

		public String handleMap2(InputStream is) throws IOException, JSONException
		{
			String result = null;
			Log.v("TAG","-----------------------IS:"+is.toString());
			result = convertStreamToString(is);
		//	Log.d("DELETE RESULT", result.toString());
			// listMap.remove(location);
			return result;
		}

		public String convertStreamToString(InputStream is)
		{
			BufferedReader reader=new BufferedReader(new InputStreamReader(is),8192) ;
			StringBuilder sb=new StringBuilder() ;
			String line=null ;
			try
			{
				while((line=reader.readLine())!=null)
				{
					sb.append(line+"\n") ;
				}
			}catch(IOException e)
			{
				e.printStackTrace() ;
			}finally
			{
				try
				{
					is.close() ;
				}catch(IOException e)
				{
					e.printStackTrace() ;
				}
			}
			return sb.toString() ;
		}
		

		@Override
		protected void onPostExecute(String[] result) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					
					Log.v("TAG","Added items after refresh..");
					// Call onRefreshComplete when the list has been refreshed.
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							VehicleGarageList.onRefreshComplete();							
						}
					});
				
					
				}
				});
			

			super.onPostExecute(result);
		}
	}
	private String getAppVersion(Context context)
	{
		String appVersion = "";
		try
		{
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			appVersion = info.versionName;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return appVersion;
	}
	private String[] mStrings = { "Andaman and Nicobar Islands",
			"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
			"Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
			"Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala",
			"Madhya Pradesh", "Maharashtra", "Manipur"};


public static String removeChr(String str, char x){
	StringBuilder strBuilder = new StringBuilder();
	char[] rmString = str.toCharArray();
	for(int i=0; i<rmString.length; i++)
	{
		if(rmString[i] == x)
		{
			
		} else {
			strBuilder.append(rmString[i]);
		}
	}
	return strBuilder.toString();
}
}