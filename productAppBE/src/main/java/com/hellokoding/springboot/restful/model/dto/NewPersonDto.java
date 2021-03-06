package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//just Person Dto
public class NewPersonDto implements Comparable<NewPersonDto> {

    private Integer id;
    private Integer status;
    private String surname;
    private String name;
    private String patronymic;
    private String surnameRus;
    private String nameRus;
    private String surnameEng;
    private String nameEng;

    private Integer location_id;
    private Location location;
    private String description;
    private String miscellany;
    private String rowColor;

    private Integer birthYear;
    private Integer deathYear;

    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<PositionDto> testList; //todo rename
    private List<ItemConnectionDto> locationList;
    private List<ItemConnectionDto> personList;
    private List<Movement> movementList;
    private String photo;
//    private byte[] photo;

    public NewPersonDto() {
    }

    public NewPersonDto(Integer id, List<Movement> movementList, String surname, String name, String patronymic, String surnameRus,
                        String nameRus, String surnameEng, String nameEng, String description,
                        String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                        List<PositionDto> testList, List<ItemConnectionDto> locationList,  List<ItemConnectionDto> personList, Integer bYear, Integer dYear, String photo, Integer status) {
        this.id = id;
        this.movementList = movementList;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.surnameRus = surnameRus;
        this.nameRus = nameRus;
        this.surnameEng = surnameEng;
        this.nameEng = nameEng;
        this.status = status;
        this.description = description;
        this.miscellany = miscellany;
        this.rowColor = rowColor;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.testList = testList;
        this.locationList = locationList;
        this.personList = personList;
        this.birthYear = bYear;
        this.deathYear = dYear;
        this.photo = photo;
    }

    public NewPersonDto(Person p) {
        this.id = p.getId();
        this.movementList = p.getMovementList();
        this.surname = p.getSurname();
        this.name = p.getName();
        this.patronymic = p.getPatronymic();
        this.status = p.getStatus();
        this.surnameRus = p.getSurnameRus();
        this.nameRus = p.getNameRus();
        this.surnameEng = p.getSurnameEng();
        this.nameEng = p.getNameEng();
        this.description = p.getDescription();
        this.miscellany = p.getMiscellany();
        this.rowColor = p.getRgbSelection();
        this.linkList = p.getLinkList();
        this.birthYear = p.getBirthYear();
        this.deathYear = p.getDeathYear();

        this.hashtagList = new ArrayList<>();
        for (PersonHashtag pers: p.getHashtagList()) {
            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
                this.hashtagList.add(pers.getHashtag().getContent());
        }

        this.testList = new ArrayList<>();
        this.locationList = new ArrayList<>();
        this.personList = new ArrayList<>();

        PositionDto posDto;
        for (Position pos : p.getOccupation()) {
            posDto = new PositionDto();
            posDto.setOrgId(pos.getOrg().getId());
            posDto.setPosition(pos.getPosition());
            posDto.setComment(pos.getComment());

            this.testList.add(posDto);
        }

        ItemConnectionDto locationConnectionDto;
        for (PersonLocationConnection connection : p.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

        ItemConnectionDto personConnectionDto;
        for (PersonPersonConnection connection : p.getPersonConnections()) {
            personConnectionDto = new ItemConnectionDto();
            personConnectionDto.setItemId(connection.getConnectedPerson().getId());
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.personList.add(personConnectionDto);
        }

        if (p.getPhoto() != null) {
            String encodedString = Base64.getEncoder().encodeToString(p.getPhoto());
            this.photo = encodedString;
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

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public List<PositionDto> getTestList() { return testList;  }

    public void setTestList(List<PositionDto> testList) {
        this.testList = testList;
    }

    public List<ItemConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<ItemConnectionDto> locationList) {
        this.locationList = locationList;
    }

    public String getMiscellany() {
        return miscellany;
    }

    public void setMiscellany(String miscellany) {
        this.miscellany = miscellany;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<ItemConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<ItemConnectionDto> personList) {
        this.personList = personList;
    }
//    public byte[] getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(byte[] photo) {
//        this.photo = photo;
//    }


    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

