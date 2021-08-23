package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
    import java.util.List;

//just Person Dto
public class NewPersonDto implements Comparable<NewPersonDto> {

    private Integer id;
    private String status;
    private List <SnpDto> snpList;

    private Integer location_id;
    private Location location;
    private String description;
    private String miscellany;
    private String rowColor;

    private Integer birthYear;
    private Integer deathYear;

    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<PositionDto> positionDtoList;
    private List<ItemConnectionDto> locationList;
    private List<ItemConnectionDto> personList;
    private List<ItemConnectionDto> isourceList;
    private List<ItemConnectionDto> eventList;
    private List<Movement> movementList;
//    private String photo;
//    private byte[] photo;

    public NewPersonDto() {
    }

    public NewPersonDto(Integer id, List<Movement> movementList, List<SnpDto> snpList, String description,
                        String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                        List<PositionDto> positionList, List<ItemConnectionDto> locationList,  List<ItemConnectionDto> personList,
                        List<ItemConnectionDto> isourceList, List<ItemConnectionDto> eventList, Integer bYear, Integer dYear, String status) {
        this.id = id;
        this.movementList = movementList;
        this.snpList = snpList;
        this.status = status;
        this.description = description;
        this.miscellany = miscellany;
        this.rowColor = rowColor;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.positionDtoList = positionList;
        this.locationList = locationList;
        this.isourceList = isourceList;
        this.eventList = eventList;
        this.personList = personList;
        this.birthYear = bYear;
        this.deathYear = dYear;
//        this.photo = photo;
    }

    public NewPersonDto(Person p) {
        this.id = p.getId();
        this.status = p.getStatus().getName();
        this.movementList = p.getMovementList();
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

        this.positionDtoList = new ArrayList<>();
        this.locationList = new ArrayList<>();
        this.personList = new ArrayList<>();
        this.snpList = new ArrayList<>();
        this.isourceList = new ArrayList<>();
        this.eventList = new ArrayList<>();


        PositionDto posDto;
        for (Position pos : p.getOccupation()) {
            posDto = new PositionDto();
            posDto.setOrgId(pos.getOrg().getId());
            posDto.setPosition(pos.getPosition());
            posDto.setComment(pos.getComment());

            this.positionDtoList.add(posDto);
        }

        ItemConnectionDto locationConnectionDto;
        for (PersonLocationConnection connection : p.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

        ItemConnectionDto isourceConnectionDto;
        for (PersonIsourceConnection connection : p.getIsourceConnections()) {
            isourceConnectionDto = new ItemConnectionDto();
            isourceConnectionDto.setItemId(connection.getIsource().getId());
            isourceConnectionDto.setConnection(connection.getConnection());
            isourceConnectionDto.setComment(connection.getComment());

            this.isourceList.add(isourceConnectionDto);
        }

        ItemConnectionDto eventConnectionDto;
        for (PersonEventConnection connection : p.getEventConnections()) {
            eventConnectionDto = new ItemConnectionDto();
            eventConnectionDto.setItemId(connection.getEvent().getId());
            eventConnectionDto.setConnection(connection.getConnection());
            eventConnectionDto.setComment(connection.getComment());

            this.eventList.add(eventConnectionDto);
        }

        ItemConnectionDto personConnectionDto;
        for (PersonPersonConnection connection : p.getPersonConnections()) {
            personConnectionDto = new ItemConnectionDto();
            personConnectionDto.setItemId(connection.getConnectedPerson().getId());
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.personList.add(personConnectionDto);
        }

        SnpDto snpDto;
        for (SurnameNamePatr snp : p.getSnpList()) {
            snpDto = new SnpDto();
            snpDto.setId(snp.getId());
            snpDto.setSurname(snp.getSurname());
            snpDto.setName(snp.getName());
            snpDto.setPatronymic(snp.getPatronymic());
            snpDto.setPriority(snp.getPriority());

            this.snpList.add(snpDto);
        }

//        if (p.getPhoto() != null) {
//            String encodedString = Base64.getEncoder().encodeToString(p.getPhoto());
//            this.photo = encodedString;
//        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SnpDto> getSnpList() {
        return snpList;
    }

    public void setSnpList(List<SnpDto> snpList) {
        this.snpList = snpList;
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

    public List<PositionDto> getPositionDtoList() { return positionDtoList;  }

    public void setPositionDtoList(List<PositionDto> positionDtoList) {
        this.positionDtoList = positionDtoList;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<ItemConnectionDto> getIsourceList() {
        return isourceList;
    }

    public void setIsourceList(List<ItemConnectionDto> isourceList) {
        this.isourceList = isourceList;
    }


    public List<ItemConnectionDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<ItemConnectionDto> eventList) {
        this.eventList = eventList;
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

        String surname = "", surname1 = "";

        for (int i=0 ; i<snpList.size(); i++)
            if (snpList.get(i).getPriority() == 1){
                surname = snpList.get(i).getSurname();
            }

        for (int i=0 ; i < obj.snpList.size(); i++)
            if (obj.snpList.get(i).getPriority() == 1){
                surname1 = obj.snpList.get(i).getSurname();
            }

        return surname.compareToIgnoreCase(surname1);
    }
}

