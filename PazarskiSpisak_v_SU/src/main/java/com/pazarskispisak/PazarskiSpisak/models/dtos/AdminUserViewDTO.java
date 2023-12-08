package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminUserViewDTO {

    private String email;
    private String displayNickname;
    private LocalDate dateRegistered;
    private Set<AdminUserRolesDTO> userRoles;
    private Set<String> userRoleNames;

    public AdminUserViewDTO (){ }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayNickname() {
        return displayNickname;
    }

    public void setDisplayNickname(String displayNickname) {
        this.displayNickname = displayNickname;
    }

    public Set<AdminUserRolesDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<AdminUserRolesDTO> userRoles) {
        this.userRoles = userRoles;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public Set<String> getUserRoleNames() {
        return userRoleNames;
    }

    public void setUserRoleNames(Set<String> userRoleNames) {
        this.userRoleNames = userRoleNames;
    }
}
