package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;

public class AdminUserRolesDTO {
    private UserRoleEnum userRole;

    public AdminUserRolesDTO(){}

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
       return getUserRole().name();
    }
}
