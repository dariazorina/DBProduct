package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language getLanguageByCode(String code);
}
