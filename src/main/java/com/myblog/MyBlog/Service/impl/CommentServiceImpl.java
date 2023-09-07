package com.myblog.MyBlog.Service.impl;

import com.myblog.MyBlog.Entities.Comment;
import com.myblog.MyBlog.Entities.Post;
import com.myblog.MyBlog.Exception.BlogAPIException;
import com.myblog.MyBlog.Exception.ResourseNotFoundException;
import com.myblog.MyBlog.Payload.CommentDto;
import com.myblog.MyBlog.Repository.CommentRepository;
import com.myblog.MyBlog.Repository.PostRepository;
import com.myblog.MyBlog.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", postId)
        );
        comment.setPost(post);
        //comment to entity to DB
        Comment newComment=commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", postId)); //for find the post , if the post is there then check comment

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourseNotFoundException("Comment", "id", commentId));//for check comment
        if (!Objects.equals(comment.getPost().getId(), post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment not found");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", id));
        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to Post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post", "id", id));
        if (!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
        }
        commentRepository.deleteById(comment.getId());

    }
    private CommentDto mapToDto(Comment newComment) {
        CommentDto commentDto = modelMapper.map(newComment, CommentDto.class);
        return commentDto;
//        CommentDto dto=new CommentDto();
//        dto.setId(newComment.getId());
//        dto.setName(newComment.getName());
//        dto.setEmail(newComment.getEmail());
//        dto.setBody(newComment.getBody());
//        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
//        Comment comment=new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
//        return comment;
    }
}
