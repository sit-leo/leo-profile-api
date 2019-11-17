package app.leo.profile.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

@Entity
public class Education {
    @Id
    @GeneratedValue
    private long id;
    private String university;
    private String year;
    private String major;
    private String gpax;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "applicant_id")
    @JsonBackReference
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
