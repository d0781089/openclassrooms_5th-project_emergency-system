package emergencysystem.dao;

import emergencysystem.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    List<FireStation> findBySort(String fireStation);

}