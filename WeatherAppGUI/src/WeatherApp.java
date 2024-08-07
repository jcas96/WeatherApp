//retrieving weather data from API = backend logic for getting latest weather
//data from the external API and return it for later use/.

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){

        //get the location data from user using geolocation API
        JSONArray locationData = getLocationData(locationName);

        return null;
    }

    //finds and retrieves coordinates for location
    public static JSONArray getLocationData(String locationName){
        //replace whitespace in location Name to + to fit the API request

        locationName = locationName.replaceAll(" ","+");


        //building API url with loc parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+
                locationName+"&count=10&language=en&format=json";

        try{
            //calls api and gets your response
            HttpURLConnection conn = fetchApiRes(urlString);

            //check status of response
            //200== successful
            if(conn.getResponseCode()!=200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else{

                StringBuilder resultJson = new StringBuilder();
                Scanner input = new Scanner(conn.getInputStream());

                //read and store the resulting JSON data into S builder
                while(input.hasNext()){
                    resultJson.append(input.nextLine());
                }

                input.close();

                conn.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        //if no coordinates found
        return null;
    }

    private static HttpURLConnection fetchApiRes(String urlString){
        try{
            //attempts to create connection with api link
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //set request method to GET. since we are getting data

            conn.setRequestMethod("GET");

            //connect to URL
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }
        //if connection couldn't be made
        return null;
    }
}
