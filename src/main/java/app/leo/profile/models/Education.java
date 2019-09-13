package app.leo.profile.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Education {
    @Id
    private long id;
    private String university;
    private String year;
    private String major;
    private String gpax;

    @OneToOne(mappedBy = "education")
    private ApplicantProfile applicantProfile;

    public long getId() {
        return id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGpax() {
        return gpax;
    }

    public void setGpax(String gpax) {
        this.gpax = gpax;
    }

    public ApplicantProfile getApplicantProfile() {
        return applicantProfile;
    }

    public void setApplicantProfile(ApplicantProfile applicantProfile) {
        this.applicantProfile = applicantProfile;
    }
}
