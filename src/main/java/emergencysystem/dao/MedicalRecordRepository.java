package emergencysystem.dao;

import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> getByBirthDateGreaterThan(Date minimumBirthDateRequired);
    List<MedicalRecord> getByBirthDateLessThanEqual(Date minimumBirthDateRequired);
    List<MedicalRecord> getByFirstNameInAndLastNameIn(List<String> firstName, List<String> lastName);
}
