package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

//just Person Dto
public class NewPersonDto implements Comparable<NewPersonDto> {

    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private String surnameRus;
    private String nameRus;
    private String surnameEng;
    private String nameEng;

    private Integer country_id;
    private Country country;
    private String settlement;
    private String description;
    private String miscellany;

    private Integer birthYear;
    private Integer deathYear;

    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<PositionDto> testList;

    public NewPersonDto() {
    }

    public NewPersonDto(Integer id, String surname, String name, String patronymic, String surnameRus, String nameRus, String surnameEng, String nameEng, Integer country_id, String settlement, String description, String miscellany, List<UrlLink> linkList, List<String> hashtagList, List<PositionDto> testList, Integer bYear, Integer dYear) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.surnameRus = surnameRus;
        this.nameRus = nameRus;
        this.surnameEng = surnameEng;
        this.nameEng = nameEng;
        this.country_id = country_id;
        this.settlement = settlement;
        this.description = description;
        this.miscellany = miscellany;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.testList = testList;
        this.birthYear = bYear;
        this.deathYear = dYear;
    }

    public NewPersonDto(Person p) {
        this.id = p.getId();
        this.surname = p.getSurname();
        this.name = p.getName();
        this.patronymic = p.getPatronymic();
        this.surnameRus = p.getSurnameRus();
        this.nameRus = p.getNameRus();
        this.surnameEng = p.getSurnameEng();
        this.nameEng = p.getNameEng();
        this.country = p.getCountry();
        this.settlement = p.getSettlement();
        this.description = p.getDescription();
        this.miscellany = p.getMiscellany();
        this.linkList = p.getLinkList();
        this.birthYear = p.getBirthYear();
        this.deathYear = p.getDeathYear();

        this.hashtagList = new ArrayList<>();
        for (PersonHashtag pers: p.getHashtagList()) {
            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
                this.hashtagList.add(pers.getHashtag().getContent());
        }

        this.testList = new ArrayList<>();

        PositionDto posDto;
        for (Position pos : p.getOccupation()) {
            posDto = new PositionDto();
            posDto.setOrgId(pos.getOrg().getId());
            posDto.setPosition(pos.getPosition());

            this.testList.add(posDto);
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurnameRus() {
        return surnameRus;
    }

    public void setSurnameRus(String surnameRus) {
        this.surnameRus = surnameRus;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getSurnameEng() {
        return surnameEng;
    }

    public void setSurnameEng(String surnameEng) {
        this.surnameEng = surnameEng;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UrlLink> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<UrlLink> linkList) {
        this.linkList = linkList;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public List<PositionDto> getTestList() {
        return testList;
    }

    public void setTestList(List<PositionDto> testList) {
        this.testList = testList;
    }

    public String getMiscellany() {
        return miscellany;
    }

    public void setMiscellany(String miscellany) {
        this.miscellany = miscellany;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    @Override
    public boolean equals(Object obj) {
        return ((NewPersonDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(NewPersonDto obj) {
        return surname.compareToIgnoreCase(obj.getSurname());
    }
}

