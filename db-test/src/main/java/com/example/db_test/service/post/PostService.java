package com.example.db_test.service.post;

import com.example.db_test.dto.post.PostAllDto;
import com.example.db_test.dto.post.PostDetailDto;
import com.example.db_test.dto.post.PostDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.entity.post.PostEntity;
import com.example.db_test.exception.post.MemberNotFoundException;
import com.example.db_test.repository.MemberRepository;
import com.example.db_test.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void insert(PostDto postDto) {
        MemberEntity memberEntity = memberRepository
                .findById(postDto.getNumber())
                .orElseThrow(() -> new MemberNotFoundException("회원 가입 먼저 하세요."));
        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);
        postEntity.setMemberEntity(memberEntity);

        postRepository.save(postEntity);
    }

    public List<PostAllDto> getPost() {
//        List<PostAllDto> testList = postRepository
//                .findAll()
//                .stream()
//                .map(PostAllDto::new)
//                .toList();
//        System.out.println(Hibernate.isInitialized(testList.get(0).getMemberEntity()));
//        System.out.println(testList.get(0).getMemberEntity());
//        System.out.println(Hibernate.isInitialized(testList.get(0).getMemberEntity()));

        return postRepository
                .findAll()
                .stream()
                .map(PostAllDto::new)
                .toList();
    }

    public PostDetailDto getPostOne(Long id){
        return postRepository
                .findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(
                        ()-> new MemberNotFoundException("존재하지 않는 글")
                );
    }
}
