package dev.uhhyunjoo.service.posts;

import dev.uhhyunjoo.domain.posts.Posts;
import dev.uhhyunjoo.domain.posts.PostsRepository;
import dev.uhhyunjoo.web.dto.PostsResponseDto;
import dev.uhhyunjoo.web.dto.PostsSaveRequestDto;
import dev.uhhyunjoo.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));
        return new PostsResponseDto(entity);
    }
}