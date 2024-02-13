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
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        map.addAttribute("article", "article"); // TODO: 구현할 때 여기에 실제 데이터를 넣어줘야 한다
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }

}

