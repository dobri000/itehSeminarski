package com.example.travelplanner.service.impl;

import com.example.travelplanner.domain.Reservation;
import com.example.travelplanner.domain.TravelPlan;
import com.example.travelplanner.dto.ReservationDto;
import com.example.travelplanner.persistence.ReservationRepository;
import com.example.travelplanner.persistence.TravelPlanRepository;
import com.example.travelplanner.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TravelPlanRepository travelPlanRepository;

    @Transactional
    @Override
    public Reservation createReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);

        TravelPlan travelPlan = savedReservation.getTravelPlan();
        travelPlan.setNumberOfSeats(travelPlan.getNumberOfSeats() - 1);
        travelPlanRepository.save(travelPlan);

        return savedReservation;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<ReservationDto> reservations = new ArrayList<>();
        for (Reservation reservation : reservationRepository.findAll()) {
            ReservationDto dto = ReservationDto.builder()
                    .reservationID(reservation.getReservationID())
                    .dateOfReservation(reservation.getDateOfReservation())
                    .totalCost(reservation.getTotalCost())
                    .build();
            reservations.add(dto);
        }
        return reservations;
    }

    @Override
    public Optional<Reservation> deleteReservation(Long reservationID) {
        Optional<Reservation> reservationToDelete = reservationRepository.findById(reservationID);
        reservationToDelete.ifPresent(reservationRepository::delete);
        return reservationToDelete;
    }
}

