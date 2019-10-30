package app.leo.profile.service;

import app.leo.profile.models.OrganizationProfile;
import app.leo.profile.repository.OrganizationProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.leo.profile.exceptions.RoleNotExistException;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Profile;
import app.leo.profile.models.RecruiterProfile;
import app.leo.profile.repository.ApplicantProfileRepository;
import app.leo.profile.repository.RecruiterProfileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ApplicantProfileRepository applicantProfileRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private OrganizationProfileRepository organizationProfileRepository;

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

    public OrganizationProfile getOrganizationProfileByUserId(long userId){
        return organizationProfileRepository.findByUserId(userId);
    }
    public OrganizationProfile saveOrganizationProfile(OrganizationProfile organizationProfile){
        return organizationProfileRepository.save(organizationProfile);
    }

    public List<ApplicantProfile> getApplicantProfileList(long[] ids){
        List<Long> idList = Arrays.stream(ids)
                            .boxed()
                            .collect(Collectors.toList());
        return applicantProfileRepository.findByApplicantIdIn(idList);
    }

    public List<RecruiterProfile> getRecruiterProfileList(long[] ids){
        List<Long> idList = Arrays.stream(ids)
                .boxed()
                .collect(Collectors.toList());
        return recruiterProfileRepository.findAllByRecruiterIdIn(idList);
    }
}
