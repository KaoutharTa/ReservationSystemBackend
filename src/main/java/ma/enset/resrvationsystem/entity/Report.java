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
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @NotNull(message = "Session is required")
    private Session session;

    @NotNull(message = "Presence rate is required")
    private Float presenceRate;

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

    public Float getPresenceRate() {
        return presenceRate;
    }

    public void setPresenceRate(Float presenceRate) {
        this.presenceRate = presenceRate;
    }
}

