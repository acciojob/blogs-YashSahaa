package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        if(blog== null) return null;
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        blog.getImageList().add(image);
        blogRepository2.save(blog);
        //imageRepository2.save(image);
        return image;
    }

    public void deleteImage(Integer id){
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id,String screenDimensions){
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        int countImages = 0;
        String[] screenSize = screenDimensions.split("X");
        String[] imageSize = image.getDimensions().split("X");

        // finding height and width of image
        int imageH = Integer.parseInt(imageSize[0]);
        int imageW = Integer.parseInt(imageSize[1]);

        // finding height and width of screen
        int screenH = Integer.parseInt(screenSize[0]);
        int screenW = Integer.parseInt(screenSize[1]);

        return (screenH/imageH)*(screenW/imageW);
    }
}
