package com.hmsapp.controller;

import com.hmsapp.entity.RoomAvailability;
import com.hmsapp.repository.RoomAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {

    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;

    @GetMapping("/search/rooms")
    public ResponseEntity<?> searchRooms(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam String roomType,
            @RequestParam long propertyId
    ){
        List<RoomAvailability> rooms = roomAvailabilityRepository.findRoomsAvailability(fromDate, toDate, roomType);

        for(RoomAvailability r:rooms){
            if(r.getTotalRooms()==0){
                return new ResponseEntity<>("No rooms available", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
