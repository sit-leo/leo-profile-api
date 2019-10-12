package app.leo.profile.repository;

import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Long> {

    @Query(value = "select * from documents d where d.applicant_profile_id= ?1 ", nativeQuery = true)
    List<Document> findByApplicantProfileId(long applicantProfileId);
}
