package app.leo.profile.service;

import app.leo.profile.models.Picture;
import app.leo.profile.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private DocumentManagementService documentManagementService;

    public Picture getPictureByUserId(long userId){
        return pictureRepository.findByUserId(userId);
    }

    public Picture savePicture(Picture picture){
        return pictureRepository.save(picture);
    }

    public Picture uploadPictureToS3(MultipartFile file,String folderName,long userId){
        Map<String,String> nameMap = documentManagementService.uploadPicture(file,folderName);
        Picture picture = getPictureByUserId(userId);
        if(picture == null) {
            picture=new Picture();
        }
        for(Map.Entry<String,String> name: nameMap.entrySet()){
            picture.setName(name.getKey());
            picture.setGenaratedName(name.getValue());
            picture.setUserId(userId);
        }
        return pictureRepository.save(picture);
    }

    public String getPicture(long userId){
        Picture picture = pictureRepository.findByUserId(userId);
        return documentManagementService.getLinkOfProfilePicture(picture.getGenaratedName());
    }
}
