package Lesson_7;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class AccuWeatherModel implements WeatherProvider {

    public static final String BASE_HOST = "dataservice.accuweather.com";
    public static final String FORECASTS = "forecasts";
    private static final String CURRENT_CONDITIONS_ENDPOINT = "currentconditions";
    public static final String API_VERSION = "v1";
    public static final String PERIOD = "daily";
    public static final String TOP_CITIES = "topcities";
    public static final String CITIES_NUMBER = "150";
    public static final String LOCATIONS = "locations";
    public static final String API_KEY = "bY9sFyXGO5fB2bwx9ueDnA6mmqcow3Hn";
    private static final OkHttpClient client = new OkHttpClient();
    private static final String AUTOCOMPLETE_ENDPOINT = "autocomplete";

    public static void getWeather(Periods periods, String city) throws IOException {

        String cityKey = setGlobalCity(city);
        System.out.println("Код города: " + cityKey);
        if (periods.equals(Periods.IN5DAYS)) {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host(BASE_HOST)
                    .addPathSegment(FORECASTS)
                    .addPathSegment(API_VERSION)
                    .addPathSegment(PERIOD)
                    .addPathSegment("5day")
                    .addPathSegment(cityKey)
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("language", "ru-ru")
                    .addQueryParameter("metric", "true") //градусы Цельсия
                    .build();

            Request request = new Request.Builder()
                    .addHeader("accept", "application/json")
                    .url(url)
                    .build();
            String jsonResponse = client.newCall(request).execute().body().string();
            //выводим результат прогноза в файл
            File file = new File("./forecast_5days.json");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (PrintWriter out = new PrintWriter(file)) {
                out.print(jsonResponse);
                System.out.println("Погода на ближайшие 5 дней записана в файл " + file.getName());
            }


        }

        else if (periods.equals(Periods.NOW)) {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host(BASE_HOST)
                    .addPathSegment(FORECASTS)
                    .addPathSegment(API_VERSION)
                    .addPathSegment(PERIOD)
                    .addPathSegment("NOW")
                    .addPathSegment(cityKey)
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("language", "ru-ru")
                    .addQueryParameter("metric", "true") //градусы Цельсия
                    .build();

            Request request = new Request.Builder()
                    .addHeader("accept", "application/json")
                    .url(url)
                    .build();
            String jsonResponse = client.newCall(request).execute().body().string();
            //выводим результат прогноза в файл
            File file = new File("./forecast_today.json");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (PrintWriter out = new PrintWriter(file)) {
                out.print(jsonResponse);
                System.out.println("Погода на текущие сутки записана в файл " + file.getName());
            }


        }
        //десериализация и вывод в консоль
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WeatherResponse weatherResponse = objectMapper.readValue(new File("forecast_5days.json"), WeatherResponse.class);
        for (int i = 0; i < weatherResponse.dailyForecasts.size(); i++) {
            System.out.println("В городе " + city + " на дату " + weatherResponse.dailyForecasts.get(i).date + " ожидается " + weatherResponse.dailyForecasts.get(i).day.value + ", минимальная температура в этот день: " + weatherResponse.dailyForecasts.get(i).temperature.minimum.value + "C" + ", максимальная: " + weatherResponse.dailyForecasts.get(i).temperature.maximum.value + "C");
        }
    }

    //узнаем id города
    public static String setGlobalCity(String mycity) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(API_VERSION)
                .addPathSegment(TOP_CITIES)
                .addPathSegment(CITIES_NUMBER)
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("language", "ru-ru")
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(url)
                .build();
        String jsonResponse = client.newCall(request).execute().body().string();

        File file = new File("./top150cities.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(jsonResponse);
            //из этого файла найдем id города
            System.out.println("Метеорологические данные по 150 самым крупным городам записаны в файл " + file.getName());
        }
        //ищем в файле код города
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Ищем город: " + mycity);
        String cityKey = " ";
        String cityName = " ";
        int i = 0;
        try {
            while (!(cityName.equals(mycity))) {
                //код города
                JsonNode key1 = objectMapper.readTree(new File("top150cities.json"))
                        .get(i).at("/Key");
                cityKey = key1.asText();
                //имя города
                JsonNode key2 = objectMapper.readTree(new File("top150cities.json"))
                        .get(i).at("/EnglishName");
                cityName = key2.asText();
                i = i + 1;
            }
        } catch (NullPointerException e) {
            System.out.println("Город "+mycity+ " не найден");
            System.exit(-1);
        }
        return cityKey;
    }
}