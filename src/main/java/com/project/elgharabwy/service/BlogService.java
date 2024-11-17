package com.project.elgharabwy.service;

import com.project.elgharabwy.entity.Blog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;




public interface BlogService {

    ResponseEntity<?> createBlog(Blog theBlog);

    List<Blog> findAll();

    ResponseEntity<?> deleteBlog(String id);

    ResponseEntity<?> updateBlog(@PathVariable String id, @RequestBody Blog blog) ;

    boolean getBlogByID(String id);


}
