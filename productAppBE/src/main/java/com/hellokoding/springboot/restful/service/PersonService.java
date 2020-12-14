package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.NewPersonDto;
import com.hellokoding.springboot.restful.model.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<NewPersonDto> findAll();
    Optional<NewPersonDto> findById(Integer id);
    List<NewPersonDto> findByIds(List<Integer> idList);
    void deleteById(Integer id);
    List<PersonDto> search(String q);
    Person save(NewPersonDto stock);
}
