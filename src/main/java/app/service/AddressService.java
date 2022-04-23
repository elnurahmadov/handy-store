package app.service;

import app.entity.Address;
import app.repo.AddressRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepo addressRepo;

    public List<Address> findAll(){
        return addressRepo.findAll();
    }

}
