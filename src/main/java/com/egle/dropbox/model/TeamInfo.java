package com.egle.dropbox.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TeamInfo {

    @Id
    private String teamId;
    private String name;
    private Integer numLicensedUsers;
    private Integer numProvisionedUsers;
    private Boolean individualTeam;

    public TeamInfo() {}

    public TeamInfo(String teamId, String name, Integer numLicensedUsers,
                    Integer numProvisionedUsers, Boolean individualTeam) {
        this.teamId = teamId;
        this.name = name;
        this.numLicensedUsers = numLicensedUsers;
        this.numProvisionedUsers = numProvisionedUsers;
        this.individualTeam = individualTeam;
    }

    // getters
    public String getTeamId() { return teamId; }
    public String getName() { return name; }
    public Integer getNumLicensedUsers() { return numLicensedUsers; }
    public Integer getNumProvisionedUsers() { return numProvisionedUsers; }
    public Boolean getIndividualTeam() { return individualTeam; }

    // setters
    public void setTeamId(String teamId) { this.teamId = teamId; }
    public void setName(String name) { this.name = name; }
    public void setNumLicensedUsers(Integer numLicensedUsers) { this.numLicensedUsers = numLicensedUsers; }
    public void setNumProvisionedUsers(Integer numProvisionedUsers) { this.numProvisionedUsers = numProvisionedUsers; }
    public void setIndividualTeam(Boolean individualTeam) { this.individualTeam = individualTeam; }
}
