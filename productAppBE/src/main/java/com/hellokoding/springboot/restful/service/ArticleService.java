package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.HashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final HashTagRepository hashTagRepository;

    public List<Article> findAll() {
        List<Article> all = articleRepository.findAll();
        return all;
    }

    public Optional<Article> findById(Integer id) {
        Optional<Article> byId = articleRepository.findById(id);
        Article article = byId.get();
        return byId;
    }

    public Article save(Article stock) {

        HashTag hashTagByContent;
        HashTag hashTagWithID;
        List<HashTag> hashTagList = stock.getHashtagList();
        List<HashTag> hashTagListWithID = new ArrayList<>();

        for (HashTag hashtag : hashTagList) {
            hashTagByContent = hashTagRepository.getHashTagByContent(hashtag.getContent()); //ищем хештег в БД
            if (hashTagByContent == null) {
                hashTagRepository.save(hashtag);

                hashTagWithID = hashTagRepository.getHashTagByContent(hashtag.getContent());
                hashTagListWithID.add(hashTagWithID);

            } else {
                System.out.println(",t,t,t,,t");
                hashTagListWithID.add(hashTagByContent);
            }
        }

        stock.setHashtagList(hashTagListWithID);
        return articleRepository.save(stock);
    }

    public void deleteById(Integer id) {
        articleRepository.deleteById(id);
    }
}
