package ma.enset.resrvationsystem.repository;

import ma.enset.resrvationsystem.entity.Reservation;
import ma.enset.resrvationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.session.id = :sessionId AND r.status = 'confirmed'")
    long countReservationsBySessionId(Long sessionId);

    List<Reservation> findByUserId(Long userId);

    @Query("SELECT DISTINCT r.user FROM Reservation r")
    List<User> findAllUsersWithReservations();

    @Query("SELECT r.user FROM Reservation r GROUP BY r.user HAVING (COUNT(r) FILTER (WHERE r.status = 'confirmed') / COUNT(r)) * 100 > 70")
    List<User> findAllUsersWithReservationRateAbove(int percentage);
}
