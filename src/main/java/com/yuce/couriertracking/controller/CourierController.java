package com.yuce.couriertracking.controller;

import com.yuce.couriertracking.service.ICourierService;
import com.yuce.couriertracking.service.impl.CourierService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/courier")
public class CourierController {
    private final ICourierService courierService;

    public CourierController(ICourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping("/updateLocation")
    public String updateLocation(@RequestParam String courierId,
                                 @RequestParam double lat,
                                 @RequestParam double lng,
                                 @RequestParam LocalDateTime time) {

        return courierService.updateCourierLocation(courierId, lat, lng, time);
    }

    @GetMapping("/totalTravelDistance/{courierId}")
    public Double getTotalTravelDistance(@PathVariable String courierId) {
        return courierService.getTotalTravelDistance(courierId);
    }
}
