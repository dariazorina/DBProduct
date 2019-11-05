package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.dao.LanguageRepository;
import com.hellokoding.springboot.restful.model.Isource;
import com.hellokoding.springboot.restful.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LanguageService {

    private final IsourceRepository isourceRepository;
    private final LanguageRepository languageRepository;


    public List<Language> findAll() {
        List<Language> all = languageRepository.findAll();
        return all;
    }

    public Optional<Language> findById(Long id) {
        Optional<Language> byId = languageRepository.findById(Math.toIntExact(id));
        return byId;
    }

    public Language save(Language stock) {
        return languageRepository.save(stock);
    }

    public void deleteById(Long id) {
        languageRepository.deleteById(Math.toIntExact(id));
    }



    public void initializeReferenceBetweenLanguageAndIsource() {
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String language = isource.getLang();
            if (language != null) {
                language = language.substring(1, language.length() - 1); //убираем { }
                String[] split = language.split(","); //разделяем по "," на массив строк

                List<Language> langList = new LinkedList<>();
                for (String lan_code : split) {
                    Language movByCode = languageRepository.getLanguageByCode(lan_code);
                    langList.add(movByCode);
                }
                isource.setLangList(langList);
                isourceRepository.save(isource);
            }
        }
    }
}
