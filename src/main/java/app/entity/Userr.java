package app.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
public class Userr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String city;
    private String phone;
    private String image;
    private LocalDateTime reg_date;
    private boolean status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Post> posts;

    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
    private Set<Message> from_messages;

    @OneToMany(mappedBy = "to", fetch = FetchType.LAZY)
    private Set<Message> to_messages;

    @OneToMany(mappedBy = "who")
    private Set<Blocked> who_block;

    @OneToMany(mappedBy = "whom")
    private Set<Blocked> whom_block;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<ResetToken> resetTokens;


    public Userr(String email, String password, LocalDateTime reg_date, boolean status){
        this.email=email;
        this.password=password;
        this.reg_date = reg_date;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Userr userr = (Userr) o;
        return id == userr.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Userr{id=%d}", id);
    }
}
