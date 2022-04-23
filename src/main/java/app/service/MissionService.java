package app.service;

import app.entity.Mission;
import app.repo.MissionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MissionService {

    private final MissionRepo missionRepo;

    public List<Mission> findAll(){
        return missionRepo.findAll();
    }
}
