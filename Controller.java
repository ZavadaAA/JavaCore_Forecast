package Lesson_7;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static Lesson_7.Periods.IN5DAYS;
import static Lesson_7.Periods.NOW;

public class Controller {
    WeatherProvider weatherProvider = new AccuWeatherModel();
    Map<Integer, Functionality> variantResult = new HashMap();

    public Controller() {
        variantResult.put(1, Functionality.GET_WEATHER_IN_NEXT_5_DAYS);
        variantResult.put(2, Functionality.GET_CURRENT_WEATHER);
    }

    public void onUserInput(String input) throws IOException {
        int command = Integer.parseInt(input);
        if (!variantResult.containsKey(command)) {
            throw new IOException("Таковой команды нет " + command);
        }
        switch (variantResult.get(command)) {
            case GET_WEATHER_IN_NEXT_5_DAYS:
                WeatherProvider.getWeather(IN5DAYS);
                break;
        }
        switch (variantResult.get(command)) {
            case GET_CURRENT_WEATHER:
                WeatherProvider.getWeather(NOW);
                break;
        }

    }

}