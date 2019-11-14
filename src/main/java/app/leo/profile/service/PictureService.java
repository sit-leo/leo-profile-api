package app.leo.profile.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.leo.profile.models.Picture;
import app.leo.profile.repository.PictureRepository;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private DocumentManagementService documentManagementService;

    public Picture getPictureByProfileId(long profileId){
        return pictureRepository.findByProfileId(profileId);
    }

    public Picture savePicture(Picture picture){
        return pictureRepository.save(picture);
    }

    public Picture uploadPictureToS3(MultipartFile file,String folderName,long profileId){
        Map<String,String> nameMap = documentManagementService.uploadPicture(file,folderName);
        Picture picture = getPictureByProfileId(profileId);
        if(picture == null) {
            picture=new Picture();
        }
        for(Map.Entry<String,String> name: nameMap.entrySet()){
            picture.setName(name.getKey());
            picture.setGenaratedName(name.getValue());
            String url = documentManagementService.getLinkOfProfilePicture(picture.getGenaratedName());
            picture.setImageURL(url);
            picture.setProfileId(profileId);
        }
        return pictureRepository.save(picture);
    }

    public String getPicture(long profileId){
        Picture picture = pictureRepository.findByProfileId(profileId);
        if (picture == null) {
            return null;
        }
        return documentManagementService.getLinkOfProfilePicture(picture.getGenaratedName());
    }
}
