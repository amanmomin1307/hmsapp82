package com.hmsapp.repository;

import com.hmsapp.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    @Query("SELECT r FROM RoomAvailability r WHERE r.roomType = :roomType "+
            "AND r.date BETWEEN :fromDate AND :toDate")
    List<RoomAvailability> findRoomsAvailability(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("roomType") String roomType);


}
