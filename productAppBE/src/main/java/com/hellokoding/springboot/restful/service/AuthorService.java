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
public class AuthorService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(Math.toIntExact(id));
    }

    public Author save(Author stock) {
        return authorRepository.save(stock);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(Math.toIntExact(id));
    }

    public void fillAuthorTable() {   ////step1: create table author
        List<Article> all = articleRepository.findAll();

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

    public void initializeReferenceBetweenAuthorAndArticle() {  //step2: create connecting table
        List<Article> all = articleRepository.findAll();

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

}
