//package com.pazarskispisak.PazarskiSpisak.models.dtos;
//
//import com.pazarskispisak.PazarskiSpisak.models.validation.LoginData;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//
//
//
////цялото UserLoginDTO става излишно, можем да го изтрием, тъй като функционалността е поверена на Спринг Секюрити
//@LoginData(
//        email = "email",
//        password = "password",
//        message = "Грешна комбинация от имейл и парола"
//)
//public class UserLoginDTO {
//
//    @NotBlank(message = "Задължително поле.")
//    @Email(message = "Невалиден имейл.")
//    private String email;
//
//    private String password;
//
//    public UserLoginDTO() {
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public UserLoginDTO setEmail(String email) {
//        this.email = email;
//        return this;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public UserLoginDTO setPassword(String password) {
//        this.password = password;
//        return this;
//    }
//
//    @Override
//    public String toString() {
//        return "UserLoginDTO{" +
//                "email='" + email + '\'' +
//                ", password='" + (password != null ? "[PROVIDED]" : null) + '\'' +
//                '}';
//    }
//}
