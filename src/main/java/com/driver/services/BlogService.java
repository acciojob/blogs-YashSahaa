package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;

    @Autowired
    ImageService imageService;

    public Blog createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
        User user = userRepository1.findById(userId).orElse(null);
        Blog blog = new Blog(title,content);
        blogRepository1.save(blog);
        List<Blog> blogList = user.getBlogs();
        blogList.add(blog);
        user.setBlogs(blogList);
        return blog;
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        Blog blog = blogRepository1.findById(blogId).orElse(null);
        List<Image> imageList = blog.getImages();

        for(Image image : imageList){
            imageService.deleteImage(image.getId());
        }
        blogRepository1.deleteById(blogId);
    }
}
