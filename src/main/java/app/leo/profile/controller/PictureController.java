package app.leo.profile.controller;

import app.leo.profile.dto.PictureDTO;
import app.leo.profile.dto.User;
import app.leo.profile.exceptions.RoleNotExistException;
import app.leo.profile.models.Picture;
import app.leo.profile.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ModelMapper modelMapper;

    private final String APPLICANT_ROLE= "applicant";
    private final String RECRUITER_ROLE = "recruiter";
    private final String ORGANIZER_ROLE = "organizer";

    @PostMapping("profile/upload")
    public ResponseEntity<PictureDTO> uploadPicture(@ModelAttribute MultipartFile file,
                                                    @RequestAttribute("user") User user){
        String folderName = "statics/profile/";
        String role = user.getRole();
        switch (role) {
            case APPLICANT_ROLE:
                folderName += "applicant/";
                break;
            case RECRUITER_ROLE:
                folderName += "recruiter/";
                break;
            case ORGANIZER_ROLE:
                folderName += "organizer/";
                break;
            default:
                throw new RoleNotExistException("Your role are not in the system");
        }
        Picture picture = pictureService.uploadPictureToS3(file,folderName,user.getProfileId());
        return new ResponseEntity<>(modelMapper.map(picture,PictureDTO.class), HttpStatus.ACCEPTED);
    }

    @GetMapping("profile/picture")
    public ResponseEntity<String> getProfilePicture(@RequestAttribute("user") User user){
        return new ResponseEntity<>(pictureService.getPicture( user.getProfileId()) ,HttpStatus.OK);
    }
}
