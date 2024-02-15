package com.fastcampus.mvcboardproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
    만들어야 하는 api 목록
    /api/aricles
    /api/aricles/{article-id}
    /api/article/{article-id}/articleComments
 */
@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    // ModelMap은 컨트롤러가 뷰에 데이터를 전달하는 데 사용되는 스프링의 데이터 저장 및 검색 매커니즘
    public String articles(ModelMap map) {
        // 빈 리스트를 articles라는 이름으로 모델에 추가. 뷰에서 사용될 데이터를 가지고 있음.
        map.addAttribute("articles", List.of());
        // "articles/index"를 반환(컨트롤러가 렌더링할 뷰의 이름)
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    // 파라미터(게시글 번호)를 받아서 매개변수에 추가함
    public String article(@PathVariable Long articleId, ModelMap map) {
        map.addAttribute("article", "article"); // TODO: 구현할 때 여기에 실제 데이터를 넣어줘야 한다
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }

}

