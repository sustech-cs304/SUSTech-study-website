package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
