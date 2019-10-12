package app.leo.profile.controller;

import app.leo.profile.dto.User;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Document;
import app.leo.profile.service.DocumentService;
import app.leo.profile.service.ProfileService;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ProfileService profileService;


    @PostMapping("/profile/documents")
    public ResponseEntity<List<Document>> uploadMultipleFiles(@ModelAttribute List<MultipartFile> files,
                                                              @RequestAttribute("user") User user,
                                                              @RequestAttribute("token") String token){
        ApplicantProfile applicantProfile = profileService.getApplicantProfileByUserId(user.getId());
        return new ResponseEntity<>(documentService.uploadDocumentsToS3(files,applicantProfile,1), HttpStatus.OK);
    }

    @GetMapping("/profile/documents")
    public ResponseEntity<List<S3ObjectInputStream>> getFromS3File(@RequestAttribute("user") User user){
        ApplicantProfile applicantProfile = profileService.getApplicantProfileByUserId(user.getId());
        return new ResponseEntity<>(documentService.getDocumentsByApplicantProfileId(applicantProfile.getApplicantId()),HttpStatus.OK);
    }

    @GetMapping("/profile/documents/name-list")
    public ResponseEntity<List<Document>> getDocumentByUserId(@RequestAttribute("user") User user){
        ApplicantProfile applicantProfile = profileService.getApplicantProfileByUserId(user.getId());
        return new ResponseEntity<>(documentService.getDocumentNameListByApplicantProfileId(applicantProfile),HttpStatus.OK);
    }
}