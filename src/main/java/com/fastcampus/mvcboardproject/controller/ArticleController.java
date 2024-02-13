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

    
}

