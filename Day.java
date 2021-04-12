package Lesson_7;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

    @JsonIgnoreProperties(ignoreUnknown = true)

    public class Day implements Serializable {
        @JsonProperty(value = "IconPhrase")
        public String value;
    }
