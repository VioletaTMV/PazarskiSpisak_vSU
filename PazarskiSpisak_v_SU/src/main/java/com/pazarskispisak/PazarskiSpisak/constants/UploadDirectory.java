package com.pazarskispisak.PazarskiSpisak.constants;

public class UploadDirectory {

//    public static final String UPLOAD_DIRECTORY = "recipe-pictures" + "/uploads";

    //единствено долната константа трябва да променя ако искам да сменя директорията, на локалния PC, където да се пазят снимките
    public static final String UPLOAD_DIRECTORY = "C:/Users/Violeta/Documents/JavaProgramsUploads" + "/recipe-pictures" + "/uploads";

    //а долните константи се отнасят при качване на снимките в Cloudinary
    public static final String CLOUDINARY_BASE_IMAGE_UPLOAD_URL = "http://res.cloudinary.com/pazarski/image/upload/";
    public static final String CLOUDINARY_UPLOAD_FOLDER = "recipe-pic";
}
