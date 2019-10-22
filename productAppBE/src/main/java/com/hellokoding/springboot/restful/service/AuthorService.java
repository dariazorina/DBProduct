package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.AuthorRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

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
        //productRespository.deleteById(id);
    }

}
