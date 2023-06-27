package com.example.travelplanner.controller;

import com.example.travelplanner.domain.Reservation;
import com.example.travelplanner.dto.ReservationDto;
import com.example.travelplanner.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationID}")
    public ResponseEntity<String> deleteReservation(@PathVariable("reservationID") Long reservationID) {
        Optional<Reservation> reservationToDelete = reservationService.deleteReservation(reservationID);
        if (!reservationToDelete.isPresent()) {
            return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Reservation successfully deleted!", HttpStatus.OK);
    }
}

