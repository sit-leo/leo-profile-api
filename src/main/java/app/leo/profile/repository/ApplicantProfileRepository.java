package app.leo.profile.repository;

import app.leo.profile.models.ApplicantProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantProfileRepository extends JpaRepository<ApplicantProfile,Long> {

    ApplicantProfile findByApplicantId(long applicantId);

    ApplicantProfile findByUserId(long userId);

    List<ApplicantProfile> findByApplicantIdIn(List<Long> applicantIdList);
}
