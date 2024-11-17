package com.project.elgharabwy.dao;

import com.project.elgharabwy.entity.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BlogDAO extends MongoRepository<Blog, String> {


}
