package app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
public class Wished {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "w_id")
  private long id;

  private long userr;
  private long post;

  public Wished(long userId, long postId) {
    this.userr=userId;
    this.post=postId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return String.format("Wished{id=%d}", id);
  }
}
