package com.trio.java.bikerentapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BikeDto {
    private int id;
    private String name;
    private String type;
    private int bodySize;
    private int maxLoad;
    private double rate;
    private String description;
    private double ratings;
    private List<String> imageUrls;
}
