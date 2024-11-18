package com.project.elgharabwy.rest;

import com.project.elgharabwy.entity.Blog;
import com.project.elgharabwy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:3000", "https://elgharabwy-clinc.netlify.app"})
@RestController
@RequestMapping("/api/blog")
public class BlogRestController {

    private final BlogService blogService;

    @Autowired
    public BlogRestController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {

        blog.setDate(LocalDate.now());

        return blogService.createBlog(blog);
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable String id, @RequestBody Blog blog) {
        return blogService.updateBlog(id, blog);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable String id) {
        return blogService.deleteBlog(id);
    }
}
