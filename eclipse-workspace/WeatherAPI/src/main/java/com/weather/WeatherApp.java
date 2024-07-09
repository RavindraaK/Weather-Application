package com.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {
    private static final String API_KEY = "9e534c30914994c074d9a52ef90679b0"; // Replace with your OpenWeatherMap API key

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter city name: ");
            String city = reader.readLine();

            // Make API request to get weather data
            JSONObject weatherData = getWeatherData(city);

            // Display weather information
            if (weatherData != null) {
                displayWeatherInfo(weatherData);
            } else {
                System.out.println("Failed to retrieve weather information.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static JSONObject getWeatherData(String city) {
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new JSONObject(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void displayWeatherInfo(JSONObject weatherData) {
        JSONObject main = weatherData.getJSONObject("main");
        JSONObject weather = weatherData.getJSONArray("weather").getJSONObject(0);

        double temperature = main.getDouble("temp") - 273.15; // Convert temperature from Kelvin to Celsius
        double humidity = main.getDouble("humidity");
        String description = weather.getString("description");

        System.out.println("Temperature: " + String.format("%.2f", temperature) + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Description: " + description);
    }
}

