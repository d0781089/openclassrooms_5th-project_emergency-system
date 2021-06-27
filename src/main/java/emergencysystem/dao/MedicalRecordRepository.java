package emergencysystem.dao;

import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    int countByBirthDateLessThanEqual(int matureAge);
    int countByBirthDateGreaterThanEqual(int matureAge);
}
