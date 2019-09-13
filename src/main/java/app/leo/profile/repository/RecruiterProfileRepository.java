package app.leo.profile.repository;

import app.leo.profile.models.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Long> {

    RecruiterProfile findByRecruiterId(long recruiterId);
}
