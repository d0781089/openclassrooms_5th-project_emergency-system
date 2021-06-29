package emergencysystem.dao;

import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> getByBirthDateBeforeOrBirthDateEquals(String birthDate, String birthDate2);
    List<MedicalRecord> getByBirthDateAfter(String birthDate);

    /*List<Person> getByBirthDateBeforeOrBirthDateEquals(List<Person> persons);
    List<Person> getByBirthDateAfter(List<Person> persons);*/
}
