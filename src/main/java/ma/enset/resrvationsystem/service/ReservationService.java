package ma.enset.resrvationsystem.service;

import ma.enset.resrvationsystem.entity.Reservation;
import ma.enset.resrvationsystem.entity.Session;
import ma.enset.resrvationsystem.entity.User;
import ma.enset.resrvationsystem.exeption.ResourceNotFoundException;
import ma.enset.resrvationsystem.repository.ReservationRepository;
import ma.enset.resrvationsystem.repository.SessionRepository;
import ma.enset.resrvationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation createReservation(Long userId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (reservationRepository.countReservationsBySessionId(sessionId) >= session.getMaxReservations()) {
            throw new IllegalStateException("Session is fully booked");
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSession(session);
        reservation.setStatus("confirmed");
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = getReservationById(id);
        reservation.setStatus(reservationDetails.getStatus());
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getUserReservations(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public void cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.setStatus("cancelled");
        reservationRepository.save(reservation);
    }
}
