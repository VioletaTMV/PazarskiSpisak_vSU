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

//Display uploaded images (от локалния сървър) in browser
//In case of uploaded files are images, we can display the images in browser with a little configuration.
// We need to expose the directory containing the uploaded files so the clients (web browsers) can access.
//    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        exposeDirectory(UPLOAD_DIRECTORY, registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(maintenanceInterceptor);
    }

    //Display uploaded (от локалния сървър) images in browser
//In case of uploaded files are images, we can display the images in browser with a little configuration.
// We need to expose the directory containing the uploaded files so the clients (web browsers) can access.
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {

        Path recipeImgUploadDir = Paths.get(dirName);
        String recipeImgUploadPath = recipeImgUploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) {
            dirName = dirName.replace("../", "");
        }
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ recipeImgUploadPath + "/");
    }



}
