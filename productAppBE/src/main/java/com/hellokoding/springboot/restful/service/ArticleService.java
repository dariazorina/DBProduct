package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.AuthorRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.Author;
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

    public List<Article> findAll() {
        List<Article> all = articleRepository.findAll();
        //step 0
        // create table author
        // crete connecting table


        //step1
 /*       for (Article article : all) {

            String author = article.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1);
                String[] split = author.split(",");


                for (String s : split) {
                    String authorName = s.substring(1, s.length() - 1);
                    Author authorByName = authorRepository.getAuthorByName(authorName);
//                    if (authorByName == null) {
//                        Author s1 = new Author();
//                        s1.setName(authorName);
//                        authorRepository.save(s1);
//                    }
                }
            }
*/
//        }


        //step2
//        for (Article article : all) {
//
//            String author = article.getAuthor();
//            if (author != null) {
//                author = author.substring(1, author.length() - 1);
//                String[] split = author.split(",");
//
//                List<Author> authors = new LinkedList<>();
//                for (String s : split) {
//                    String authorName = s.substring(1, s.length() - 1);
//                    Author authorByName = authorRepository.getAuthorByName(authorName);
//                    authors.add(authorByName);
//                }
//                article.setAuthors(authors);
//                articleRepository.save(article);
//            }
//        }

        return all;
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
