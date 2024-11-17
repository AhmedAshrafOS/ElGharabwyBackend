package com.project.elgharabwy.service;

import com.project.elgharabwy.dao.BlogDAO;
import com.project.elgharabwy.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class BlogServiceImpl implements BlogService {


    private final BlogDAO blogDAO;

    @Autowired
    public BlogServiceImpl(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }

    @Override
    public ResponseEntity<?> createBlog(@RequestBody Blog theBlog) {
        try {
            if (theBlog.getTitle() == null || theBlog.getCoverImageUrl() == null) {
                // Returning a bad request status with an error message
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Title and Cover Image URL are required fields.");
            } else {
                Blog savedBlog = blogDAO.save(theBlog);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedBlog);
            }
        } catch (Exception exception) {
            System.out.println("Error While Saving.....");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving the blog.");
        }
    }

    @Override
    public boolean getBlogByID(String id) {
        Optional<Blog> blog = blogDAO.findById(id);

        if (blog.isPresent()) {
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public ResponseEntity<?> updateBlog(String id, Blog blog) {
        if (this.getBlogByID(id)) {
            blog.setId(id); // Set the ID on the Blog instance
            Blog updatedBlog = blogDAO.save(blog); // Save the updated Blog
            return ResponseEntity.ok(updatedBlog);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Blog with ID " + id + " not found");
    }

    @Override
    public ResponseEntity<?> deleteBlog(String id) {
        if  (this.getBlogByID(id))  {
            blogDAO.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Blog with ID " + id + " not found");
    }

    @Override
    public List<Blog> findAll() {
        return blogDAO.findAll();
    }

}
