package app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Blocked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    long id;

    @ManyToOne
    @JoinTable(name = "r_blocked_who",
            joinColumns = {@JoinColumn(name="blocked_id",
                    referencedColumnName = "b_id"),
            },
            inverseJoinColumns = {@JoinColumn(name = "who_id",
                    referencedColumnName = "u_id")})
    private Userr who;

    @ManyToOne
    @JoinTable(name = "r_blocked_whom",
            joinColumns = {@JoinColumn(name="blocked_id",
                    referencedColumnName = "b_id"),
            },
            inverseJoinColumns = {@JoinColumn(name = "whom_id",
                    referencedColumnName = "u_id")})
    private Userr whom;

    public Blocked(Userr loggedUser, Userr currentUser) {
        this.who = loggedUser;
        this.whom = currentUser;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Blocked{id=%d}", id);
    }
}
