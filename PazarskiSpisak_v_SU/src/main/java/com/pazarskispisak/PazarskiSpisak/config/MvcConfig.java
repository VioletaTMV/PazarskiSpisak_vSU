package com.pazarskispisak.PazarskiSpisak.config;


import com.pazarskispisak.PazarskiSpisak.web.interceptors.MaintenanceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.pazarskispisak.PazarskiSpisak.constants.UploadDirectory.UPLOAD_DIRECTORY;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
    private final MaintenanceInterceptor maintenanceInterceptor;

    @Autowired
    public MvcConfig(MaintenanceInterceptor maintenanceInterceptor) {
        this.maintenanceInterceptor = maintenanceInterceptor;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        exposeDirectory(UPLOAD_DIRECTORY, registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(maintenanceInterceptor);
    }


    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {

        Path recipeImgUploadDir = Paths.get(dirName);
        String recipeImgUploadPath = recipeImgUploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) {
            dirName = dirName.replace("../", "");
        }
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ recipeImgUploadPath + "/");
    }



}
