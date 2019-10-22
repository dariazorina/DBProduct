package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author getAuthorByName(String name);
}
