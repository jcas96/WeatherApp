//retrieving weather data from API = backend logic for getting latest weather
//data from the external API and return it for later use/.

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){

        //get the location data from user using geolocation API
        JSONArray locationData = getLocationData(locationName);

        //retrieve longitude and latitude from data

        JSONObject location = (JSONObject) locationData.get(0);

        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        //build api request URL with loc coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+
                "&longitude="+longitude+"&hourly=temperature_2m,weather_code&temperature_unit=fahrenheit&precipitation_unit=inch&timezone=America%2FLos_Angeles";
        try{
            HttpURLConnection conn = fetchApiRes(urlString);


            //checks for 200 response
            if(conn.getResponseCode()!=200){
                //future change to out.printf
                System.out.println("Error: Could not connect");
                return null;
            }

            //store output json data
            StringBuilder resultJson = new StringBuilder();
            Scanner input = new Scanner(conn.getInputStream());
            while(input.hasNext()){
                //read and stored
                resultJson.append(input.nextLine());
            }

            input.close();

            //close connection
            conn.disconnect();

            //parse through json data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            JSONArray tempData = (JSONArray) hourly.get("temperature_2m");
            double temp = (double)tempData.get(index);

            //JSONArray weathercode = (JSONArray) hourly.get("weathercode");
            //String weatherCon = convertWeatherCode((long)weathercode.get(index));


            //building JSON data object that is going to be accessed by frontend

            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temp);
            //weatherData.put("weather_condition",weatherCon);

            return weatherData;
        }catch(Exception e){
            e.printStackTrace();
        }
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

    private static int findIndexOfCurrentTime(JSONArray timeList){
        String current = getCurrentTime();

        for(int i=0;i<timeList.size();i++){
            String time = (String)  timeList.get(i);
            if(time.equalsIgnoreCase(current)){
                return i;
            }
        }
        return 0;
    }

    private static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        //2024-08-7T00:00
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH':00'");

        //format and print current time
        String formattedDateTime = currentDateTime.format(format);
        return formattedDateTime;
    }

    private static String convertWeatherCode(long weathercode){
        String weatherCon="";
        if(weathercode==0L){
            weatherCon="Clear";
        }else if(weathercode>0L && weathercode<=3L){
            weatherCon ="Cloudy";
        }else if((weathercode>=51L && weathercode<=57L)||(weathercode>=80L && weathercode<=99L)){
            weatherCon="Rain";

        }else if(weathercode>=71L && weathercode<=77L){
            weatherCon ="Snow";
        }

        return weatherCon;
    }
}
