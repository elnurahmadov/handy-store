package app.repo;


import app.entity.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepo extends JpaRepository<AboutUs, Long> {
}
