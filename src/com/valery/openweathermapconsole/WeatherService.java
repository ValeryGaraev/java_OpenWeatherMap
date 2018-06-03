package com.valery.openweathermapconsole;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private JSONObject fetchData(String city) throws IOException, JSONException {

        String query = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=9f1c6da33186cff18e6aa50ac4532cc0&units=metric", city);

        URL url = new URL(query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) connection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }

    private WeatherData parseData(JSONObject json) {

        WeatherData weather = new WeatherData();

        try {
            weather.city = json.getString("name");
            weather.temperature = String.valueOf(json.getJSONObject("main").getDouble("temp"));
            weather.pressure = json.getJSONObject("main").getInt("pressure");
            weather.humidity = json.getJSONObject("main").getInt("humidity");
            weather.weatherCondition = json.getJSONArray("weather").getJSONObject(Integer.parseInt("0")).getString("description");
            weather.windSpeed = json.getJSONObject("wind").getInt("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather;
    }

    public WeatherData getWeather(String city) throws IOException, JSONException {

        JSONObject json = fetchData(city);
        return parseData(json);
    }
}