package com.myblog.MyBlog.Service.impl;

import com.myblog.MyBlog.Entities.Post;
import com.myblog.MyBlog.Exception.ResourseNotFoundException;
import com.myblog.MyBlog.Payload.PostDto;
import com.myblog.MyBlog.Payload.PostResponse;
import com.myblog.MyBlog.Repository.PostRepository;
import com.myblog.MyBlog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
     //convert DTO to entity
        Post post=mapToEntity(postDto);
        Post newPost = postRepository.save(post);

     //convert entity to DTO
     PostDto dto= mapToDTO(newPost);
     return dto;

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        //instance of Pagination
        PageRequest pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();//this method will convert page of post into list of post
        List<PostDto> dto = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();

        postResponse.setContent(dto);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalElements(content.getNumberOfElements());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setLast(content.isLast());

        return postResponse;

    }
    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post", "Id", id)
        );
        return mapToDTO(post);

    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post", "Id", id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", id)
        );
        postRepository.deleteById(id);
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
//        return dto;
    }

    private Post mapToEntity(PostDto dto) {
        Post post = mapper.map(dto, Post.class);
        return post;
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        return post;
    }


}
