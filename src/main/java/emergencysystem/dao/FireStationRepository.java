package emergencysystem.dao;

import emergencysystem.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    List<FireStation> getByStation(int station);
    FireStation getByAddress(String address);
    FireStation findByAddress(String address);
}
