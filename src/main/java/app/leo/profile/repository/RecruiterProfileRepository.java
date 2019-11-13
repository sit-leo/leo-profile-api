package app.leo.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.leo.profile.models.RecruiterProfile;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Long> {

    RecruiterProfile findByUserId(long userId);

    RecruiterProfile findByRecruiterId(long recruiterId);

    List<RecruiterProfile> findAllByRecruiterIdIn(List<Long> ids);

    List<RecruiterProfile> findAllByRecruiterIdNotIn(List<Long> idList);
}
