package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.validations.ValueOfEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminUserViewDTO {

    private Long id;
    private String email;
    private String displayNickname;
    private LocalDate dateRegistered;
    //    private Set<AdminUserRolesDTO> userRoles;

    private Set<@ValueOfEnum(enumClass = UserRoleEnum.class, message = "Невалидна роля.") String> userRoles;

    public AdminUserViewDTO() {
        this.userRoles = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

//    public Set<AdminUserRolesDTO> getUserRoles() {
//        return userRoles;
//    }
//
//    public void setUserRoles(Set<AdminUserRolesDTO> userRoles) {
//        this.userRoles = userRoles;
//    }


    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Override
    public String toString() {
        return "AdminUserViewDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", displayNickname='" + displayNickname + '\'' +
                ", dateRegistered=" + dateRegistered +
                ", userRoles=" + userRoles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminUserViewDTO that = (AdminUserViewDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(displayNickname, that.displayNickname) && Objects.equals(dateRegistered, that.dateRegistered) && Objects.equals(userRoles, that.userRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, displayNickname, dateRegistered, userRoles);
    }
}
