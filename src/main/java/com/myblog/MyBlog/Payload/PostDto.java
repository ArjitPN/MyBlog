package com.myblog.MyBlog.Payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty//check whether the given argument array/map/collection/character sequence is not null or empty.
    @Size(min=2, message = "Post title Should have at least 2 character")
    private String title;
    @NotEmpty
    @Size(min=10, message = "Post description Should have at least 10 character")
    private String description;
    private String content;

}
