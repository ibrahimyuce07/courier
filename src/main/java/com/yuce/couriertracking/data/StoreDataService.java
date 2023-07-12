package com.yuce.couriertracking.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuce.couriertracking.model.Store;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class StoreDataService {
    @Getter
    private final List<Store> stores;

    public StoreDataService() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream inputStream = new ClassPathResource("stores.json").getInputStream();
            this.stores = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Store data could not be read", e);
        }
    }

}
