package com.treeleaf.blog.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class FileUploadUtil {



    public static void saveFile(String uploadDir, String fileName, MultipartFile image){

        String storedPath = "";

        try{


            File dir = new File(uploadDir+ File.separator+"images");
            if(!dir.exists()){
                dir.mkdirs();
            }

            byte[] bytes = image.getBytes();
            storedPath = dir.getAbsolutePath()+ File.separator+ fileName;
            File serverFile = new File(storedPath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(String.valueOf(new FileOutputStream(serverFile))));
            stream.write(bytes);
            stream.close();
        }catch(IOException e){

        }
    }
}
