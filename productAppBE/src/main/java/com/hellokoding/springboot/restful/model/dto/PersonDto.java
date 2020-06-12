package com.hellokoding.springboot.restful.model.dto;



//for autocomplete in author search
public class PersonDto implements Comparable<PersonDto>{

    private Integer id;
    private String surname;

    public PersonDto(Integer id, String s) {
        this.id = id;
        this.surname = s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object obj) {
        return ((PersonDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(PersonDto obj) {
        return surname.compareToIgnoreCase(obj.getSurname());
    }
}
