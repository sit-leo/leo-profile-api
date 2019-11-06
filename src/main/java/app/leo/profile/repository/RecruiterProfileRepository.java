package app.leo.profile.repository;

import app.leo.profile.models.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Long> {

    RecruiterProfile findByRecruiterId(long recruiterId);

    List<RecruiterProfile> findAllByRecruiterIdIn(List<Long> ids);

    List<RecruiterProfile> findAllByRecruiterIdNotIn(List<Long> idList);
}
