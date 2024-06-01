package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.ReservationController;
import com.example.mvcdemo2.model.Reservation;
import com.example.mvcdemo2.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReservation() throws Exception {
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

        when(reservationService.saveReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\": \"2024-06-01\", \"time_slot\": \"10:00-11:00\", \"location\": \"Room 101\", \"participants\": 5, \"firstName\": \"John Doe\", \"firstStudentId\": \"S1234567\", \"roomType\": \"Conference\", \"status\": \"未签到\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2024-06-01"))
                .andExpect(jsonPath("$.time_slot").value("10:00-11:00"))
                .andExpect(jsonPath("$.location").value("Room 101"))
                .andExpect(jsonPath("$.participants").value(5))
                .andExpect(jsonPath("$.firstName").value("John Doe"))
                .andExpect(jsonPath("$.firstStudentId").value("S1234567"))
                .andExpect(jsonPath("$.roomType").value("Conference"))
                .andExpect(jsonPath("$.status").value("未签到"));
    }

    @Test
    public void testGetAllReservations() throws Exception {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setDate("2024-06-01");
        reservation1.setTime_slot("10:00-11:00");
        reservation1.setLocation("Room 101");
        reservation1.setParticipants(5);
        reservation1.setFirstName("John Doe");
        reservation1.setFirstStudentId("S1234567");
        reservation1.setRoomType("Conference");
        reservation1.setStatus("未签到");

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setDate("2024-06-02");
        reservation2.setTime_slot("11:00-12:00");
        reservation2.setLocation("Room 102");
        reservation2.setParticipants(3);
        reservation2.setFirstName("Jane Smith");
        reservation2.setFirstStudentId("S7654321");
        reservation2.setRoomType("Meeting");
        reservation2.setStatus("已签到");

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(reservationService.findAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/reservations/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].date").value("2024-06-01"))
                .andExpect(jsonPath("$[0].time_slot").value("10:00-11:00"))
                .andExpect(jsonPath("$[0].location").value("Room 101"))
                .andExpect(jsonPath("$[0].participants").value(5))
                .andExpect(jsonPath("$[0].firstName").value("John Doe"))
                .andExpect(jsonPath("$[0].firstStudentId").value("S1234567"))
                .andExpect(jsonPath("$[0].roomType").value("Conference"))
                .andExpect(jsonPath("$[0].status").value("未签到"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].date").value("2024-06-02"))
                .andExpect(jsonPath("$[1].time_slot").value("11:00-12:00"))
                .andExpect(jsonPath("$[1].location").value("Room 102"))
                .andExpect(jsonPath("$[1].participants").value(3))
                .andExpect(jsonPath("$[1].firstName").value("Jane Smith"))
                .andExpect(jsonPath("$[1].firstStudentId").value("S7654321"))
                .andExpect(jsonPath("$[1].roomType").value("Meeting"))
                .andExpect(jsonPath("$[1].status").value("已签到"));
    }

    @Test
    public void testGetAllReservations_NoContent() throws Exception {
        when(reservationService.findAllReservations()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/reservations/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCheckInReservation() throws Exception {
        when(reservationService.checkInReservation(anyLong())).thenReturn(true);

        mockMvc.perform(put("/reservations/1/checkin"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckInReservation_Failure() throws Exception {
        when(reservationService.checkInReservation(anyLong())).thenReturn(false);

        mockMvc.perform(put("/reservations/1/checkin"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testCancelReservation() throws Exception {
        when(reservationService.cancelReservation(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelReservation_Failure() throws Exception {
        when(reservationService.cancelReservation(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isInternalServerError());
    }
}
