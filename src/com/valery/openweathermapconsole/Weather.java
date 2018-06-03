package com.valery.openweathermapconsole;

import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class Weather {

    public static void main(String[] args) throws IOException, JSONException {

        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter city: ");
        String city = scan.nextLine();
        System.out.println();

        WeatherService service = new WeatherService();
        WeatherData weather = service.getWeather(city);

        System.out.println(String.format("City: %s", weather.city));
        System.out.println(String.format("Temperature: %s°", weather.temperature));
        System.out.println(String.format("Pressure: %d hPa", weather.pressure));
        System.out.println(String.format("Humidity: %d%%", weather.humidity));
        System.out.println(String.format("Weather conditions: %s", weather.weatherCondition));
        System.out.println(String.format("Wind speed: %d meter/sec", weather.windSpeed));
        System.out.println();
    }
}