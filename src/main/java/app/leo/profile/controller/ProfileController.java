package app.leo.profile.controller;

import app.leo.profile.dto.ApplicantProfileDTO;
import app.leo.profile.dto.GetProfileRequest;
import app.leo.profile.dto.RecruiterProfileDTO;
import app.leo.profile.exception.UserNotExistException;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Profile;
import app.leo.profile.models.RecruiterProfile;
import app.leo.profile.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/profile/applicant")
    public ResponseEntity<ApplicantProfile> saveApplicantProfile(ApplicantProfileDTO applicantProfileDTO){
        ApplicantProfile applicantProfile = modelMapper.map(applicantProfileDTO,ApplicantProfile.class);
        return new ResponseEntity<>(profileService.saveApplicantProfile(applicantProfile), HttpStatus.ACCEPTED);
    }

    @PostMapping("/profile/recruiter")
    public ResponseEntity<RecruiterProfile> saveRecruiterProfile(RecruiterProfileDTO recruiterProfileDTO){
        RecruiterProfile recruiterProfile = modelMapper.map(recruiterProfileDTO,RecruiterProfile.class);
        return new ResponseEntity<>(profileService.saveRecruiterProfile(recruiterProfile),HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile/applicant")
    public ResponseEntity<ApplicantProfile> updateApplicantProfile(ApplicantProfile applicantProfileDTO){
        if(applicantProfileDTO.getApplicantId() ==0){
           throw new UserNotExistException("This profile does not exist");
        }
        ApplicantProfile applicantProfile = modelMapper.map(applicantProfileDTO,ApplicantProfile.class);
        return new ResponseEntity<>(profileService.saveApplicantProfile(applicantProfile), HttpStatus.OK);
    }

    @PutMapping("/profile/recruiter")
    public ResponseEntity<RecruiterProfile> updateRecruiterProfile(RecruiterProfileDTO recruiterProfileDTO){
        if(recruiterProfileDTO.getRecruiterId() ==0){
            throw new UserNotExistException("This profile does not exist");
        }
        RecruiterProfile recruiterProfile = modelMapper.map(recruiterProfileDTO,RecruiterProfile.class);
        return new ResponseEntity<>(profileService.saveRecruiterProfile(recruiterProfile),HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public  ResponseEntity<Profile> getProfile(@RequestBody GetProfileRequest getProfileRequest){
        return new ResponseEntity<>(profileService.getProfile(getProfileRequest.getId(),getProfileRequest.getRole()),HttpStatus.OK);
    }
}