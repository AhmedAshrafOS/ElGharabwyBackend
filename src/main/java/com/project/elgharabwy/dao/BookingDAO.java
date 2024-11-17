package com.project.elgharabwy.dao;

import com.project.elgharabwy.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDAO extends MongoRepository<Booking, String> {



}

