package com.pazarskispisak.PazarskiSpisak.models.dtos;

import java.util.Objects;

public class UserBasicDTO {

    private Long id;
    private String displayNickname;

    public UserBasicDTO(){}

    public Long getId() {
        return id;
    }

    public UserBasicDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDisplayNickname() {
        return displayNickname;
    }

    public UserBasicDTO setDisplayNickname(String displayNickname) {
        this.displayNickname = displayNickname;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBasicDTO that = (UserBasicDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDisplayNickname(), that.getDisplayNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDisplayNickname());
    }

    @Override
    public String toString() {
        return "UserBasicDTO{" +
                "id=" + id +
                ", displayNickname='" + displayNickname + '\'' +
                '}';
    }
}
