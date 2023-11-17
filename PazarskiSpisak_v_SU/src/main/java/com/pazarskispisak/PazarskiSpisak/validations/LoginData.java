//package com.pazarskispisak.PazarskiSpisak.models.validation;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
//@Constraint(validatedBy = LoginDataValidator.class)
//public @interface LoginData {
//
//    String email();
//    String password();
//
//    String message() default "Invalid combination of email and password";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
