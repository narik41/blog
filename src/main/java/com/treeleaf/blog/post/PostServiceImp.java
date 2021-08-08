package com.treeleaf.blog.post;

import com.sun.org.apache.xpath.internal.operations.Mult;
import com.treeleaf.blog.comment.CommentView;
import com.treeleaf.blog.exception.*;
import com.treeleaf.blog.image.FileSystemStorageService;
import com.treeleaf.blog.image.Image;
import com.treeleaf.blog.image.ImageRepository;
import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PostServiceImp implements  PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepostitory userRepostitory;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileSystemStorageService fileSystemStorage;

    public Map<String, Object> getList(int page, int size){
        Pageable paging = PageRequest.of(page,size, Sort.by("id").descending());
        Page<OnlyPost> posts = postRepository.findAllProjectBy(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts.getContent());
        response.put("currentPage", posts.getNumber());
        response.put("totalItems", posts.getTotalElements());
        response.put("totalPages", posts.getTotalPages());

        return response;
    }

    @Override
    public PostView getPostDetails(Long id) {

        List<PostView> posts = postRepository.findAllProjectById(id);
        return posts.stream().findFirst().get();
    }

    /**
     * Validate the image type
     *
     * @param file
     */
    private void validateImage(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw  new FileNotFoundException("Please upload a image");
        }
    }

    @Override
    public void store(PostRequest request)   {

        validateImage(request.getImage());
        String upfile = fileSystemStorage.saveFile(request.getImage());
        Resource filePath = fileSystemStorage.loadFile(upfile);

        try{

            // save image
            Image image = new Image();
            image.setName(upfile);
            image.setPath(filePath.getURI().toString());
            image = imageRepository.save(image);

            User user = userRepostitory.findById(1L).orElseThrow(()->new RuntimeException("user not found"));

            // save post
            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setDescription(request.getDescription());
            post.setUser(user);
            post.getImages().add(image);

            postRepository.save(post);
        }catch(Exception e){
            try{
                Files.delete((Path) filePath);
            }catch(IOException exception){
            }

            throw new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }

    }

    @Override
    public void update(Long id, PostRequest request) {

        // get the post
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found."));
        if(post.getUser().getId() != 1){
            throw new UserAuthorizationException("You are not authorized to delete the post.");
        }

        // get the images to delete from the disk if new image is uploaded
        Set<Image> storedImages = post.getImages();
        boolean isNewImageUploaded = false;

        try{
            Image image = new Image();
            if(request.getImage() != null){
                // if image is uploaded , upload that image
                validateImage(request.getImage());
                String upfile = fileSystemStorage.saveFile(request.getImage());
                Resource filePath = fileSystemStorage.loadFile(upfile);

                image.setName(upfile);
                image.setPath(filePath.getURI().toString());
                image = imageRepository.save(image);
                System.out.println(upfile);
                isNewImageUploaded = true;
            }

            post.setTitle(request.getTitle());
            post.setDescription(request.getDescription());
            if(image != null)
                post.getImages().add(image);

            postRepository.save(post);
        }catch(Exception e){
            isNewImageUploaded = false;
            throw new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }finally {
            if(isNewImageUploaded){
               /* storedImages.stream().forEach((image)->{
                    System.out.println(image.getPath());
                    imageRepository.delete(image);

                    try{
                        Path imagePath = Paths.get(image.getDirPath());
                        Files.delete(imagePath);
                    }catch (IOException e){
                    }
                });

                */
            }
        }
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found."));
        if(post.getUser().getId() != 1){
            throw new UserAuthorizationException("You are not authorized to delete the post.");
        }
        Set<Image> storedImages = post.getImages();
        postRepository.delete(post);

        // delete the images related to the post.
        storedImages.stream().forEach((image)->{
            imageRepository.delete(image);
            try{
                Path imagePath = Paths.get(image.getDirPath());
                Files.delete(imagePath);

            }catch (IOException e){
                System.out.println(e);
            }
        });
    }


}
