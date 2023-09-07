package com.myblog.MyBlog.Repository;

import com.myblog.MyBlog.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
