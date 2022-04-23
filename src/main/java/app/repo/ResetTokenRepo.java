package app.repo;


import app.entity.ResetToken;
import app.entity.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResetTokenRepo extends JpaRepository<ResetToken, Long> {
  List<ResetToken> findAllByUser(Userr user);

  void deleteByUser(Userr user);
}
