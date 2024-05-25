package ma.enset.resrvationsystem.repository;

import ma.enset.resrvationsystem.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.session.id = :sessionId AND r.status = 'confirmed'")
    long countReservationsBySessionId(Long sessionId);
}
