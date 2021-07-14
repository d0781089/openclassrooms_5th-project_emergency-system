package com.safetynet.emergencysystem.dao;

import com.safetynet.emergencysystem.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> getByBirthDateGreaterThan(Date minimumBirthDate);
    List<MedicalRecord> getByBirthDateLessThanEqual(Date minimumBirthDate);
    List<MedicalRecord> getByFirstNameInAndLastNameIn(List<String> firstName, List<String> lastName);
}
