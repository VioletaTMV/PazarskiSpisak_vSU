package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pazarskispisak.PazarskiSpisak.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String folderName, String recipeImageName) {

        try{
            Map cloudinaryPicParams = ObjectUtils.asMap(
                    "public_id", recipeImageName,
                    "folder", folderName);

            Map cloudinaryUploadResult = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), cloudinaryPicParams);

//            return cloudinary.url().secure(true).generate(publicId);
            return cloudinaryUploadResult.get("url").toString();

        } catch (IOException e) {
            System.out.println("Image with ID: " + recipeImageName + "could not be uploaded to Cloudinary.");
            e.printStackTrace();
            return null;
        }


        //а долното при съхранение в Cloudinary
//        Map cloudinaryPicParams = ObjectUtils.asMap(
//                "public_id", recipeImageName,
//                "asset_folder", folderName);
//
//        Map cloudinaryUploadResult = cloudinary.uploader()
//                .upload(multipartFile.getBytes(), cloudinaryPicParams);

//        String uploadedPicCloudURL = cloudinary.uploader()
//                .upload(multipartFile.getBytes(),
//                        Map.of("public_id", recipeImageName))
//                .get("url")
//                .toString();

    }
}
