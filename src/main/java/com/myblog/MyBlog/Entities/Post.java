package com.myblog.MyBlog.Entities;

import lombok.*;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Title", nullable = false)
    private String title;
    @Column(name = "Description", nullable = false)
    private String description;
    @Column(name = "Content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();//bcoz set has only unique data

}
