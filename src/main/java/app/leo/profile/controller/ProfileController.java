package app.leo.profile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.leo.profile.adapters.MatchManagementAdapter;
import app.leo.profile.dto.ApplicantProfileDTO;
import app.leo.profile.dto.GetOrganizationsOfUserResponse;
import app.leo.profile.dto.IdWithNumberOfApplicantAndRecruiter;
import app.leo.profile.dto.IdWrapper;
import app.leo.profile.dto.OrganizationProfileDTO;
import app.leo.profile.dto.Profile;
import app.leo.profile.dto.RecruiterProfileDTO;
import app.leo.profile.dto.User;
import app.leo.profile.exceptions.RoleNotExistException;
import app.leo.profile.exceptions.UserNotExistException;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.OrganizationProfile;
import app.leo.profile.models.RecruiterProfile;
import app.leo.profile.service.ProfileService;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MatchManagementAdapter matchManagementAdapter;

    private final String APPLICANT_ROLE = "applicant";
    private final String RECRUITER_ROLE = "recruiter";
    private final String ORGANIZER_ROLE = "organizer";

    @PostMapping("/profile/applicant/create")
    public ResponseEntity<ApplicantProfile> saveApplicantProfile(@Valid @RequestBody ApplicantProfileDTO applicantProfileDTO) {
        ApplicantProfile applicantProfile = modelMapper.map(applicantProfileDTO, ApplicantProfile.class);
        return new ResponseEntity<>(profileService.saveApplicantProfile(applicantProfile), HttpStatus.ACCEPTED);
    }

    @PostMapping("/profile/recruiter/create")
    public ResponseEntity<RecruiterProfile> saveRecruiterProfile(@Valid @RequestBody RecruiterProfileDTO recruiterProfileDTO) {
        RecruiterProfile recruiterProfile = modelMapper.map(recruiterProfileDTO, RecruiterProfile.class);
        return new ResponseEntity<>(profileService.saveRecruiterProfile(recruiterProfile), HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile/applicant")
    public ResponseEntity<ApplicantProfile> updateApplicantProfile(@Valid @RequestBody ApplicantProfile applicantProfileDTO) {
        if (applicantProfileDTO.getApplicantId() == 0) {
            throw new UserNotExistException("This profile does not exist");
        }
        ApplicantProfile applicantProfile = modelMapper.map(applicantProfileDTO, ApplicantProfile.class);
        return new ResponseEntity<>(profileService.saveApplicantProfile(applicantProfile), HttpStatus.OK);
    }

    @PutMapping("/profile/recruiter")
    public ResponseEntity<RecruiterProfile> updateRecruiterProfile(@Valid @RequestBody RecruiterProfile recruiterProfileDTO) {
        if (recruiterProfileDTO.getRecruiterId() == 0) {
            throw new UserNotExistException("This profile does not exist");
        }
        RecruiterProfile recruiterProfile = modelMapper.map(recruiterProfileDTO, RecruiterProfile.class);
        return new ResponseEntity<>(profileService.saveRecruiterProfile(recruiterProfile), HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile/organizer")
    public ResponseEntity<OrganizationProfile> updateOrganizerProfile(@Valid @RequestBody OrganizationProfile organizationProfile) {
        if (organizationProfile.getId() == 0) {
            throw new UserNotExistException("This profile does not exist");
        }
        return new ResponseEntity<>(profileService.saveOrganizationProfile(organizationProfile), HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity<Profile> getProfile(@RequestAttribute(name = "user") User user) {
        return new ResponseEntity<>(profileService.getProfile(user.getId(), user.getRole()), HttpStatus.OK);
    }

    @GetMapping("/profile/{recruiterId}/recruiter")
    public ResponseEntity<RecruiterProfile> getRecruiterProfile(@PathVariable long recruiterId) {
        return new ResponseEntity<>(profileService.getRecruiterProfileByRecruiterId(recruiterId), HttpStatus.OK);
    }

    @GetMapping("/profile/{applicantId}/applicant")
    public ResponseEntity<ApplicantProfile> getApplicantProfile(@PathVariable long applicantId) {
        return new ResponseEntity<>(profileService.getApplicantProfileByApplicantId(applicantId), HttpStatus.OK);
    }

    @PostMapping("/profile/organizer/create")
    public ResponseEntity<OrganizationProfileDTO> createOrganizationProfile(@RequestBody OrganizationProfileDTO organizationProfileDTO) {
        OrganizationProfile organizationProfile = modelMapper.map(organizationProfileDTO, OrganizationProfile.class);
        return new ResponseEntity<>(modelMapper.map(profileService.saveOrganizationProfile(organizationProfile), OrganizationProfileDTO.class), HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile/id/{userId}/{role}")
    public ResponseEntity<Long> getProfileIdByUserId(@PathVariable String role, @PathVariable long userId) {
        long result;
        switch (role) {
            case APPLICANT_ROLE:
                result = profileService.getApplicantProfileByUserId(userId).getApplicantId();
                break;
            case RECRUITER_ROLE:
                result = profileService.getRecruiterProfileByUserId(userId).getRecruiterId();
                break;
            case ORGANIZER_ROLE:
                result = profileService.getOrganizationProfileByUserId(userId).getId();
                break;
            default:
                throw new RoleNotExistException("Your role isn't existed");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("profile/applicants/{ids}")
    public ResponseEntity<List<ApplicantProfile>> getApplicantListByIds(@PathVariable("ids") long[] ids) {
        return new ResponseEntity<>(profileService.getApplicantProfileList(ids), HttpStatus.OK);
    }

    @GetMapping("profile/recruiters/{ids}")
    public ResponseEntity<List<RecruiterProfile>> getRecruiterListByIds(@PathVariable("ids") long[] ids) {
        return new ResponseEntity<>(profileService.getRecruiterProfileList(ids), HttpStatus.OK);
    }

    @GetMapping("profile/applicants")
    public ResponseEntity<List<ApplicantProfileDTO>> getApplicantWhoAreNotInOrg(@RequestAttribute("user") User user, @RequestAttribute("token") String token) {
        List<ApplicantProfileDTO> response = new ArrayList<>();
        IdWrapper idWrapper = matchManagementAdapter.getApplicantInOrgByOrgProfileId(user.getProfileId(), token);
        List<ApplicantProfile> applicantProfileList = null;

        if (!isEmpty(idWrapper)) {
            applicantProfileList = profileService.getApplicantProfileListExcludeWhoInList(idWrapper.getIdList());
        } else {
            applicantProfileList = profileService.getApplicantProfileList();
        }

        for (ApplicantProfile applicantProfile : applicantProfileList) {
            response.add(modelMapper.map(applicantProfile, ApplicantProfileDTO.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("profile/recruiters")
    public ResponseEntity<List<RecruiterProfileDTO>> getRecruiterWhoAreNotInOrg(@RequestAttribute("user") User user, @RequestAttribute("token") String token){
        List<RecruiterProfileDTO> response = new ArrayList<>();
        IdWrapper idWrapper = matchManagementAdapter.getRecruiterInOrgByOrgProfileId(user.getProfileId(),token);
        List<RecruiterProfile> recruiterProfileList = null;

        if (!isEmpty(idWrapper)) {
            recruiterProfileList = profileService.getRecruiterProfileListExcludeWhoInOrganization(idWrapper.getIdList());
        } else {
            recruiterProfileList = profileService.getRecruiterProfileList();
        }

        for(RecruiterProfile recruiterProfile:recruiterProfileList){
            response.add(modelMapper.map(recruiterProfile,RecruiterProfileDTO.class));
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private boolean isEmpty(IdWrapper idWrapper) {
        return idWrapper.getIdList().isEmpty();
    }

    @GetMapping("profile/organizations")
    public ResponseEntity<List<GetOrganizationsOfUserResponse>> getOrganizationsByUserId(@RequestAttribute("token") String token, @RequestAttribute("user") User user){
        List<IdWithNumberOfApplicantAndRecruiter>  organizationsOfUser =matchManagementAdapter.getOrganizationsOfUser(user.getProfileId(),token).getIdList();
        List<GetOrganizationsOfUserResponse> organizationProfileDTOList = mapOrgProfileDTO(organizationsOfUser);
        return new ResponseEntity<>(organizationProfileDTOList,HttpStatus.OK);
    }

    private List<GetOrganizationsOfUserResponse> mapOrgProfileDTO(List<IdWithNumberOfApplicantAndRecruiter> organizationIdList){
        List<GetOrganizationsOfUserResponse> result = new ArrayList<>();
        List<Long> idList =  organizationIdList.parallelStream()
                .map(IdWithNumberOfApplicantAndRecruiter::getId).collect(Collectors.toList());
        List<OrganizationProfile> organizationProfileList = profileService.getOrganizationProfileInIdList(idList);
        for(int i = 0; i<organizationIdList.size(); i++){
            GetOrganizationsOfUserResponse organizationsOfUserResponse = modelMapper.map(organizationProfileList.get(i),GetOrganizationsOfUserResponse.class);
            organizationsOfUserResponse.setNumberOfApplicant(organizationIdList.get(i).getNumOfApplicants());
            organizationsOfUserResponse.setNumberOfRecruiter(organizationIdList.get(i).getNumOfRecruiters());
            result.add(organizationsOfUserResponse);
        }
        return result;
    }
}