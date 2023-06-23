package com.trio.java.bikerentapi.data;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    private int id;
    private String name;
    private String type;
    private int bodySize;
    private int maxLoad;
    private double rate;
    private String description;
    private double ratings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    private List<BikeImage> imageUrls;

}
