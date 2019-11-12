package app.leo.profile.adapters;

import app.leo.profile.HttpEntityFactory;
import app.leo.profile.dto.IdWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatchManagementAdapter {

    @Value("${match.management.api.url}")
    private String matchManagementAPIUrl;

    public IdWrapper getApplicantInOrgByOrgProfileId(long profileId, String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = matchManagementAPIUrl + "/organization/applicants/" + profileId;
        HttpEntity<?> entity =  HttpEntityFactory.getHttpEntity(token);
        ResponseEntity<IdWrapper> responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,IdWrapper.class);
        return responseEntity.getBody();
    }

    public IdWrapper getRecruiterInOrgByOrgProfileId(long profileId, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = matchManagementAPIUrl + "/organization/recruiters/" + profileId;
        HttpEntity<?> entity = HttpEntityFactory.getHttpEntity(token);
        ResponseEntity<IdWrapper> responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,IdWrapper.class);
        return responseEntity.getBody();
    }

    public IdWrapper getOrganizationsOfUser(long profileId,String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = matchManagementAPIUrl + "/user/" + profileId + "/organizations";
        HttpEntity<?> entity = HttpEntityFactory.getHttpEntity(token);
        ResponseEntity<IdWrapper> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entity,IdWrapper.class);
        return responseEntity.getBody();
    }
}
