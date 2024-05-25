package ma.enset.resrvationsystem.service;

import ma.enset.resrvationsystem.entity.Session;
import ma.enset.resrvationsystem.exeption.ResourceNotFoundException;
import ma.enset.resrvationsystem.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session updateSession(Long id, Session sessionDetails) {
        Session session = getSessionById(id);
        session.setDate(sessionDetails.getDate());
        session.setStartTime(sessionDetails.getStartTime());
        session.setEndTime(sessionDetails.getEndTime());
        session.setJuryMembers(sessionDetails.getJuryMembers());
        session.setMaxReservations(sessionDetails.getMaxReservations());
        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public List<Session> getAvailableSessions() {
        return sessionRepository.findAll().stream()
                .filter(session -> sessionRepository.countReservationsBySessionId(session.getId()) < session.getMaxReservations())
                .collect(Collectors.toList());
    }
}
