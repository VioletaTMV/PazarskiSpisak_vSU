package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.FieldMatch;
import com.pazarskispisak.PazarskiSpisak.validations.UniqueUserEmail;
import com.pazarskispisak.PazarskiSpisak.validations.UniqueUserNickname;
import jakarta.validation.constraints.*;


@FieldMatch(
        first = "password",
        second = "passwordRepeat",
        message = "Паролите не съвпадат"
)
public class UserRegisterDTO {

    @NotBlank(message = "Задължително поле.")
    @Email(message = "Невалиден имейл.")
    @UniqueUserEmail(message = "Потребител с този имейл е вече регистриран.")
    private String email;

    @NotBlank(message = "Задължително поле.")
    @Size(min = 8, message = "Минимум 8 символа.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$",
            message = "Паролата трябва да бъде поне 8 знака и да съдържа поне 1 главна буква, 1 малка буква, 1 специален символ и 1 цифра.")
    private String password;

    private String passwordRepeat;

    @NotBlank(message = "Задължително поле.")
    @Size(min = 2, max = 23, message = "Очаквана дължина между 2 и 23 символа.")
    @UniqueUserNickname(message = "Потребителското име е заето.")
    private String displayNickname;

    public UserRegisterDTO(){}

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public UserRegisterDTO setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
        return this;
    }

    public String getDisplayNickname() {
        return displayNickname;
    }

    public UserRegisterDTO setDisplayNickname(String displayNickname) {
        this.displayNickname = displayNickname;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "email='" + email + '\'' +
                ", password='" + (password != null ? "[PROVIDED]" : null) + '\'' +
                ", passwordRepeat='" + (passwordRepeat != null ? "[PROVIDED]" : null) + '\'' +
                ", displayNickname='" + displayNickname + '\'' +
                '}';
    }
}
