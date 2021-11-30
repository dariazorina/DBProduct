package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

//just Person Dto
public class NewPersonDtoForMainList implements Comparable<NewPersonDtoForMainList> {

    private Integer id;
    private String status;
    private String snp;  //surn name patr with priority = 1

    private Integer location_id;
    private String rowColor;

    private Integer birthYear;
    private Integer deathYear;

    private List<String> hashtagList;
    private List<ItemConnectionDto> orgList;
    private List<String> locationList;
    private List<Movement> movementList;
//    private String photo;
//    private byte[] photo;

    public NewPersonDtoForMainList() {
    }

    public NewPersonDtoForMainList(Integer id, List<Movement> movementList, String snp,
                                   String rowColor, List<String> hashtagList,
                                   List<ItemConnectionDto> orgList, List<String> locationList,
                                   Integer bYear, Integer dYear, String status) {
        this.id = id;
        this.movementList = movementList;
        this.snp = snp;
        this.status = status;
        this.rowColor = rowColor;
        this.hashtagList = hashtagList;
        this.orgList = orgList;
        this.locationList = locationList;
        this.birthYear = bYear;
        this.deathYear = dYear;
//        this.photo = photo;
    }

    public NewPersonDtoForMainList(Person p) {
        this.id = p.getId();
        this.status = p.getStatus().getName();
        this.movementList = p.getMovementList();
        this.rowColor = p.getRgbSelection();
        this.birthYear = p.getBirthYear();
        this.deathYear = p.getDeathYear();

        this.hashtagList = new ArrayList<>();
        for (PersonHashtag pers: p.getHashtagList()) {
            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
                this.hashtagList.add(pers.getHashtag().getContent());
        }

        this.orgList = new ArrayList<>();
        this.locationList = new ArrayList<>();
      //  this.snpList = new ArrayList<>();

        ItemConnectionDto orgConnectionDto;
        for (OrgPersonConnection connection : p.getOrgConnections()) {
            orgConnectionDto = new ItemConnectionDto();
            orgConnectionDto.setItemId(connection.getOrg().getId());
            orgConnectionDto.setConnection(connection.getConnection());
            orgConnectionDto.setComment(connection.getComment());

            this.orgList.add(orgConnectionDto);
        }

        List<String> locationStringList = new ArrayList<>();
        for (PersonLocationConnection plC : p.getLocationConnections()) {
            locationStringList.add(plC.getLocation().getCountry());
        }
        this.setLocationList(locationStringList);


        for (SurnameNamePatr snp : p.getSnpList()) {
            if (snp.getPriority() == 1) {
                this.snp = snp.getSurname() + " " + snp.getName();
                if (snp.getPatronymic()!=null){
                    this.snp += " " + snp.getPatronymic();
                }
            }
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

    public String getSnp() {
        return snp;
    }

    public void setSnpList(String snp) {
        this.snp = snp;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public List<ItemConnectionDto> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<ItemConnectionDto> orgList) {
        this.orgList = orgList;
    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        this.locationList = locationList;
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
        return ((NewPersonDtoForMainList) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(NewPersonDtoForMainList obj) {

        String surname = "", surname1 = "";

//        for (int i=0 ; i<snpList.size(); i++)
//            if (snpList.get(i).getPriority() == 1){
//                surname = snpList.get(i).getSurname();
//            }
//
//        for (int i=0 ; i < obj.snpList.size(); i++)
//            if (obj.snpList.get(i).getPriority() == 1){
//                surname1 = obj.snpList.get(i).getSurname();
//            }


        return snp.compareToIgnoreCase(obj.getSnp());
    }
}

