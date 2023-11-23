package com.pazarskispisak.PazarskiSpisak.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(RedirectAttributes redirectAttributes) {

        System.out.println();
        redirectAttributes.addFlashAttribute("fileUploadTooLargeInterceptorHandled",
                "Файлът не бе запазен тъй като е твърде голям. Върнете се назад и опитайте отново с по-малък файл.");

        return "redirect:/intercept";
    }
}
