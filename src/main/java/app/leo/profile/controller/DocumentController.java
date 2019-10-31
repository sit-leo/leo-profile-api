package app.leo.profile.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import app.leo.profile.dto.User;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Document;
import app.leo.profile.service.DocumentManagementService;
import app.leo.profile.service.DocumentService;
import app.leo.profile.service.ProfileService;

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
    public ResponseEntity<String> getDocumentByFileId(@PathVariable long fileId, HttpServletResponse response) throws IOException {
        Document document = documentService.getDocumentById(fileId);
        InputStream inputStream = documentManagementService.getInputStream(document.getGenaratedFileName());

        byte[] fileBytes = IOUtils.toByteArray(inputStream);
        String fileEncoded = Base64.encodeBase64String(fileBytes);

        return new ResponseEntity<>(fileEncoded,HttpStatus.OK);
    }
}