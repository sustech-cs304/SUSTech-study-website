package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Reservation;
import com.example.mvcdemo2.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testSaveAndFindById() {
        Reservation reservation = new Reservation();
        reservation.setDate("2024-06-01");
        reservation.setTime_slot("10:00-11:00");
        reservation.setLocation("Room 101");
        reservation.setParticipants(5);
        reservation.setFirstName("John Doe");
        reservation.setFirstStudentId("S1234567");
        reservation.setRoomType("Conference");
        reservation.setStatus("未签到");

        Reservation savedReservation = reservationRepository.save(reservation);
        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());

        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
        assertTrue(foundReservation.isPresent());
        assertEquals(savedReservation.getId(), foundReservation.get().getId());
    }

    @Test
    public void testDelete() {
        Reservation reservation = new Reservation();
        reservation.setDate("2024-06-01");
        reservation.setTime_slot("10:00-11:00");
        reservation.setLocation("Room 101");
        reservation.setParticipants(5);
        reservation.setFirstName("John Doe");
        reservation.setFirstStudentId("S1234567");
        reservation.setRoomType("Conference");
        reservation.setStatus("未签到");

        Reservation savedReservation = reservationRepository.save(reservation);
        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());

        reservationRepository.deleteById(savedReservation.getId());

        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
        assertFalse(foundReservation.isPresent());
    }
}
