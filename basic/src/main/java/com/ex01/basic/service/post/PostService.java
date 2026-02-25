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
import com.ex01.basic.repository.post.PostLikeRepository;
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
    private final PostLikeRepository postLikeRepository;

    @Transactional(readOnly = true)
    public HashMap<String, Object> getPosts(int start, int memberId) {
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
        res.put("content", page.getContent().stream().map(post -> {
            int postCount = getPostCount(post.getId());
            boolean liked = false;
            if (memberId != 0) {
                liked = postLikeRepository
                        .existsByMemberEntity_IdAndPostEntity_Id(
                                memberId,
                                post.getId()
                        );
            }
            int likeCount = getLikeCount(post.getId());
            return new PostAllDto(post, postCount, liked, likeCount);
        }));
        res.put("number", page.getNumber());
        return res;
    }

    public PostAllDto getPost(int postId, int memberId) {
        return postRepository
                .findById(postId)
                .map(post -> {
                            checkMemPostId(memberId, postId);
                            int postCount = getPostCount(postId);
                            boolean liked = postLikeRepository
                                    .existsByMemberEntity_IdAndPostEntity_Id(
                                            memberId, postId
                                    );
                            int likeCount = getLikeCount(post.getId());
                            return new PostAllDto(post, postCount, liked, likeCount);
                        }
                )
                .orElseThrow(PostNotFoundException::new);
    }

    private void checkMemPostId(int memberId, int postId) {
        if (!postCountRepository.existsByMemberEntity_IdAndPostEntity_Id(memberId, postId)) {
            MemberEntity memberEntity = memberRepository.getReferenceById(memberId);
            PostEntity postEntity = postRepository.getReferenceById(postId);

            PostCountEntity postCountEntity = new PostCountEntity(memberEntity, postEntity);
            postCountRepository.save(postCountEntity);
        }
    }

    private int getPostCount(int postId) {

        return postCountRepository.countByPostEntity_Id(postId);
    }

    private int getLikeCount(int postId) {

        return postLikeRepository.countByPostEntity_Id(postId);
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

        postEntity.getPostCounts().clear();
        postEntity.getPostLikes().clear();
        postRepository.deleteById(id);
    }
}
