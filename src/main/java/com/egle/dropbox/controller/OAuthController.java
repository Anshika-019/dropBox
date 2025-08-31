package com.egle.dropbox.controller;


import com.egle.dropbox.dtos.ApiResponse;
import com.egle.dropbox.model.AccessToken;
import com.egle.dropbox.service.DropboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/dropbox")
public class OAuthController {
    private static final Logger log = LoggerFactory.getLogger(OAuthController.class);

    private final DropboxService dropboxService;

    @Value("${app.dropbox.scopes}")
    private String scopes;

    public OAuthController(DropboxService dropboxService) {
        this.dropboxService = dropboxService;
    }
    /**
     * Endpoint to initiate Dropbox OAuth2 login.
     * Generates and returns the authorization URL.
     */

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<String>> login() {
        log.info("Generating Dropbox authorization URL with scopes: {}", scopes);
        String url = dropboxService.buildAuthorizeUrl(scopes, "xyz123");
        return ResponseEntity.ok(new ApiResponse<String>(true, "Dropbox authorization URL generated successfully", url));
    }


    /*
       * OAuth2 callback endpoint to handle Dropbox's redirect after user authorization.
        * * Exchanges the authorization code for an access token and stores it.
     */
    @GetMapping("/oauth/callback")
    public ResponseEntity<ApiResponse<AccessToken>> callback(
            @RequestParam String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false, name = "team_id") String teamId) {

        log.info("OAuth callback received with code: {} and teamId: {}", code, teamId);

        AccessToken token = dropboxService.exchangeCodeForToken(
                code,
                teamId != null ? teamId : "default-team"
        );

        return ResponseEntity.ok(new ApiResponse<AccessToken>(true, "Token stored successfully", token));

    }
}