package com.julia.guestbook.entity.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RainData {
    @JsonProperty("1h")
    private Double oneHour;

    public Double getOneHour() {
        return oneHour;
    }

    public void setOneHour(Double oneHour) {
        this.oneHour = oneHour;
    }
}
