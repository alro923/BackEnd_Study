package dev.uhhyunjoo.service.posts;

import dev.uhhyunjoo.domain.posts.Posts;
import dev.uhhyunjoo.domain.posts.PostsRepository;
import dev.uhhyunjoo.web.dto.PostsListResponseDto;
import dev.uhhyunjoo.web.dto.PostsResponseDto;
import dev.uhhyunjoo.web.dto.PostsSaveRequestDto;
import dev.uhhyunjoo.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true) // readOnly : 등록/수정/삭제 없이 조회만 있는 서비스 메소드에 쓰면 속도 빨라짐!
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream() // postsRepository 의 결과로 넘어온 Posts 의 Stream 을
                .map(PostsListResponseDto::new) // map 을 통해 PostsListResponseDto 로 변환해서 list 로 반환하는 메소드
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }
}