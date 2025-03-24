package com.kunal.journalApp.service;

import com.kunal.journalApp.api.response.WeatherResponse;
import com.kunal.journalApp.cache.AppCache;
import com.kunal.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {


    @Value("${weather.api.key}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;


    public WeatherResponse getWeather(String city) {

        WeatherResponse cachedWeather = redisService.get("weather_of_" + city, WeatherResponse.class);

        if (cachedWeather != null) {
            return cachedWeather;
        } else {
            String replace = appCache.getAppCache().get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, API_KEY).replace(Placeholders.CITY, city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(replace, HttpMethod.POST, null, WeatherResponse.class);

            WeatherResponse body = response.getBody();

            if (body != null) {
                redisService.set("weather_of_" + city, body, 300L);
            }

            return body;
        }

//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        // Sending headers
//        httpHeaders.set("key", "value");
//
        // Sending user to request body
//        Users user = Users.builder().username("admin").password("123").build();
//
//        HttpEntity<Users> requestEntity = new HttpEntity<>(user, httpHeaders);

//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(replace, HttpMethod.GET, null, WeatherResponse.class);


    }

}
