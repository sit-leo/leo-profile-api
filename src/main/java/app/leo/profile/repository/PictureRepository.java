package app.leo.profile.repository;

import app.leo.profile.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {

    Picture findByProfileId(long profileId);
}
