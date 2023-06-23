package com.trio.java.bikerentapi.util;

import com.trio.java.bikerentapi.data.Bike;
import java.util.ArrayList;

public class ObjectsFactory {

    public static Bike createBike(int id, String name) {
        return Bike.builder()
                .withId(id)
                .withName(name)
                .withImageUrls(new ArrayList<>())
                .build();
    }
}
