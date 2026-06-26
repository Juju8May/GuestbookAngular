package com.julia.guestbook.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.julia.guestbook.entity.Weather.WeatherData;
import com.julia.guestbook.entity.Weather.WeatherResponse;

@Service
public class WeatherService {
    
    private static final Logger logger = Logger.getLogger(WeatherService.class.getName());
    private RestClient openWeatherAPIClient;
    public  WeatherResponse openWeatherResponse;
    @Value("${weather.api.key}") String weatherApiKey;
    @Value("${weather.api.base-url}") String weatherApiBaseUrl;
    @Value("${weather.location.city}") String cityName;

    public WeatherService() {
        this.openWeatherAPIClient = RestClient.create(weatherApiBaseUrl);
    }

    private WeatherResponse retrieveWeatherInfoFromOpenWeather(String city) {
        return openWeatherAPIClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("")
                        .queryParam("q", city)
                        .queryParam("appid", weatherApiKey)
                        .queryParam("units", "metric")
                        .queryParam("lang", "de")
                        .build())
                .retrieve()
                .body(WeatherResponse.class);
    }

    public String getCurrentWeather(String city) {
        try {
             openWeatherResponse = retrieveWeatherInfoFromOpenWeather(city);
            if (openWeatherResponse.getMain() != null) {
                WeatherData data = openWeatherResponse.getMain();
                return String.format("%s: %.1f°C, %s",
                        city,
                        data.getTemp(),
                        openWeatherResponse.getWeather() != null && !openWeatherResponse.getWeather().isEmpty()
                                ? openWeatherResponse.getWeather().get(0).getDescription()
                                : "Keine Beschreibung");
            } else {
                return String.format("%s: Kein Wetter verfügbar", city);
            }
        } catch (RestClientException e) {
            logger.severe("Error fetching weather data: " + e.getMessage());
            return String.format("%s: Die Daten konnten nicht abgerufen werden", city);
        }
    }
    public String getIconUrl(String weatherIconCode) {
        return String.format("https://openweathermap.org/img/wn/%s@2x.png", weatherIconCode);
    }
    public String getCurrentWeatherIcon(String city) {
        try {
            if (openWeatherResponse.getWeather() != null && !openWeatherResponse.getWeather().isEmpty()) {
                String iconCode = openWeatherResponse.getWeather().get(0).getIcon();
                return getIconUrl(iconCode);
            } else {
                return "Kein Wetter-Icon verfügbar";
            }
        } catch (RestClientException e) {
            logger.severe("Error fetching weather data: " + e.getMessage());
            return "Kein Wetter-Icon verfügbar";
        }
    }
}
