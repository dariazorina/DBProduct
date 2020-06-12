package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.dao.UrlLinkRepository;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import com.hellokoding.springboot.restful.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final HashTagRepository hashTagRepository;

    @Override
    public List<ArticleDto> findAll() {
        List<ArticleDto> dtoAllArticleList = new ArrayList<>();
        List<Article> all = articleRepository.findAll();

        ArticleDto articleDto;
        for (Article article : all) {
            articleDto = new ArticleDto(article);
            dtoAllArticleList.add(articleDto);
        }
        return dtoAllArticleList;
    }

    @Override
    public Optional<ArticleDto> findById(Integer id) {
        Optional<Article> byId = articleRepository.findById(id);
        Optional<ArticleDto> byIdDto;

        byIdDto = Optional.of(new ArticleDto(byId.get()));
        return byIdDto;
    }

    @Override
    @Transactional
    public Article save(ArticleDto articleDto) {

        Article article;
        if (articleDto.getId() == null) {
            article = new Article();
        } else if (articleRepository.findById(articleDto.getId()).isPresent()) {
            article = articleRepository.findById(articleDto.getId()).get();
        } else
            return null;

        article.setTitle(articleDto.getTitle());
        article.setTitleRus(articleDto.getTitleRus());
        article.setMovement(articleDto.getMovement());
        article.setLanguage(articleDto.getLanguage());
        article.setDate(articleDto.getDate());
        article.setStatus(articleDto.getStatus());
        article.setUrl(articleDto.getUrl());
        article.setDescription(articleDto.getDescription());
        article.setMiscellany(articleDto.getMiscellany());
        article.setAuthorList(articleDto.getAuthorList());

        /////////////////////article-hashtag////////////////////////
        ArticleHashtag articleHashtag, previousArticleHashtag, previousPreviousArticleHashtag;
        HashTag hashTag, hashTagPrevious, hashTagPreviousPrevious;
        //List<ArticleHashtag> hashtagList = new ArrayList<>();

        if (article.getHashtagList() != null) {
            for (ArticleHashtag at : article.getHashtagList()) {
                at.setArticle(null);
            }
            article.getHashtagList().clear();
            articleRepository.flush();
        } else {
            article.setHashtagList(new ArrayList<>());
        }

        for (String hashtag_content : articleDto.getHashtagList()) {
            hashTag = hashTagRepository.getHashTagByContent(hashtag_content);
            articleHashtag = new ArticleHashtag();

            if (hashTag != null) { // hashtag was found in repo

                if (hashTag.getParentId() == 0) {    ///////////////////////hashtag level 1
                    articleHashtag.setHashtag(hashTag);
                    articleHashtag.setLevel(1);
                    articleHashtag.setAssigned_hashtag(hashTag);
                    articleHashtag.setArticle(article);

                    article.getHashtagList().add(articleHashtag);

                } else {  ///////////////////////hashtag level 2/3
                    hashTagPrevious = hashTagRepository.findById(hashTag.getParentId()).get();
                    previousArticleHashtag = new ArticleHashtag();

                    if (hashTagPrevious.getParentId() == 0) {   ///////////////////////hashtag level 2

                        previousArticleHashtag.setHashtag(hashTagPrevious);
                        previousArticleHashtag.setLevel(1);
                        previousArticleHashtag.setAssigned_hashtag(hashTag);
                        previousArticleHashtag.setArticle(article);

                        articleHashtag.setHashtag(hashTag);
                        articleHashtag.setLevel(2);
                        articleHashtag.setAssigned_hashtag(hashTag);
                        articleHashtag.setArticle(article);

                        article.getHashtagList().add(articleHashtag);
                        article.getHashtagList().add(previousArticleHashtag);

                    } else {   ///////////////////////hashtag level 3
                        hashTagPreviousPrevious = hashTagRepository.findById(hashTagPrevious.getParentId()).get();
                        previousPreviousArticleHashtag = new ArticleHashtag();

                        if (hashTagPreviousPrevious.getParentId() == 0) {

                            previousPreviousArticleHashtag.setHashtag(hashTagPreviousPrevious);
                            previousPreviousArticleHashtag.setLevel(1);
                            previousPreviousArticleHashtag.setAssigned_hashtag(hashTag);
                            previousPreviousArticleHashtag.setArticle(article);

                            previousArticleHashtag.setHashtag(hashTagPrevious);
                            previousArticleHashtag.setLevel(2);
                            previousArticleHashtag.setAssigned_hashtag(hashTag);
                            previousArticleHashtag.setArticle(article);

                            articleHashtag.setHashtag(hashTag);
                            articleHashtag.setLevel(3);
                            articleHashtag.setAssigned_hashtag(hashTag);
                            articleHashtag.setArticle(article);

                            article.getHashtagList().add(articleHashtag);
                            article.getHashtagList().add(previousArticleHashtag);
                            article.getHashtagList().add(previousPreviousArticleHashtag);
                        }
                    }//level 3
                } //level 2/3
            }
        }//for
        //article.setHashtagList(null);
        //article.setHashtagList(hashtagList);
        return articleRepository.save(article);
//        return article;
    }

    @Override
    public List<ArticleDto> search(String title, String hash, String author, String language, String description, List<Integer> status, String startDate, String endDate) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date frmtStartDate = format.parse(startDate);
        Date frmtEndDate = format.parse(endDate);

        List<Article> searchList = new ArrayList<>();
        List<ArticleDto> dtoSearchList = new ArrayList<>();

        if (status != null && status.size() > 0) {
            if (title != null && !title.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByTitleAndDate("%" + title + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByTitleAndStatus("%" + title + "%", status);

                } else {
                    searchList = articleRepository.findByTitleAndStatusAndDate("%" + title + "%", status, frmtStartDate, frmtEndDate);
                }


            } else if (hash != null && !hash.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByHashAndDate(hash + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByHashAndStatus(hash + "%", status);

                } else {
                    searchList = articleRepository.findByHashAndStatusAndDate(hash + "%", status, frmtStartDate, frmtEndDate);
                }


            } else if (author != null && !author.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByAuthorAndDate(author + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByAuthorAndStatus(author + "%", status);

                } else {
                    searchList = articleRepository.findByAuthorAndStatusAndDate(author + "%", status, frmtStartDate, frmtEndDate);
                }

            } else if (language != null && !language.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByLangAndDate(language + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByLangAndStatus(language + "%", status);

                } else {
                    searchList = articleRepository.findByLangAndStatusAndDate(language + "%", status, frmtStartDate, frmtEndDate);
                }

            } else if (description != null && !description.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByDescriptionAndDate("%" + description + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByDescriptionAndStatus("%" + description + "%", status);

                } else {
                    searchList = articleRepository.findByDescriptionAndStatusAndDate("%" + description + "%", status, frmtStartDate, frmtEndDate);
                }

            } else if (status.get(0) == -1) {
                searchList = articleRepository.findAllByDateBetween(frmtStartDate, frmtEndDate);
            } else if (status.get(0) == 3) {
                searchList = articleRepository.findAllByStatus(status);
            } else {
                searchList = articleRepository.findByDateAndStatus(status, frmtStartDate, frmtEndDate);
            }
        }

        ArticleDto dtoArticle;
        for (Article article : searchList) {
            dtoArticle = new ArticleDto(article);
            dtoSearchList.add(dtoArticle);
        }
        return dtoSearchList;
    }

    @Override
    public void deleteById(Integer id) {
        articleRepository.deleteById(id);
    }
}
