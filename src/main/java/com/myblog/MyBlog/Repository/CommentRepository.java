package com.myblog.MyBlog.Repository;

import com.myblog.MyBlog.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);//it will search the record based on postId
    //List<Comment>findByPostId(String email);
}
