package com.hmsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class RoomAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "total_rooms", nullable = false)
    private Long totalRooms;

    @Column(name = "nightly_price", nullable = false)
    private Long nightlyPrice;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "date", nullable = false)
    private LocalDate date;

}
