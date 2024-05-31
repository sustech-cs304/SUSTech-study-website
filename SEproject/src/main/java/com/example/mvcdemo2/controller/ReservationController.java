package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.Reservation;
import com.example.mvcdemo2.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    // 创建一个日志器对象
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        // 打印接收到的预约对象
        logger.info("接收到的预约数据: " + reservation);
        Reservation savedReservation = reservationService.saveReservation(reservation);
        return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
    }

    // 新添加的方法
    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // 新添加的检查重复预约的方法
    @PutMapping("/{reservationId}/checkin")
    public ResponseEntity<Void> checkInReservation(@PathVariable Long reservationId) {
        boolean success = reservationService.checkInReservation(reservationId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        boolean success = reservationService.cancelReservation(reservationId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
