package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();

    Optional<Person> findById(Integer id);

    void deleteById(Integer id);

    List<PersonDto> search(String q);

    Person save(Person stock);
}
