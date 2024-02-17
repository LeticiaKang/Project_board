package com.fastcampus.mvcboardproject.controller;

import com.fastcampus.mvcboardproject.domain.type.SearchType;
import com.fastcampus.mvcboardproject.dto.response.ArticleResponse;
import com.fastcampus.mvcboardproject.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.mvcboardproject.service.ArticleService;
import com.fastcampus.mvcboardproject.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
    만들어야 하는 api 목록
    /api/aricles
    /api/aricles/{article-id}
    /api/article/{article-id}/articleComments
 */
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService  articleService;
    private final PaginationService paginationService;

    @GetMapping
    // ModelMap은 컨트롤러가 뷰에 데이터를 전달하는 데 사용되는 스프링의 데이터 저장 및 검색 매커니즘
    public String articles(
            @RequestParam(required = false) SearchType searchType, // 검색어, 반드시 있어야 하는 것은 아님
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,  // 정확하고 빠른 소통을 위해 사이즈를 명시 / 최신순 정렬
            ModelMap map
    ) {
        // 게시글 데이터를 가져와서 dto로 변환시켜 article객체를 생성
        // 페이지네이션 바를 생성
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        // 데이터를 view로 보냄
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "articles/index"; // "articles/index"를 반환(컨트롤러가 렌더링할 뷰의 이름)
    }

    @GetMapping("/{articleId}")
    // 파라미터(게시글 번호)를 받아서 매개변수에 추가함
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());

        return "articles/detail";
    }

}

