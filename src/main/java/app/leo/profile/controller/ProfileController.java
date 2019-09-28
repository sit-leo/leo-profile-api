package app.leo.profile.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.leo.profile.dto.ApplicantProfileDTO;
import app.leo.profile.dto.RecruiterProfileDTO;
import app.leo.profile.dto.User;
import app.leo.profile.exceptions.UserNotExistException;
import app.leo.profile.models.ApplicantProfile;
import app.leo.profile.models.Profile;
import app.leo.profile.models.RecruiterProfile;
import app.leo.profile.service.ProfileService;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/profile/applicant")
    public ResponseEntity<ApplicantProfile> saveApplicantProfile(@Valid @RequestBody ApplicantProfileDTO applicantProfileDTO){
        ApplicantProfile applicantProfile = modelMapper.map(applicantProfileDTO,ApplicantProfile.class);
        return new ResponseEntity<>(profileService.saveApplicantProfile(applicantProfile), HttpStatus.ACCEPTED);
    }

    @PostMapping("/profile/recruiter")
    public ResponseEntity<RecruiterProfile> saveRecruiterProfile(@Valid @RequestBody RecruiterProfileDTO recruiterProfileDTO){
        RecruiterProfile recruiterProfile = modelMapper.map(recruiterProfileDTO,RecruiterProfile.class);
        return new ResponseEntity<>(profileService.saveRecruiterProfile(recruiterProfile),HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile/applicant")
    public ResponseEntity<ApplicantProfile> updateApplicantProfile(@Valid @RequestBody ApplicantProfile applicantProfileDTO){
        if(applicantProfileDTO.getApplicantId() == 0){
           throw new UserNotExistException("This profile does not exist");
        }
        ApplicantProfile applicantProfile = modelMapper.map(applicantProfileDTO,ApplicantProfile.class);
        return new ResponseEntity<>(profileService.saveApplicantProfile(applicantProfile), HttpStatus.OK);
    }

    @PutMapping("/profile/recruiter")
    public ResponseEntity<RecruiterProfile> updateRecruiterProfile(@Valid @RequestBody RecruiterProfile recruiterProfileDTO){
        if(recruiterProfileDTO.getRecruiterId() ==0){
            throw new UserNotExistException("This profile does not exist");
        }
        RecruiterProfile recruiterProfile = modelMapper.map(recruiterProfileDTO,RecruiterProfile.class);
        return new ResponseEntity<>(profileService.saveRecruiterProfile(recruiterProfile),HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public  ResponseEntity<Profile> getProfile(@RequestAttribute(name ="user") User user){
        return new ResponseEntity<>(profileService.getProfile(user.getId(),user.getRole()),HttpStatus.OK);
    }
}
