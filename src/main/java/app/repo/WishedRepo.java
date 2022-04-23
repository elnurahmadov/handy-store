package app.repo;


import app.entity.Wished;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishedRepo extends JpaRepository<Wished, Long> {

  List<Wished> findAllByUserr(long userId);

  Optional<Wished> findByUserrAndPost(long userId, long postId);

  //Conventional delete method does not work
  @Transactional
  @Modifying
  @Query("DELETE FROM Wished w WHERE w.post=:post AND w.userr =:user")
  void deleteByPostAndUserr(@Param("post") long post, @Param("user") long user);

}
