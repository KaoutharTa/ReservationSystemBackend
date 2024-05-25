package ma.enset.resrvationsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Session is required")
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    private float presenceRate;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public float getPresenceRate() {
        return presenceRate;
    }

    public void setPresenceRate(float presenceRate) {
        this.presenceRate = presenceRate;
    }
}
