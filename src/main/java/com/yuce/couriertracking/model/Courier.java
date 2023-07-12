package com.yuce.couriertracking.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Courier {
    private String courierId;
    private double lat;
    private double lng;
    private LocalDateTime time;

    @Builder
    public Courier(String courierId, double lat, double lng, LocalDateTime time) {
        this.courierId = courierId;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

}
