package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;
import com.hellokoding.springboot.restful.model.dto.NewPersonDto;
//import com.hellokoding.springboot.restful.model.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

//    List<NewPersonDto> findAll();
    List<NewPersonDto> findAll(Integer movement);

    Optional<NewPersonDto> findById(Integer id);
    List<IdContentDto> findByIds(List<Integer> idList);

    void deleteById(Integer id);
    Person save(NewPersonDto stock);

    List<IdContentDto> searchBySurname(String q, Integer mov);
    List<NewPersonDto> search(List<String> hash, List<String> author, List<String> org, List<String> location);
    List<NameConnectionDto> findByIdsAndSymmetrically(List<Integer> idList, Integer id);
}
