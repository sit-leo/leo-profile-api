package app.leo.profile.service;

import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Document;
import app.leo.profile.repository.DocumentRepository;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentManagementService documentManagementService;

    public List<S3ObjectInputStream> getDocumentsByApplicantProfileId(long applicantProfileId){
        List<Document> documents =  documentRepository.findByApplicantProfileId(applicantProfileId);
        List<S3ObjectInputStream> inputStreams = new ArrayList<>();
        for(Document document:documents){
            S3ObjectInputStream s3ObjectInputStream = documentManagementService.getObjectInputStream(document.getGenaratedFileName());
            inputStreams.add(s3ObjectInputStream);
        }

        return inputStreams;
    }

    public List<Document> uploadDocumentsToS3(List<MultipartFile> files, ApplicantProfile applicantProfile){
        Map<String,String> documentList = documentManagementService.uploadMultipleFiles(files);
        List<Document> documents = new ArrayList<>();
        for(Map.Entry<String,String> documentName: documentList.entrySet()){
            Document document  = new Document();
            document.setFileName(documentName.getKey());
            document.setGenaratedFileName(documentName.getValue());
            document.setApplicantProfile(applicantProfile);
            document = documentRepository.save(document);
            documents.add(document);
        }
        return documents;
    }

    public List<Document> getDocumentNameListByApplicantProfileId(ApplicantProfile applicantProfile){
        return documentRepository.findByApplicantProfileId(applicantProfile.getApplicantId());
    }
}
