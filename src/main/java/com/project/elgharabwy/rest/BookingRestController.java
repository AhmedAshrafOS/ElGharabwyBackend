package com.project.elgharabwy.rest;


import com.project.elgharabwy.entity.Booking;
import com.project.elgharabwy.service.BookingService;
import com.project.elgharabwy.service.GoogleSheetsService;
import com.project.elgharabwy.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = {"x-client-id", "Content-Type", "Authorization"},
        methods = { RequestMethod.POST}
)
@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RateLimiterService rateLimiterService;
    @Autowired
    private  GoogleSheetsService googleSheetsService;


    private boolean isRequestAllowed(String clientId) {
        return rateLimiterService.isAllowed(clientId);
    }
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking, @RequestHeader("X-Client-Id") String clientId) {

        if (!isRequestAllowed(clientId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests. Please try again later.");
        }
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createBookingAdmin(@RequestBody Booking booking) throws IOException {
        Booking savedBooking = bookingService.createBooking(booking);
        LocalDate createdDate = LocalDate.now();
        String sheetName = googleSheetsService.getOrCreateSheetForDate(savedBooking.getDate());
        String month = createdDate.format(DateTimeFormatter.ofPattern("MMMM"));
        String age = Integer.toString(savedBooking.getAge());
        try {
            // Save the booking in the database

            // Append the booking details to Google Sheets
            googleSheetsService.appendDataToSheet(sheetName + "!A1", Arrays.asList(
                    savedBooking.getName(),
                    savedBooking.getPhoneNumber(),
                    age,
                    savedBooking.getDate(),
                    savedBooking.getService(),
                    month,
                    createdDate.toString()

            ));

            return ResponseEntity.ok(savedBooking);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to create booking and update Google Sheets.");
        }

    }


    @PostMapping("/sheets")
    public ResponseEntity<?> createSheetsBookingAdmin(@RequestBody Booking booking) {
        try {
            // Validate input
            if (booking.getName() == null || booking.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required.");
            }
            if (booking.getDate() == null) {
                return ResponseEntity.badRequest().body("Booking date is required.");
            }
            if (booking.getPhoneNumber() == null) {
                return ResponseEntity.badRequest().body("PhoneNumber  is required.");
            }
            // Prepare dynamic data
            LocalDate createdDate = LocalDate.now();
            String sheetName = googleSheetsService.getOrCreateSheetForDate(booking.getDate());
            String month = createdDate.format(DateTimeFormatter.ofPattern("MMMM"));
            String age = Integer.toString(booking.getAge());

            // Append to Google Sheets
            googleSheetsService.appendDataToSheet(sheetName + "!A1", Arrays.asList(
                    booking.getName(),
                    booking.getPhoneNumber(),
                    age,
                    booking.getDate(),
                    booking.getService(),
                    month,
                    createdDate.toString()
            ));

            // Build success response
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("sheetName", sheetName);
            response.put("appendedData", booking);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Log and handle unexpected errors
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to create and update Google Sheets.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }


}
