package app.leo.profile.dto;

public class IdWithNumberOfApplicantAndRecruiter {

    private long id;
    private int numOfApplicants;
    private int numOfRecruiters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumOfApplicants() {
        return numOfApplicants;
    }

    public void setNumOfApplicants(int numOfApplicants) {
        this.numOfApplicants = numOfApplicants;
    }

    public int getNumOfRecruiters() {
        return numOfRecruiters;
    }

    public void setNumOfRecruiters(int numOfRecruiters) {
        this.numOfRecruiters = numOfRecruiters;
    }
}
