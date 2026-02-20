package com.ex01.basic.entity;

import com.ex01.basic.entity.post.PostEntity;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "memberEntity")
    private List<PostEntity> posts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.role == null || this.role.equals("") ) {
            this.role = "USER";
        }
    }


}
