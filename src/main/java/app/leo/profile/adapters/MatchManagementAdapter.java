package app.leo.profile.adapters;

import app.leo.profile.dto.GetTokenRequest;
import app.leo.profile.dto.IdWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MatchManagementAdapter {

    @Value("${match.management.api.url}")
    private String matchManagementAPIUrl;

    public IdWrapper getApplicantInOrgByOrgProfileId(long profileId, String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = matchManagementAPIUrl + "/organization/applicants/" + profileId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<GetTokenRequest> entity = new HttpEntity<>(headers);
        ResponseEntity<IdWrapper> responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,IdWrapper.class);
        return responseEntity.getBody();
    }
}
