package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository <Person, Integer> {
    Person getPersonByName(String name);

    List<Person> findBySurnameStartsWithIgnoreCase(String surname);
    List<Person> findBySurnameEngStartsWithIgnoreCase(String surname);
    List<Person> findBySurnameRusStartsWithIgnoreCase(String surname);
 //   List<Person> findBySurnameStartsWithAndSurnameEngStarsWithAndSurnameRusStartsWithOrde

}
