package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImportUserDTO implements Serializable {

    @Expose
    @SerializedName("user_id")
    private Short legacyId;

    @Expose
    @SerializedName("user_logname")
    private String legacyUserLogname;

    @Expose
    @SerializedName("user_pass")
    private String password;

    @Expose
    @SerializedName("user_email")
    private String email;

    @Expose
    @SerializedName("user_name")
    private String displayNickname;

    public ImportUserDTO(){}


    public String getDisplayNickname() {
        return displayNickname;
    }

    public String getLegacyUserLogname() {
        return legacyUserLogname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Short getLegacyId() {
        return legacyId;
    }
}
