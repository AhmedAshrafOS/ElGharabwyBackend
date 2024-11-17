package com.project.elgharabwy.service;

import com.project.elgharabwy.dao.BookingDAO;
import com.project.elgharabwy.entity.Booking;

// BookingServiceImpl.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingDAO.save(booking);
    }

    @Override
    public Booking getBookingById(String id) {
        return bookingDAO.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }

    @Override
    public Booking updateBooking(String id, Booking updatedBooking) {
        Optional<Booking> bookingOptional = bookingDAO.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setName(updatedBooking.getName());
            booking.setAge(updatedBooking.getAge());
            booking.setDate(updatedBooking.getDate());
            booking.setPhoneNumber(updatedBooking.getPhoneNumber());
            booking.setService(updatedBooking.getService());
            return bookingDAO.save(booking);
        }
        return null;
    }

    @Override
    public void deleteBooking(String id) {
        bookingDAO.deleteById(id);
    }
}
