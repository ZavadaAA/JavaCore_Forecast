package Lesson_7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature implements Serializable {
    @JsonProperty(value = "Minimum")
    public Minimum minimum;
   @JsonProperty(value = "Maximum")
  public Maximum maximum;
}