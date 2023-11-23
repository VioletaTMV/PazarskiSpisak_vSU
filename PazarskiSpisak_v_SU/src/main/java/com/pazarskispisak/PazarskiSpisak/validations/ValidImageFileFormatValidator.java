package com.pazarskispisak.PazarskiSpisak.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ValidImageFileFormatValidator implements ConstraintValidator<ValidImageFileFormat, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValidFileType = true;

        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            isValidFileType = false;
        }

        return isValidFileType;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }

}
