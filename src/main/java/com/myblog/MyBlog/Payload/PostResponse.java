package com.myblog.MyBlog.Payload;

import com.myblog.MyBlog.Entities.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int PageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean isLast;


}
