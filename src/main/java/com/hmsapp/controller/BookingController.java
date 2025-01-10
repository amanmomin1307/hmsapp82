package com.hmsapp.controller;

import com.hmsapp.entity.Property;
import com.hmsapp.entity.RoomAvailability;
import com.hmsapp.entity.Booking;
import com.hmsapp.repository.BookingRepository;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.repository.RoomAvailabilityRepository;
import com.hmsapp.service.PDFGenerator;
import com.hmsapp.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private RoomAvailabilityRepository roomAvailabilityRepository;

    private PropertyRepository propertyRepository;

    private BookingRepository bookingRepository;

    private PDFGenerator pdfGenerator;

    private TwilioService twilioService;

    public BookingController(RoomAvailabilityRepository roomAvailabilityRepository, PropertyRepository propertyRepository, BookingRepository bookingRepository, PDFGenerator pdfGenerator, TwilioService twilioService) {
        this.roomAvailabilityRepository = roomAvailabilityRepository;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfGenerator = pdfGenerator;
        this.twilioService = twilioService;
    }

    @GetMapping("/search/rooms")
    public ResponseEntity<?> searchRoomsAndBook(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam String roomType,
            @RequestParam Long propertyId,
            @RequestBody Booking bookings
    ){
        List<RoomAvailability> rooms = roomAvailabilityRepository.findRoomsAvailability(fromDate, toDate, roomType);

        Property property = propertyRepository.findById(propertyId).get();

        for(RoomAvailability r:rooms){
            if(r.getTotalRooms()==0){
                return new ResponseEntity<>("No rooms available", HttpStatus.OK);
            }
        }

        bookings.setProperty(property);
        Booking savedBookingDetails = bookingRepository.save(bookings);

        pdfGenerator.generatePdf("D:\\GenaratedPDF\\bookings"+"_"+savedBookingDetails.getId()+".pdf",savedBookingDetails,fromDate,toDate,property);

        twilioService.sendSms("+919637742012"," bhai kese ho?");

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }


}
