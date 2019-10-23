package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.AuthorRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.Author;
import com.hellokoding.springboot.restful.model.HashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final HashTagRepository hashtagRepository;


    public List<Article> findAll() {
        List<Article> all = articleRepository.findAll();
        return all;
    }

    public void fillAuthorTable() {

        List<Article> all = articleRepository.findAll();

        //step1: create table author
        for (Article article : all) {
            String author = article.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1); //убираем { }
                String[] split = author.split(","); //разделяем по "," на массив строк

                for (String authorNameWithQuotes : split) {
                    String authorName = authorNameWithQuotes.substring(1, authorNameWithQuotes.length() - 1);//del "
                    Author authorByName = authorRepository.getAuthorByName(authorName); //ищем автора в БД
                    if (authorByName == null) {
                        Author s1 = new Author();
                        s1.setName(authorName);
                        authorRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenAuthorAndArticle() {

        List<Article> all = articleRepository.findAll();

        //step2: create connecting table
        for (Article article : all) {
            String author = article.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1);
                String[] split = author.split(",");
                List<Author> authors = new LinkedList<>();
                for (String s : split) {
                    String authorName = s.substring(1, s.length() - 1);
                    Author authorByName = authorRepository.getAuthorByName(authorName);
                    authors.add(authorByName);
                }
                article.setAuthorList(authors);
                articleRepository.save(article);
            }
        }
    }


    public void fillHashTagTable() {

        List<Article> all = articleRepository.findAll();

        //step1: create hashtag table
        for (Article article : all) {
            String hashtag = article.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1); //убираем { }
                String[] split = hashtag.split(","); //разделяем по "," на массив строк

                for (String hashTagWithSharp : split) {
                    String pureHashtag = hashTagWithSharp.substring(1, hashTagWithSharp.length()); //del #
                    HashTag hashTagByContent = hashtagRepository.getHashTagByContent(pureHashtag); //ищем хештег в БД
                    if (hashTagByContent == null) {
                        HashTag s1 = new HashTag();
                        s1.setContent(pureHashtag);
                        hashtagRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenHashTagAndArticle() {

        List<Article> all = articleRepository.findAll();

        //step2: create connecting table
        for (Article article : all) {
            String hashtag = article.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1);
                String[] split = hashtag.split(",");
                List<HashTag> hashtags = new LinkedList<>();
                for (String s : split) {
                    String pureHashtag = s.substring(1, s.length());
                    HashTag hashTagByContent = hashtagRepository.getHashTagByContent(pureHashtag);
                    hashtags.add(hashTagByContent);
                }
                article.setHashtagList(hashtags);
                articleRepository.save(article);
            }
        }
    }


    public Optional<Article> findById(Long id) {
        Optional<Article> byId = articleRepository.findById(Math.toIntExact(id));
        Article article = byId.get();
        return byId;
    }

    public Article save(Article stock) {
        return articleRepository.save(stock);
    }

    public void deleteById(Long id) {
        //productRespository.deleteById(id);
    }
}
