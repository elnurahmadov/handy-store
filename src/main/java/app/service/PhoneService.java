package app.service;

import app.entity.Phone;
import app.repo.PhoneRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneService {

  private final PhoneRepo phoneRepo;

  public List<Phone> findAll(){
      return phoneRepo.findAll();
  }
}
