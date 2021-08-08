package com.treeleaf.blog.image;

import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.util.FileUploadProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("images")
public class ImageController {

    private final Path dirLocation;

    @Autowired
    public ImageController(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
    }

    /**
     * Display the images stored on dir
     *
     * @param filename
     * @return
     */
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {

        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(dirLocation+ File.separator+filename));
        } catch (IOException e) {
            throw new ResourceNotFoundException("Image not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
