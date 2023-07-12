package com.yuce.couriertracking.service.impl;

import com.yuce.couriertracking.data.StoreDataService;
import com.yuce.couriertracking.model.Courier;
import com.yuce.couriertracking.model.Store;
import com.yuce.couriertracking.service.ICourierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CourierService implements ICourierService {

    private final Map<String, Double> totalDistances = new HashMap<>(); //Her bir kuryenin toplam mesafesini tutmak için kullanılan map
    private final Map<String, Courier> couriersLastLocation = new HashMap<>(); // Her bir kuryenin son bilinen konumunu tutmak için kullanılan map
    private final Map<String, LocalDateTime> courierStoreEntryTimes = new HashMap<>(); // Her bir kuryenin mağazaya son giriş zamanını tutmak için kullanılan map
    private final StoreDataService storeDataService;

    @Autowired
    public CourierService(StoreDataService storeDataService) {
        this.storeDataService = storeDataService;
    }

    @Override
    public String updateCourierLocation(String courierId, double lat, double lng, LocalDateTime time) {
        StringBuilder message = new StringBuilder();


        // Kuryenin son bilinen konumunu alır.
        Courier lastCourierLocation = couriersLastLocation.get(courierId);
        if (lastCourierLocation != null) {
            double lastDistance = calculateDistance(lastCourierLocation.getLat(), lastCourierLocation.getLng(), lat, lng);
            totalDistances.merge(courierId, lastDistance, Double::sum); // Kuryenin toplam mesafesini yeni mesafe ile toplayarak günceller.
        }

        // Kuryenin son bilinen konumunu günceller.
        Courier courier = Courier.builder().courierId(courierId).lat(lat).lng(lng).time(time).build();
        couriersLastLocation.put(courierId, courier);


        message.append(logStoreEntrance(courier)); // Mağazaya giriş bilgisini logla.
        if (totalDistances.get(courierId) != null && totalDistances.get(courierId) > 0)  //Kurye hiç hareket etmediyse mesafe bilgisini loglama.
            message.append(String.format("Courier %s has traveled %.2f meters so far.", courierId, totalDistances.get(courierId)));

        log.info(message.toString());
        return message.toString();
    }

    @Override
    public Double getTotalTravelDistance(String courierId) {
        return totalDistances.getOrDefault(courierId, 0.0);
    }

    private String logStoreEntrance(Courier courier) {
        StringBuilder message = new StringBuilder();
        for (Store store : storeDataService.getStores()) {
            String entryTimeKey = courier.getCourierId() + "-" + store.getName();
            // Kuryenin mağazaya son giriş zamanını alır.
            LocalDateTime lastEntryTime = courierStoreEntryTimes.get(entryTimeKey);

            //Eğer kurye mağazanın 100 metre yarıçapı içindeyse ve kuryenin mağazaya ilk girişiyse veya son giriş zamanından 1 dakika geçmişse girişi loglar.
            if (calculateDistance(courier.getLat(), courier.getLng(), store.getLat(), store.getLng()) <= 100
                    && (lastEntryTime == null || lastEntryTime.plusMinutes(1).isBefore(courier.getTime()))) {

                message.append(String.format("Courier %s entered the 100m radius of the store %s\n",
                        courier.getCourierId(), store.getName()));

                // Kuryenin mağazaya son giriş zamanını günceller.
                courierStoreEntryTimes.put(entryTimeKey, courier.getTime());
            }
        }
        return message.toString();
    }

    //Kuryenin katettiği 2 nokta arasındaki mesafeyi Haversine formülü ile hesaplar.
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final double R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; //km->m
    }
}
