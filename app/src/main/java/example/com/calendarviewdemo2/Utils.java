package example.com.calendarviewdemo2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<Event> fetchEvents(String requestUrl){
        String jsonResponse = "";

        try{
            URL url = new URL(requestUrl);
            jsonResponse = makeHttpRequest(url);
        } catch (MalformedURLException e) {
            Log.i("error","creating url");
              e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Event> events = extractFeatureFromJSON(jsonResponse);
        return events;
    }

    private static ArrayList<Event> extractFeatureFromJSON(String jsonResponse) {
        ArrayList<Event> events = new ArrayList<>();
        if(jsonResponse == null|| jsonResponse.isEmpty())
            return null;

        try {
            JSONArray arr = new JSONArray();
            for(int i = 0; i<arr.length(); ++i){
                JSONObject properties = arr.getJSONObject(i);

                int id = properties.getInt("id");
                String name = properties.getString("name");
                String orgSociety = properties.getString("orgSociety");
                String details = properties.getString("Details");
                String location = properties.getString("nsit campus");
                String link = properties.getString("link");
                String createdAt = properties.getString("createdAt");
                String updatedAt = properties.getString("updatedAt");


                String startdate = properties.getString("startdate");
                String enddate = properties.getString("enddate");
                String starttime = properties.getString("starttime");
                String endtime = properties.getString("endtime");

                events.add(new Event(id,name,startdate,enddate,starttime,endtime,orgSociety,details,location,link,createdAt,updatedAt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url == null)
            return "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {

        StringBuilder output = new StringBuilder();
        if(inputStream == null)
            return output.toString();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String line = null;
        try {
            line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
