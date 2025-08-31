package com.egle.dropbox.service;

import com.egle.dropbox.model.AccessToken;
import com.egle.dropbox.repository.AccessTokenRepository;
import com.egle.dropbox.repository.TeamInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.egle.dropbox.model.TeamInfo;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class DropboxService {

    private static final Logger log = LoggerFactory.getLogger(DropboxService.class);

    @Autowired
    private AccessTokenRepository tokenRepo;

    @Autowired
    private TeamInfoRepository teamRepo;

    private final RestTemplate rest = new RestTemplate();

    @Value("${app.dropbox.client-id}")
    private String clientId;

    @Value("${app.dropbox.client-secret}")
    private String clientSecret;

    @Value("${app.dropbox.redirect-uri}")
    private String redirectUri;

    public String buildAuthorizeUrl(String scopes, String state) {
       String url = "https://www.dropbox.com/oauth2/authorize?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri +
                "&token_access_type=offline&scope=" + scopes + "&state=" + state;

       log.info("Generated Dropbox authorize URL: {}", url);

        return  url;
        /*
        * below url for the testing purpose
        * **/
//       return "https://www.dropbox.com/oauth2/authorize?client_id=API_KEY&response_type=code&redirect_uri=http://localhost:8080/api/dropbox/oauth/callback&token_access_type=offline&scope=team_info.read&state=xyz123";
    }

    public AccessToken exchangeCodeForToken(String code, String teamId) {
        String url = "https://api.dropboxapi.com/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", code);
        form.add("grant_type", "authorization_code");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("redirect_uri", redirectUri);

        ResponseEntity<Map> resp = rest.postForEntity(url, new HttpEntity<>(form, headers), Map.class);
        Map body = resp.getBody();

        String accessToken = (String) body.get("access_token");
        String refreshToken = (String) body.get("refresh_token");
        Integer expiresIn = (Integer) body.get("expires_in");

        AccessToken token = new AccessToken(
                teamId,
                accessToken,
                refreshToken,
                Instant.now().plusSeconds(expiresIn)
        );
        return tokenRepo.save(token);
    }

    public TeamInfo fetchTeamInfo(String teamId) {
        AccessToken token = tokenRepo.findById(teamId).orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "https://api.dropboxapi.com/2/team/get_info";
        ResponseEntity<Map> resp = rest.exchange(url, HttpMethod.POST, new HttpEntity<>("{}", headers), Map.class);
        Map body = resp.getBody();

        TeamInfo info = new TeamInfo(
                (String) body.get("team_id"),
                (String) body.get("name"),
                (Integer) body.get("num_licensed_users"),
                (Integer) body.get("num_provisioned_users"),
                (Boolean) body.get("is_individual_team")
        );
        return teamRepo.save(info);
    }
}