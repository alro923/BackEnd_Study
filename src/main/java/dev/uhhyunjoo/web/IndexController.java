package dev.uhhyunjoo.web;

import dev.uhhyunjoo.service.posts.PostsService;
import dev.uhhyunjoo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
        // 앞 경로 : src/main/resources/templates
        // 뒤의 파일 확장자 : .mustache 가 붙는다.
        // '/' 를 호출하면 src/main/resources/templates/index.mustache 로 전환되어 View Resolver 가 처리
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
        // '/posts/save' 를 호출하면 posts-save.mustache 를 호출하는 메소드
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
