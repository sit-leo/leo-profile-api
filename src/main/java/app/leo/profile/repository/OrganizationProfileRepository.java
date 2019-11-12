package app.leo.profile.repository;

import app.leo.profile.models.OrganizationProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationProfileRepository extends JpaRepository<OrganizationProfile,Long> {

    OrganizationProfile findByUserId(long userId);

    List<OrganizationProfile> findAllByIdIn(List<Long> ids);
}
