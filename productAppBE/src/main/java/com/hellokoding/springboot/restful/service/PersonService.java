package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.*;
//import com.hellokoding.springboot.restful.model.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    //    List<NewPersonDto> findAll();

//    PagedDataDto findAll(List<Integer> mov, Integer page, Integer size);
//    List<NewPersonDtoForMainList> findAll(List<Integer> mov, Integer page, Integer size);

//    Integer getQuantityAllPersonsWithMovement(List<Integer> mov);

    Optional<NewPersonDto> findById(Integer id);
//    NewPersonDto findById(Integer id);

    List<IdContentDto> findByIds(List<Integer> idList);

    void deleteById(Integer id);

    Person save(NewPersonDto stock);

    Person saveColor(NewPersonDtoForMainList personDto);

    List<IdContentDto> searchBySurname(String q);

    PagedDataDto filter(List<String> hash, List<String> author, List<String> org, List<String> location, List<Integer> mov, Integer page, Integer size);

    List<NameConnectionDto> findByIdsAndSymmetrically(Integer id);
}
