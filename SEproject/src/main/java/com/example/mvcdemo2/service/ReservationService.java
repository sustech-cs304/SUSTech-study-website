package com.example.mvcdemo2.service;

import com.example.mvcdemo2.model.Reservation;
import com.example.mvcdemo2.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // 添加新方法以获取所有预约
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
}