package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        User user = userRepository1.findById(userId).get();
        Blog blog = new Blog();
        blog.setPubDate(new Date());
        blog.setTitle(title);
        blog.setContent(content);
        blog.setImageList(new ArrayList<>());
        List<Blog> blogList = user.getBlogList();
        blogList.add(blog);
        user.setBlogList(blogList);
        blog.setUser(user);
        blogRepository1.save(blog);
        return blog;
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        Blog blog = blogRepository1.findById(blogId).get();
        List<Image> imageList = blog.getImageList();
        for(Image image : imageList){
            imageService.deleteImage(image.getId());
        }
        User user = blog.getUser();
        List<Blog> blogList = user.getBlogList();
        for(int i=0;i<blogList.size();i++){
            if(blogList.get(i)==blog){
                blogList.remove(i);
                break;
            }
        }
        user.setBlogList(blogList);
        blogRepository1.deleteById(blogId);
    }
}
