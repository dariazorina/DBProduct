package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ArticleServiceImplTest {
    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private HashTagRepository hashTagRepository;

    @Autowired
    private ArticleServiceImpl articleService;

    @Test
    public void testSave() {
        ArticleDto.ArticleDtoBuilder dtoBuilder = ArticleDto.builder();
        dtoBuilder.description("some description");
        dtoBuilder.hashtagList(new ArrayList<>());

        Article article = new Article();
        article.setDescription("another description");
        article.setHashtagList(new ArrayList<>());

        when(articleRepository.save(any())).then(returnsFirstArg());
        assertEquals(article, articleService.save(dtoBuilder.build()));
    }
}
