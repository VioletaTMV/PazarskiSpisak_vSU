package com.pazarskispisak.PazarskiSpisak.web.interceptors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(MaxUploadSizeExceededException ex) {

        System.out.println("maxUloadedSizeExceededException global handler");
        return "maxUploadSizeExceededExceptionGlobal";

    }

}
