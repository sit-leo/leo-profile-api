package app.leo.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.leo.profile.exceptions.RoleNotExistException;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Profile;
import app.leo.profile.models.RecruiterProfile;
import app.leo.profile.repository.ApplicantProfileRepository;
import app.leo.profile.repository.RecruiterProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ApplicantProfileRepository applicantProfileRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    public ApplicantProfile saveApplicantProfile(ApplicantProfile applicantProfile){
        return applicantProfileRepository.save(applicantProfile);
    }

    public RecruiterProfile saveRecruiterProfile(RecruiterProfile recruiterProfile){
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public Profile getProfile(long id,String role){
        try {
            if (role.equalsIgnoreCase("recruiter")) {
                return recruiterProfileRepository.findByRecruiterId(id);
            } else if(role.equalsIgnoreCase("applicant")) {
                return applicantProfileRepository.findByApplicantId(id);
            }else{
                throw new RoleNotExistException("this role does not have a profile.");
            }
        }catch (NullPointerException ex){
            System.out.println("No profile");
            return null;
        }
    }

    public RecruiterProfile getRecruiterProfileByUserId(long userId) {
        return recruiterProfileRepository.findByRecruiterId(userId);
    }

    public ApplicantProfile getApplicantProfileByApplicantId(long userId) {
        return applicantProfileRepository.findByApplicantId(userId);
    }

    public ApplicantProfile getApplicantProfileByUserId(long userId){
        return applicantProfileRepository.findByUserId(userId);
    }
}
