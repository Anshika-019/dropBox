package com.egle.dropbox.controller;


import com.egle.dropbox.model.TeamInfo;
import com.egle.dropbox.service.DropboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    private final DropboxService dropboxService;

    @Autowired
    public TeamController(DropboxService dropboxService) {
        this.dropboxService = dropboxService;
    }

    /*
    Endpoint to fetch team information by team ID
     */
    @GetMapping("/team/members/list/{teamId}")
    public TeamInfo fetchTeam(@PathVariable String teamId) {
        return dropboxService.fetchTeamInfo(teamId);
    }
}