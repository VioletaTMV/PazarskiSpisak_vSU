package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

   private UserService userService;

   @Autowired
    public UniqueUserEmailValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

       return this.userService.findByEmail(value).isEmpty();

    }
}
