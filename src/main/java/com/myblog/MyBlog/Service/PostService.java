package com.myblog.MyBlog.Service;

import com.myblog.MyBlog.Payload.PostDto;
import com.myblog.MyBlog.Payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);


    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


     PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
