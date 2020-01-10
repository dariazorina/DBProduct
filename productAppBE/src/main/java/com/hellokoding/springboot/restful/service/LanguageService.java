package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.dao.LanguageRepository;
import com.hellokoding.springboot.restful.model.Isource;
import com.hellokoding.springboot.restful.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public interface LanguageService {

    public List<Language> findAll();

    public Optional<Language> findById(Integer id);

    public Language save(Language stock)  throws UniqueConstraintViolationException;;

    public void deleteById(Integer id);

}
