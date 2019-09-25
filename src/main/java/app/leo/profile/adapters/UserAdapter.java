package app.leo.profile.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import app.leo.profile.dto.GetTokenRequest;
import app.leo.profile.dto.TokenDTO;

@Service
public class UserAdapter {
	@Value("${user.api.url}")
	private String userApiUrl;

	public TokenDTO getTokenByUsernameAndToken(String token){
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format(userApiUrl + "getToken");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<GetTokenRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entity,TokenDTO.class);
		return responseEntity.getBody();
	}

	public void deleteAllTokenByUsername(String token){
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format(userApiUrl + "/logout");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<GetTokenRequest> entity = new HttpEntity<>(headers);
		restTemplate.delete(url,entity);
	}
}