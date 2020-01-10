package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.dao.LanguageRepository;
import com.hellokoding.springboot.restful.model.Language;
import com.hellokoding.springboot.restful.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class LanguageServiceImpl implements LanguageService {

    private final IsourceRepository isourceRepository;
    private final LanguageRepository languageRepository;


    public List<Language> findAll() {
        List<Language> all = languageRepository.findAll();
        return all;
    }

    public Optional<Language> findById(Integer id) {
        Optional<Language> byId = languageRepository.findById(id);
        return byId;
    }

    @Override
    @Transactional(noRollbackFor = UniqueConstraintViolationException.class)
    public Language save(Language language) throws UniqueConstraintViolationException {
        try {
            return languageRepository.save(language);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueConstraintViolationException();
        }
    }

    public void deleteById(Integer id) {
        languageRepository.deleteById(id);
    }



/*    public void initializeReferenceBetweenLanguageAndIsource() {
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
    }*/
}
