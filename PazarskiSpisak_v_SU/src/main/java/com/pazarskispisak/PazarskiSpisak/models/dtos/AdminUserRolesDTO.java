//package com.pazarskispisak.PazarskiSpisak.models.dtos;
//
//import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
//
//import java.util.Objects;
//
//public class AdminUserRolesDTO {
//    private UserRoleEnum userRole;
//
//    public AdminUserRolesDTO(){}
//
//    public UserRoleEnum getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(UserRoleEnum userRole) {
//        this.userRole = userRole;
//    }
//
//    @Override
//    public String toString() {
//       return getUserRole().name();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AdminUserRolesDTO that = (AdminUserRolesDTO) o;
//        return userRole == that.userRole;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userRole);
//    }
//}
