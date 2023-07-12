package com.yuce.couriertracking.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Store {
    private String name;
    private double lat;
    private double lng;

    @Builder
    public Store(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}
