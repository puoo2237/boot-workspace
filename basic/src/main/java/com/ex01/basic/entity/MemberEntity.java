package com.ex01.basic.entity;

import com.ex01.basic.entity.post.PostCountEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.entity.post.PostLikeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private String fileName;

    @OneToMany(
            mappedBy = "memberEntity",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<PostEntity> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "memberEntity",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<PostCountEntity> postCounts = new ArrayList<>();


    @OneToMany(
            mappedBy = "memberEntity",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<PostLikeEntity> postLikes = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.role == null || this.role.equals("") ) {
            this.role = "USER";
        }
    }


}
