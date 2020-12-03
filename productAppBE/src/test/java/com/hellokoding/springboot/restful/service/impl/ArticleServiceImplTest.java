package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    private Integer id;
//    private Movement movement;
//    private Language language;
//
//    private Date date;
//    private String title;
//    private Integer status;
//    private String titleRus;
//
//    private List<Person> authorList;
//    private String description;
//    private String url;
//    private List<String> hashtagList;
//    private String miscellany;

    @Test
    @Ignore
    public void testSave() {

        String title = "title";
        String titleRus = "titleRus";

        Movement movement = new Movement();
        movement.setId(1);
        movement.setCode("111");
        movement.setName("movementName");

        String description = "some description";

        Language language = new Language();
        language.setId(1);
        language.setCode("111");
        language.setName("languageName");

        Integer status = 0;
        Date date = new Date(1212121212121L);
        String url = "url";
        String miscellany = "miscellany";

        HashTag h1 = new HashTag();
        h1.setContent("тело_человека");
        h1.setMiscellany("тест1");
        h1.setParentId(0);
        h1.setId(1);

//        HashTag h2 = new HashTag();
//        h2.setContent("hash2");
//        h2.setMiscellany("MiscHash2");
//        h2.setParentId(0);
//        h2.setId(2);

        Article article = new Article();

        ArticleHashtag articleHashtag = new ArticleHashtag();
        articleHashtag.setHashtag(h1);
//        articleHashtag.setId(2);
        articleHashtag.setLevel(1);
        articleHashtag.setAssigned_hashtag(h1);
        articleHashtag.setArticle(article);

        List<ArticleHashtag> hashtagList = new ArrayList<>();
        List<String> hashtagStringList = new ArrayList<>();
        hashtagList.add(articleHashtag);
        hashtagStringList.add(h1.getContent());


        ArticleDto.ArticleDtoBuilder dtoBuilder = ArticleDto.builder();
        dtoBuilder.description(description);
        dtoBuilder.hashtagList(hashtagStringList); //(new ArrayList<>());
        dtoBuilder.title(title);
        dtoBuilder.titleRus(titleRus);
        dtoBuilder.movement(movement);
        dtoBuilder.language(language);
        dtoBuilder.date(date);
        dtoBuilder.status(status);
        dtoBuilder.url(url);
        dtoBuilder.miscellany(miscellany);
//        dtoBuilder.authorList(new ArrayList<>());

        article.setDescription(description);
        article.setHashtagList(hashtagList);
        article.setTitle(title);
        article.setTitleRus(titleRus);
        article.setMovement(movement);
        article.setLanguage(language);
        article.setDate(date);
        article.setStatus(status);
//        article.setUrl(url);
        article.setMiscellany(miscellany);
//        article.setAuthorList(new ArrayList<>());

        when(hashTagRepository.getHashTagByContent(h1.getContent())).thenReturn(h1);
        when(articleRepository.save(any())).then(returnsFirstArg());
        assertEquals(article.toString(), articleService.save(dtoBuilder.build()).toString());
    }
}
