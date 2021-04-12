package Lesson_7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecasts  implements Serializable {
    @JsonProperty(value = "Date")
    public String date;
    @JsonProperty(value = "Temperature")
    public Temperature temperature;
    @JsonProperty(value = "Day")
    public Day day;
    @Override
    public String toString() {
        return "Прогноз на "+date+"\r\n";
    }
}