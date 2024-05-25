package ma.enset.resrvationsystem;

import ma.enset.resrvationsystem.entity.*;
import ma.enset.resrvationsystem.repository.*;
import ma.enset.resrvationsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class ResrvationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResrvationSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        SessionRepository sessionRepository,
                                        ReservationRepository reservationRepository,
                                        ReportRepository reportRepository,
                                        UserService userService,
                                        SessionService sessionService,
                                        ReservationService reservationService,
                                        ReportService reportService,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            // Create Users
            User adminn = new User();
            adminn.setUsername("adminn");
            adminn.setPassword(passwordEncoder.encode("password"));
            adminn.setRole("ADMIN");
            userRepository.save(adminn);

            User user3 = new User();
            user3.setUsername("user3");
            user3.setPassword(passwordEncoder.encode("password"));
            user3.setRole("USER");
            userRepository.save(user3);

            User user4 = new User();
            user4.setUsername("user4");
            user4.setPassword(passwordEncoder.encode("password"));
            user4.setRole("USER");
            userRepository.save(user4);

            // Create Sessions
            Session session3 = new Session();
            session3.setDate(LocalDate.of(2024, 6, 1));
            session3.setStartTime(LocalTime.of(8, 0));
            session3.setEndTime(LocalTime.of(10, 0));
            session3.setJuryMembers("Jury Member 5, Jury Member 6");
            session3.setMaxReservations(10);
            sessionRepository.save(session3);

            Session session4 = new Session();
            session4.setDate(LocalDate.of(2024, 6, 2));
            session4.setStartTime(LocalTime.of(10, 0));
            session4.setEndTime(LocalTime.of(12, 0));
            session4.setJuryMembers("Jury Member 7, Jury Member 8");
            session4.setMaxReservations(18);
            sessionRepository.save(session4);

            // Create Reservations
            Reservation reservation3 = new Reservation();
            reservation3.setUser(user3);
            reservation3.setSession(session3);
            reservation3.setStatus("confirmed");
            reservationRepository.save(reservation3);

            Reservation reservation4 = new Reservation();
            reservation4.setUser(user4);
            reservation4.setSession(session4);
            reservation4.setStatus("confirmed");
            reservationRepository.save(reservation4);

            // Generate Reports
            List<Report> reports = reportService.generatePresenceReports();
            reportRepository.saveAll(reports);

            // Print data to console to verify
            System.out.println("Users:");
            userRepository.findAll().forEach(System.out::println);

            System.out.println("Sessions:");
            sessionRepository.findAll().forEach(System.out::println);

            System.out.println("Reservations:");
            reservationRepository.findAll().forEach(System.out::println);

            System.out.println("Reports:");
            reportRepository.findAll().forEach(System.out::println);
        };
    }
}
