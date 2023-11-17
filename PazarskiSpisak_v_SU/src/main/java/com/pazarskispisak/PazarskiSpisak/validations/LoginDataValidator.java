//package com.pazarskispisak.PazarskiSpisak.models.validation;
//
//import com.pazarskispisak.PazarskiSpisak.models.entities.User;
//import com.pazarskispisak.PazarskiSpisak.service.UserService;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.PropertyAccessorFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//public class LoginDataValidator implements ConstraintValidator<LoginData, Object> {
//
//    private String email;
//    private String password;
//    private String message;
//    private UserService userService;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public LoginDataValidator(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    @Override
//    public void initialize(LoginData constraintAnnotation) {
//
//        this.email = constraintAnnotation.email();
//        this.password = constraintAnnotation.password();
//        this.message = constraintAnnotation.message();
//
//    }
//
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context) {
//
//        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
//
//        Object emailValue = beanWrapper.getPropertyValue(this.email);
//        Object passwordValue = beanWrapper.getPropertyValue(this.password);
//
//        boolean valid = true;
//
//        Optional<User> userOpt = this.userService.findByEmail(emailValue.toString());
//
//        if (userOpt.isEmpty()) {
//            valid = false;
//        } else {
//            String rawPassword = passwordValue.toString();
//            String existingUserEncryptedPassword = userOpt.get().getPassword();
//
//            boolean userPasswordsAreEqual = this.passwordEncoder.matches(rawPassword, existingUserEncryptedPassword);
//
//            if (!userPasswordsAreEqual) {
//                valid = false;
//            }
//        }
//
//        if (!valid) {
//            context
//                    .buildConstraintViolationWithTemplate(message)
//                    .addPropertyNode(this.password)
//                    .addConstraintViolation()
//                    .disableDefaultConstraintViolation();
//        }
//
//        return valid;
//    }
//}
