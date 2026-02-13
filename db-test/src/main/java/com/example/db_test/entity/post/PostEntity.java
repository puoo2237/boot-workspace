package com.example.db_test.entity.post;

import com.example.db_test.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY) // 지연 로딩 (원래는 같이 로딩이 되나 이것을 설정하면 나중에 로딩이 가능함)
    @JoinColumn(name="number", nullable = true )
    private MemberEntity memberEntity;

    private String title, content;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }

}

//CREATE TABLE post(
//        id BIGINT AUTO_INCREMENT PRIMARY KEY,
//        number BIGINT NOT NULL,
//        title VARCHAR(255),
//content VARCHAR(255),
//created_at DATETIME ,
//update_time DATETIME ,
//CONSTRAINT fk_post_member FOREIGN KEY(number) REFERENCES member_test(number)
//        );