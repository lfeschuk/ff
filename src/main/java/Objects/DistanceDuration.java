package Objects;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.leonid.jetpack.PendingDeliveriesForGuyActivity;
import com.google.android.gms.maps.model.LatLng;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DistanceDuration {
    public static final String TAG = "DistanceDuration";
    public  Integer duration = null;
    public PendingDeliveriesForGuyActivity ac;
    public static int count = 0;
    public int size;
    Delivery to_assign_delivery;
    Boolean update_delivery_guy_with_moved_delivery;
    String index_index_update_Delivery_guy;

    public String curr_url = "";
    Boolean update_db_after_assign;

    public void start_duration_exec(  LatLng origin,  LatLng dest ,PendingDeliveriesForGuyActivity callback_activity,Boolean update_db_after_assign,int size,Delivery to_assign_delivery
            ,Boolean update_delivery_guy_with_moved_delivery,String index_index_update_Delivery_guy)
    {
        Log.d(TAG,"start exec lat1: " +origin.latitude + " long1: " +origin.longitude + " lat2: " +dest.latitude + " long2: "+ dest.longitude);
        this.size = size;
        ac = callback_activity;
        this.update_db_after_assign = update_db_after_assign;
        String url = getDirectionsUrl(origin, dest);
        curr_url = url;
        this.to_assign_delivery = to_assign_delivery;
        this.update_delivery_guy_with_moved_delivery = update_delivery_guy_with_moved_delivery;
        this.index_index_update_Delivery_guy = index_index_update_Delivery_guy;

        DistanceDuration.DownloadTask downloadTask = new DistanceDuration.DownloadTask();

// Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }
    public Integer getDuration()
    {
        return duration;
    }
    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public PendingDeliveriesForGuyActivity getCallBackActivity() {
        return ac;
    }

    public int getSize() {
        return size;
    }

    public String getDirectionsUrl(LatLng origin, LatLng dest){

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

        String key = "&key=" + "AIzaSyDoRFQVaeh76RYJnlfQpCpuATe2xqmfcAI";

// Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+key;
        Log.d(TAG,"url: " + url);

        return url;
    }
    public String getDistanceToClosestGasStation(LatLng origin){

// Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

// Destination of route
       String by_distance = "rankby=distance";
       String gas_station = "type=gas_station";

// Sensor enabled
        String sensor = "sensor=false";

// Building the parameters to the web service
        String parameters = str_origin+"&"+by_distance+"&"+gas_station+"&"+sensor;

// Output format
        String output = "json";

// Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    public String downloadUrl(String strUrl) throws IOException {
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
            Log.d("Exception whding url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    public class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

// For storing data from web service
            String data = "";

            try{
// Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d(TAG,"data:" +data);
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
            Log.d(TAG,"DownloadTask");
            parserTask.execute(result);

        }
    }

    /** A class to parse the Google Places in JSON format */
    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        int a = 0;
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
                Log.d(TAG,"routes size: " + routes.size());
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList points = null;
//            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            Log.d(TAG,"ParserTask");
            if(result.size()<1){
//                a++;
                Log.d(TAG,"ff");
                count++;
                setDuration(999);
                Toast.makeText(ac, "שים לב כי חישוב זמן אינו נכון, ייתכן כי השליח לא הפעיל את שרותי המיקום", Toast.LENGTH_LONG).show();
//                if (a >= 5)
//                {
//                    return;
//                }
                if (count == getSize() )
                {
                    count = 0;
                    ac.updateUiCallback(update_db_after_assign,to_assign_delivery,update_delivery_guy_with_moved_delivery,index_index_update_Delivery_guy);
                }
//                Toast.makeText(ac, "No Points", Toast.LENGTH_SHORT).show();
//                Log.d(TAG,"execute another time");
//                DistanceDuration.DownloadTask downloadTask = new DistanceDuration.DownloadTask();
//
//// Start downloading json data from Google Directions API
//                downloadTask.execute(curr_url);
                return;
            }

// Traversing through all the routes
            for(int i=0;i<result.size();i++){
//                points = new ArrayList();
//                lineOptions = new PolylineOptions();

// Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

// Fetching all the points in i-th route
                for(int j=0;j <path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){ // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
                }

// Adding all the points in the route to LineOptions
//                lineOptions.addAll(points);
//                lineOptions.width(2);
//                lineOptions.color(Color.RED);

            }
            Log.d(TAG,"Distance:"+distance + ", Duration:"+duration);
            int out_duration = 0;

            String[] s=duration.split("\\D+");
            int[] intarray=new int[s.length];
            for(int i=0;i<s.length;i++){
                intarray[i]=Integer.parseInt(s[i]);
            }
             if (intarray.length ==1)
            {
                out_duration = intarray[0];
            }
            else if (intarray.length == 2)
            {
                out_duration = intarray[0]*60 + intarray[1];
            }
            else
             {
                 Log.d(TAG,"wrong duration perhaps contains days : " + duration);
             }
            Log.d(TAG, "out duration: " + out_duration  + "count " + count + " size " + getSize());
            setDuration(out_duration);
            count++;
            if (count == getSize() )
            {
                count = 0;
                ac.updateUiCallback(update_db_after_assign,to_assign_delivery,update_delivery_guy_with_moved_delivery,index_index_update_Delivery_guy);
            }

            //  tvDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);

// Drawing polyline in the Google Map for the i-th route
            //   map.addPolyline(lineOptions);
        }
    }
}
