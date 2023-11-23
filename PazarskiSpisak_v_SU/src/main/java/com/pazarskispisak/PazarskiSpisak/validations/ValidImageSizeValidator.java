package com.pazarskispisak.PazarskiSpisak.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ValidImageSizeValidator implements ConstraintValidator<ValidImageSize, MultipartFile> {

    private int maxFileSize;

    @Override
    public void initialize(ValidImageSize constraintAnnotation) {
        this.maxFileSize = constraintAnnotation.maxFileSize();

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        boolean isFileSizeValid = true;

        System.out.println();
        if (/*multipartFile.isEmpty() */ multipartFile.getSize() == 0 || multipartFile.getSize() > maxFileSize){
            isFileSizeValid = false;
        }

        return isFileSizeValid;
    }

}
