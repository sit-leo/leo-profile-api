package app.leo.profile.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import app.leo.profile.dto.ApplicantProfileDTO;
import app.leo.profile.dto.OrganizationProfileDTO;
import app.leo.profile.dto.Profile;
import app.leo.profile.dto.RecruiterProfileDTO;
import app.leo.profile.models.AdminProfile;
import app.leo.profile.repository.AdminProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.leo.profile.exceptions.RoleNotExistException;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.OrganizationProfile;
import app.leo.profile.models.RecruiterProfile;
import app.leo.profile.repository.ApplicantProfileRepository;
import app.leo.profile.repository.OrganizationProfileRepository;
import app.leo.profile.repository.RecruiterProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ApplicantProfileRepository applicantProfileRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private OrganizationProfileRepository organizationProfileRepository;

    @Autowired
    private AdminProfileRepository adminProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PictureService pictureService;

    public AdminProfile saveAdminProfile(AdminProfile adminProfile){
        return adminProfileRepository.save(adminProfile);
    }

    public ApplicantProfile saveApplicantProfile(ApplicantProfile applicantProfile){
        return applicantProfileRepository.save(applicantProfile);
    }

    public RecruiterProfile saveRecruiterProfile(RecruiterProfile recruiterProfile){
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public Profile getProfile(long id, String role){
        Profile profile;
        long profileId;
        try {
            if (role.equalsIgnoreCase("recruiter")) {
                RecruiterProfile recruiterProfile =  recruiterProfileRepository.findByUserId(id);
                profileId = recruiterProfile.getRecruiterId();
                profile = modelMapper.map(recruiterProfile,RecruiterProfileDTO.class);
            } else if(role.equalsIgnoreCase("applicant")) {
                ApplicantProfile applicantProfile = applicantProfileRepository.findByUserId(id);
                profileId = applicantProfile.getApplicantId();
                profile = modelMapper.map(applicantProfile,ApplicantProfileDTO.class);
            } else if(role.equalsIgnoreCase("organizer")) {
                OrganizationProfile organizationProfile =  organizationProfileRepository.findByUserId(id);
                profileId = organizationProfile.getId();
                profile = modelMapper.map(organizationProfile,OrganizationProfileDTO.class);
            } else {
                throw new RoleNotExistException("this role does not have a profile.");
            }
        }catch (NullPointerException ex){
            System.out.println("No profile");
            return null;
        }
        profile.setImageURL(pictureService.getPicture(profileId));
        return profile;
    }

    public RecruiterProfile getRecruiterProfileByRecruiterId(long recruiterId) {
        return recruiterProfileRepository.findByRecruiterId(recruiterId);
    }

    public ApplicantProfile getApplicantProfileByApplicantId(long applicantId) {
        return applicantProfileRepository.findByApplicantId(applicantId);
    }

    public RecruiterProfile getRecruiterProfileByUserId(long userId) {
        return recruiterProfileRepository.findByUserId(userId);
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

    public List<ApplicantProfile> getApplicantProfileListExcludeWhoInList(List<Long> profileIdList) {
        return applicantProfileRepository.findAllByApplicantIdNotIn(profileIdList);
    }

    public List<RecruiterProfile> getRecruiterProfileListExcludeWhoInOrganization(List<Long> idList) {
        return recruiterProfileRepository.findAllByRecruiterIdNotIn(idList);
    }

    public List<ApplicantProfile> getApplicantProfileList() {
        return applicantProfileRepository.findAll();
    }

    public List<RecruiterProfile> getRecruiterProfileList() {
        return recruiterProfileRepository.findAll();
    }

    public List<OrganizationProfile> getOrganizationProfileInIdList(List<Long> orgIdList){
        return organizationProfileRepository.findAllByIdIn(orgIdList);
    }

    public AdminProfile getAdminProfileById(long id){
        return adminProfileRepository.findById(id).get();
    }
}
