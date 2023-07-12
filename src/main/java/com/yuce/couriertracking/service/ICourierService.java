package com.yuce.couriertracking.service;

import java.time.LocalDateTime;

public interface ICourierService {

    String updateCourierLocation(String courierId, double lat, double lng, LocalDateTime time);

    Double getTotalTravelDistance(String courierId);
}
