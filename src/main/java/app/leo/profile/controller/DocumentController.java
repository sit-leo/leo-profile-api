package app.leo.profile.controller;

import app.leo.profile.dto.User;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Document;
import app.leo.profile.service.DocumentManagementService;
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

    @Autowired
    private DocumentManagementService documentManagementService;

    @PostMapping("/profile/documents")
    public ResponseEntity<List<Document>> uploadMultipleFiles(@ModelAttribute List<MultipartFile> files,
                                                              @RequestAttribute("user") User user){
        ApplicantProfile applicantProfile = profileService.getApplicantProfileByUserId(user.getId());
        return new ResponseEntity<>(documentService.uploadDocumentsToS3(files,applicantProfile), HttpStatus.OK);
    }

    @GetMapping("/profile/files")
    public ResponseEntity<List<S3ObjectInputStream>> getFromS3File(@RequestAttribute("user") User user){
        ApplicantProfile applicantProfile = profileService.getApplicantProfileByUserId(user.getId());
        return new ResponseEntity<>(documentService.getDocumentsByApplicantProfileId(applicantProfile.getApplicantId()),HttpStatus.OK);
    }

    @GetMapping("/profile/documents")
    public ResponseEntity<List<Document>> getDocumentByUserId(@RequestAttribute("user") User user){
        ApplicantProfile applicantProfile = profileService.getApplicantProfileByUserId(user.getId());
        return new ResponseEntity<>(documentService.getDocumentNameListByApplicantProfileId(applicantProfile),HttpStatus.OK);
    }

    @GetMapping("/profile/documents/get-in-list")
    public ResponseEntity<List<Document>> getDocumentFromDocumentList(@RequestParam("idlist") List<Long> documentIdList){
        List<Document> documents =documentService.getDocumentByIdList(documentIdList);
        return new ResponseEntity<>(documents,HttpStatus.OK);
    }

    @GetMapping("documents/{fileId}")
    public ResponseEntity<S3ObjectInputStream> getDocumentByFileId(@PathVariable long fileId){
        Document document = documentService.getDocumentById(fileId);
        return new ResponseEntity<>(documentManagementService.getObjectInputStream(document.getGenaratedFileName()),HttpStatus.OK);
    }
}