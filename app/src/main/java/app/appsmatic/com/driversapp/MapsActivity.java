package app.appsmatic.com.driversapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.appsmatic.com.driversapp.GPS.DirectionsJSONParser;
import app.appsmatic.com.driversapp.GPS.GPSTracker;

public class MapsActivity extends FragmentActivity  {

    private GoogleMap map;
    ArrayList<LatLng> markerPoints;
    private Button close;


    Float lat;
    Float lng;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //set local default english
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //Get Lat and Lng
        Bundle extras = getIntent().getExtras();
        lat = Float.parseFloat(extras.get("lat").toString());
        lng = Float.parseFloat(extras.get("lng").toString());
        name=extras.getString("custname");

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        // Getting Map for the SupportMapFragment
       //  map = fm.getMapAsync;

        if (map != null) {

            // Enable MyLocation Button in the Map
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);


            // Already two locations
            if(markerPoints.size()>1){
                markerPoints.clear();
                map.clear();
            }

            GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
           Double x=gpsTracker.getLatitude();
           Double y=gpsTracker.getLongitude();

            //Current location
            LatLng point=new LatLng(x,y);


            //Target location
            LatLng point2=new LatLng(lat,lng);
            // Adding new item to the ArrayList
            markerPoints.add(point);
            markerPoints.add(point2);

            // Creating MarkerOptions
            MarkerOptions options = new MarkerOptions();

            // Setting the position of the marker
            options.position(point);
            options.position(point2);


            float zoomLevel = (float) 16.0; //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,zoomLevel));
            map.addMarker(new MarkerOptions().position(point2).title(name));

            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of marker is RED.
             */
            if(markerPoints.size()==1){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }else if(markerPoints.size()==2){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

            // Add new marker to the Google Map Android API V2
            map.addMarker(options);

            // Checks, whether start and end locations are captured
            if(markerPoints.size() >= 2){
                LatLng origin = markerPoints.get(0);
                LatLng dest = markerPoints.get(1);

                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(origin, dest);

                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }

        }













        //Close Button
        close=(Button)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }



    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);

        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
