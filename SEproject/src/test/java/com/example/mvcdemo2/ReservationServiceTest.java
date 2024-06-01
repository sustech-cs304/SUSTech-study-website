package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Reservation;
import com.example.mvcdemo2.repository.ReservationRepository;
import com.example.mvcdemo2.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation savedReservation = reservationService.saveReservation(reservation);
        assertEquals(reservation, savedReservation);
    }

    @Test
    public void testFindAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationService.findAllReservations();
        assertEquals(1, result.size());
    }

    @Test
    public void testCheckInReservation() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        boolean checkedIn = reservationService.checkInReservation(reservationId);
        assertTrue(checkedIn);
        assertEquals("已签到", reservation.getStatus());
    }

    @Test
    public void testCheckInReservation_NotFound() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
        boolean checkedIn = reservationService.checkInReservation(reservationId);
        assertFalse(checkedIn);
    }

    @Test
    public void testCancelReservation() {
        Long reservationId = 1L;
        doNothing().when(reservationRepository).deleteById(reservationId);
        boolean canceled = reservationService.cancelReservation(reservationId);
        assertTrue(canceled);
    }

    @Test
    public void testCancelReservation_Exception() {
        Long reservationId = 1L;
        doThrow(new RuntimeException()).when(reservationRepository).deleteById(reservationId);
        boolean canceled = reservationService.cancelReservation(reservationId);
        assertFalse(canceled);
    }
}
