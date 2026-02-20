package com.ex01.basic.service.post;

import com.ex01.basic.dto.post.PostAddDto;
import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostCountEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.exception.MemberAccessDeniedException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.exception.post.PostNotFoundException;
import com.ex01.basic.repository.MemberRepository;
import com.ex01.basic.repository.post.PostCountRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostCountRepository postCountRepository;

    @Transactional(readOnly = true)
    public HashMap<String, Object> getPosts(int start) {
        int size = 20;
        Pageable pageable = PageRequest.of(
                start,
                size,
                Sort.by(Sort.Order.desc("id"))
        );
        Page<PostEntity> page = postRepository
                .findAll(pageable);
        if (page.isEmpty())
            throw new PostNotFoundException();
        HashMap<String, Object> res = new HashMap<>();
        res.put("totalPages", page.getTotalPages());
        res.put("content", page.getContent().stream().map(PostAllDto::new));
        res.put("number", page.getNumber());
        return res;
    }

    public PostAllDto getPost(int id) {
        PostAllDto res = postRepository
                .findById(id)
                .map(PostAllDto::new)
                .orElseThrow(PostNotFoundException::new);

        int postCount = getPostCount(res.getMemUserId(), res.getId());
        res.setPostCount(postCount);
        return res;
    }

    private int getPostCount(int member_id, int post_id) {
        if (!postCountRepository.existsByMemberEntity_IdAndPostEntity_Id(member_id, post_id)) {
            MemberEntity memberEntity = memberRepository.getReferenceById(member_id);
            PostEntity postEntity = postRepository.getReferenceById(post_id);

            PostCountEntity postCountEntity = new PostCountEntity(memberEntity, postEntity);
            postCountRepository.save(postCountEntity);
        }
        return postCountRepository.countByPostEntity_Id(post_id);
    }

    public void insert(PostAddDto postAddDto, String username) {
        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postAddDto, postEntity);
        MemberEntity memberEntity = memberRepository
                .findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
        postEntity.setMemberEntity(memberEntity);
        postRepository.save(postEntity);
    }

    public void update(int id, PostAddDto postAddDto, String username) {
        PostEntity postEntity = postRepository
                .findById(id)
                .orElseThrow(PostNotFoundException::new);
        if (!postEntity
                .getMemberEntity()
                .getUsername()
                .equals(username))
            throw new MemberAccessDeniedException();

        postEntity.setTitle(postAddDto.getTitle());
        postEntity.setContent(postAddDto.getContent());
        postRepository.save(postEntity);
    }

    public void delete(int id, String username) {
        PostEntity postEntity = postRepository
                .findById(id)
                .orElseThrow(PostNotFoundException::new);

        if (!postEntity
                .getMemberEntity()
                .getUsername()
                .equals(username))
            throw new MemberAccessDeniedException();

        postRepository.deleteById(id);
    }
}
