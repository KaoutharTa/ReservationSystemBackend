package ma.enset.resrvationsystem.service;

import ma.enset.resrvationsystem.entity.Report;
import ma.enset.resrvationsystem.entity.Reservation;
import ma.enset.resrvationsystem.entity.User;
import ma.enset.resrvationsystem.repository.ReportRepository;
import ma.enset.resrvationsystem.repository.ReservationRepository;
import ma.enset.resrvationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Report> generatePresenceReports() {
        List<Report> reports = new ArrayList<>();
        List<User> users = reservationRepository.findAllUsersWithReservations();

        for (User user : users) {
            List<Reservation> userReservations = reservationRepository.findByUserId(user.getId());
            long confirmedCount = userReservations.stream()
                    .filter(reservation -> "confirmed".equals(reservation.getStatus()))
                    .count();
            float presenceRate = (float) confirmedCount / userReservations.size() * 100;

            Report report = new Report();
            report.setUser(user);
            report.setPresenceRate(presenceRate);
            reports.add(report);
        }

        return reportRepository.saveAll(reports);
    }

    public List<User> getUsersWithHighReservationRate() {
        return reservationRepository.findAllUsersWithReservationRateAbove(70);
    }
}

