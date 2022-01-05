package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;
import com.hellokoding.springboot.restful.model.dto.NewPersonDto;
import com.hellokoding.springboot.restful.model.dto.NewPersonDtoForMainList;
//import com.hellokoding.springboot.restful.model.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

//    List<NewPersonDto> findAll();
    List<NewPersonDtoForMainList> findAll(List<Integer> movement);

    Optional<NewPersonDto> findById(Integer id);
//    NewPersonDto findById(Integer id);

    List<IdContentDto> findByIds(List<Integer> idList);

    void deleteById(Integer id);
    Person save(NewPersonDto stock);
    Person saveColor(NewPersonDtoForMainList personDto);

    List<IdContentDto> searchBySurname(String q);
    List<NewPersonDtoForMainList> filter(List<String> hash, List<String> author, List<String> org, List<String> location, List<Integer> mov);
    List<NameConnectionDto> findByIdsAndSymmetrically(Integer id);
}
