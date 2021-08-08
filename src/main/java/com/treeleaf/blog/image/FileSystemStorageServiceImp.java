package com.treeleaf.blog.image;

import com.treeleaf.blog.exception.FileNotFoundException;
import com.treeleaf.blog.exception.FileStorageException;
import com.treeleaf.blog.util.FileUploadProperties;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageServiceImp   implements FileSystemStorageService{

    private final Path dirLocation;

    @Autowired
    public FileSystemStorageServiceImp(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
    }

    @Override
    @PostConstruct
    public void init() {

        try{
            Files.createDirectories(this.dirLocation);
        }catch(Exception e){
            throw new FileStorageException("Could no create upload dir!!");
        }
    }

    @Override
    public String saveFile(MultipartFile file)  {
        try{
            String  originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID()+"."+ FilenameUtils.getExtension(originalFilename);

            Path dfile = this.dirLocation.resolve(fileName);
            Files.copy(file.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch(Exception e){
            System.out.println(e);
            throw new FileStorageException("Could no create upload dir!!");
        }

    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new FileNotFoundException("Could not find file");
            }
        }catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
