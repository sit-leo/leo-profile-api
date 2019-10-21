package app.leo.profile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue
    private long id;

    private String fileName;

    private String genaratedFileName;

    @ManyToOne
    private ApplicantProfile applicantProfile;

    public Document() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ApplicantProfile getApplicantProfile() {
        return applicantProfile;
    }

    public void setApplicantProfile(ApplicantProfile applicantProfile) {
        this.applicantProfile = applicantProfile;
    }

    public String getGenaratedFileName() {
        return genaratedFileName;
    }

    public void setGenaratedFileName(String genaratedFileName) {
        this.genaratedFileName = genaratedFileName;
    }
}
