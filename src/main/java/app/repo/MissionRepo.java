package app.repo;


import app.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepo extends JpaRepository<Mission, Long> {
}
