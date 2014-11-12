package com.interactive360.brentbrown;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FindMyCarActivity extends Activity implements LocationListener{
	 
		MapView mapView;
		GoogleMap map;
		private LocationManager locationManager;
		
		double lat;
		double lng;
		private String provider;

		private MapView mapview;




		boolean sat = true;
		boolean dra = true;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			 setContentView(R.layout.add_my_car);
			 

		 		// Gets the MapView from the XML layout and creates it
				mapView = (MapView) findViewById(R.id.mapview);
				mapView.onCreate(savedInstanceState);
		 
				// Gets to GoogleMap from the MapView and does initialization stuff
				map = mapView.getMap();
				map.getUiSettings().setMyLocationButtonEnabled(false);
				map.setMyLocationEnabled(true);
		 
				MapsInitializer.initialize(this);
				
				  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				    Criteria criteria = new Criteria();
				    String provider = locationManager.getBestProvider(criteria, false);
				    Location location = locationManager.getLastKnownLocation(provider);

				    if (location != null) {
				        System.out.println("Provider " + provider + " has been selected.");
				        lat = (double) (location.getLatitude());
				        lng = (double) (location.getLongitude());

				        Log.i("TAG", "Lattitude:" + lat);
				        Log.i("TAG", "Longitude:" + lng);
				        Toast.makeText(
				                this,
				                "Current location:\nLatitude: " + lat + "\n"
				                        + "Longitude: " + lng, Toast.LENGTH_LONG).show();
				        // create geo point
				   /*     GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

				        // get the MapController object
				        controller = mapview.getController();

				        // animate to the desired point
				        controller.animateTo(point);

				        // set the map zoom to 13
				        // zoom 1 is top world view
				        controller.setZoom(13);

				        // invalidate the map in order to show changes
				        mapview.invalidate();

				        drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
				        OverlayItem overlayItem = new OverlayItem(point, "", "");
				        itemizedOverlay = new MyItemizedOverlay(drawable, this);
				        itemizedOverlay.addOverlay(overlayItem);
				        mapview.getOverlays().add(itemizedOverlay);*/
				        mapview.invalidate();
				    } else {

				        System.out.println("Location not avilable");
				    }

				    // when the current location is found – stop listening for updates
				    // (preserves battery)
				    locationManager.removeUpdates(this);
				
				// Updates the location and zoom of the MapView
//				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(27.00, 78.99), 10);
//				map.animateCamera(cameraUpdate);
		 
		}
		
	 
/*		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.add_my_car, container, false);
	 
	 		// Gets the MapView from the XML layout and creates it
			mapView = (MapView) v.findViewById(R.id.mapview);
			mapView.onCreate(savedInstanceState);
	 
			// Gets to GoogleMap from the MapView and does initialization stuff
			map = mapView.getMap();
			map.getUiSettings().setMyLocationButtonEnabled(false);
			map.setMyLocationEnabled(true);
	 
			MapsInitializer.initialize(this.getActivity());
			
			// Updates the location and zoom of the MapView
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
			map.animateCamera(cameraUpdate);
	 
			return v;
		}
	 */
		@Override
		public void onResume() {
			mapView.onResume();
			super.onResume();
		}
	 
		@Override
		public void onDestroy() {
			super.onDestroy();
			mapView.onDestroy();
		}
	 
		@Override
		public void onLowMemory() {
			super.onLowMemory();
			mapView.onLowMemory();
		}


		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	 
	}