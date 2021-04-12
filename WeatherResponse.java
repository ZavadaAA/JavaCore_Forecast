package Lesson_7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse implements Serializable {
    @JsonProperty(value = "Headline")
    public Headline headline;
    @JsonProperty(value = "DailyForecasts")
    public List<DailyForecasts> dailyForecasts;
    @Override
    public String toString() {return dailyForecasts.toString()+"\r\n";}
}