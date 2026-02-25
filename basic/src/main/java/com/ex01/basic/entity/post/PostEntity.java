package com.ex01.basic.entity.post;

import com.ex01.basic.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member_post")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private MemberEntity memberEntity;

    @OneToMany(
            mappedBy = "postEntity",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<PostCountEntity> postCounts = new ArrayList<>();


    @OneToMany(
            mappedBy = "postEntity",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<PostLikeEntity> postLikes = new ArrayList<>();


    private String title, content;

    @Column(nullable = true)
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;

    @PreUpdate
    public void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

}
