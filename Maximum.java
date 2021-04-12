package Lesson_7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Maximum implements Serializable {
    @JsonProperty(value = "Value")
    public Double value;
    @JsonProperty(value = "Unit")
    public String unit;
    @Override
    public String toString() {return "Максимальная температура: "+value+" " +unit;}
}