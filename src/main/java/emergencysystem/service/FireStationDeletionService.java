package emergencysystem.service;

import emergencysystem.dao.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationDeletionService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public String deleteFireStation(Long id) {

        fireStationRepository.deleteById(id);

        return "The fire station was deleted successfully.";
    }
}
