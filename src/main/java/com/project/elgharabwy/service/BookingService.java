package com.project.elgharabwy.service;

import com.project.elgharabwy.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);

    Booking getBookingById(String id);

    List<Booking> getAllBookings();

    Booking updateBooking(String id, Booking booking);

    void deleteBooking(String id);
}
