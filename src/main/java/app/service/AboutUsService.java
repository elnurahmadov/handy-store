package app.service;

import app.entity.AboutUs;
import app.repo.AboutUsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AboutUsService {

  private final AboutUsRepo aboutUsRepo;

  public List<AboutUs> findAll() {
    return aboutUsRepo.findAll();
  }

}
