package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Reservation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    @Test
    public void testReservationCreation() {
        Reservation reservation = new Reservation();
        assertNotNull(reservation);
    }

    @Test
    public void testGettersAndSetters() {
        Reservation reservation = new Reservation();

        reservation.setId(1L);
        assertEquals(1L, reservation.getId());

        reservation.setDate("2024-06-01");
        assertEquals("2024-06-01", reservation.getDate());

        reservation.setTime_slot("10:00-11:00");
        assertEquals("10:00-11:00", reservation.getTime_slot());

        reservation.setLocation("Room 101");
        assertEquals("Room 101", reservation.getLocation());

        reservation.setParticipants(5);
        assertEquals(5, reservation.getParticipants());

        reservation.setFirstName("John Doe");
        assertEquals("John Doe", reservation.getFirstName());

        reservation.setFirstStudentId("S1234567");
        assertEquals("S1234567", reservation.getFirstStudentId());

        reservation.setRoomType("Conference");
        assertEquals("Conference", reservation.getRoomType());

        reservation.setStatus("已签到");
        assertEquals("已签到", reservation.getStatus());
    }

    @Test
    public void testDefaultStatus() {
        Reservation reservation = new Reservation();
        assertEquals("未签到", reservation.getStatus());
    }

    @Test
    public void testToString() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setDate("2024-06-01");
        reservation.setTime_slot("10:00-11:00");
        reservation.setLocation("Room 101");
        reservation.setParticipants(5);
        reservation.setFirstName("John Doe");
        reservation.setFirstStudentId("S1234567");
        reservation.setRoomType("Conference");
        reservation.setStatus("未签到");

        String expected = "Reservation{id=1, date='2024-06-01', time_slot='10:00-11:00', location='Room 101', participants=5, firstName='John Doe', firstStudentId='S1234567', roomType='Conference', status='未签到'}";
        assertEquals(expected, reservation.toString());
    }
}
