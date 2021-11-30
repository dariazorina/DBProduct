package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class OrgDtoForMainList implements Comparable<OrgDtoForMainList> {

    private Integer id;

    private String status;
    private OrgType type;  //String

    private String name;

    private String rowColor;

    private Integer foundationYear;
    private Integer closureYear;

    private List<String> hashtagList;
    //    private List<NameConnectionDto> orgList;
    private List<String> locationList;
    //    private List<ItemConnectionDto> personList;
    private List<Movement> movementList;

    public OrgDtoForMainList() {
    }

    public OrgDtoForMainList(Integer id, List<Movement> movementList, String name,
                             String rowColor, List<String> hashtagList,
                             List<NameConnectionDto> orgList, List<String> locationList, List<ItemConnectionDto> personList,
                             Integer fYear, Integer cYear, String status, OrgType type) {
        this.id = id;
        this.movementList = movementList;
        this.status = status;
        this.rowColor = rowColor;
        this.name = name;
        this.hashtagList = hashtagList;
//        this.orgList = orgList;
        this.locationList = locationList;
//        this.personList = personList;
        this.foundationYear = fYear;
        this.closureYear = cYear;
        this.type = type;
//        this.photo = photo;
    }

    public OrgDtoForMainList(Org org) {
        this.id = org.getId();
        this.status = org.getStatus().getName();
        this.movementList = org.getMovementList();
        this.rowColor = org.getRgbSelection();
        this.foundationYear = org.getFoundedYear();
        this.closureYear = org.getClosedYear();

        this.type = org.getOrgType();

        this.hashtagList = new ArrayList<>();
        for (OrgHashtag orgHashtag : org.getHashtagList()) {
            if (orgHashtag.getHashtag().equals(orgHashtag.getAssigned_hashtag()))
                this.hashtagList.add(orgHashtag.getHashtag().getContent());
        }

//        this.orgList = new ArrayList<>();
        this.locationList = new ArrayList<>();
//        this.personList = new ArrayList<>();

        NameConnectionDto orgConnectionDto;
        String dtoName = "";

//        for (OrgOrgConnection connection : org.getOrgConnections()) {
//            orgConnectionDto = new NameConnectionDto();
//
//            if (org.getNameList() != null) {
//                for (OrgName name : org.getNameList()) {
//                    if (name.getPriority() == 1) {
//                        dtoName += name.getName();
//
//                        if (name.getAbbr() != null && name.getAbbr().length() != 0) {
//                            dtoName += "/ " + name.getAbbr();
//                        }
//                    }
//                }
//            }
//
//            orgConnectionDto.setName(dtoName);
//            orgConnectionDto.setItemId(connection.getConnectedOrg().getId());
//            orgConnectionDto.setConnection(connection.getConnection());
//            orgConnectionDto.setComment(connection.getComment());
//
//            this.orgList.add(orgConnectionDto);
//            dtoName = "";
//        }

//        ItemConnectionDto locationConnectionDto;
//        for (OrgLocationConnection connection : org.getLocationConnections()) {
//            locationConnectionDto = new ItemConnectionDto();
//            locationConnectionDto.setItemId(connection.getLocation().getId());
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.locationList.add(locationConnectionDto);
//        }

        List<String> locationStringList = new ArrayList<>();
        for (OrgLocationConnection plC : org.getLocationConnections()) {
            locationStringList.add(plC.getLocation().getCountry());
        }
        this.setLocationList(locationStringList);

//        ItemConnectionDto personConnectionDto;
//        for (OrgPersonConnection connection : org.getPersonConnections()) {
//            personConnectionDto = new ItemConnectionDto();
//            personConnectionDto.setItemId(connection.getPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            this.personList.add(personConnectionDto);
//        }

        for (OrgName name : org.getNameList()) {
            if (name.getPriority() == 1) {
                this.name = name.getName();
            }

            if (name.getAbbr() != null && name.getAbbr().length() != 0) {
                this.name += "/ " + name.getAbbr();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public Integer getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(Integer foundationYear) {
        this.foundationYear = foundationYear;
    }

    public Integer getClosureYear() {
        return closureYear;
    }

    public void setClosureYear(Integer closureYear) {
        this.closureYear = closureYear;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

//    public List<NameConnectionDto> getOrgList() {
//        return orgList;
//    }
//
//    public void setOrgList(List<NameConnectionDto> orgList) {
//        this.orgList = orgList;
//    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        this.locationList = locationList;
    }

//    public List<ItemConnectionDto> getPersonList() {
//        return personList;
//    }
//
//    public void setPersonList(List<ItemConnectionDto> personList) {
//        this.personList = personList;
//    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    public OrgType getType() {
        return type;
    }

    public void setType(OrgType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return ((OrgDtoForMainList) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(OrgDtoForMainList obj) {

//        String name = "", name1 = "";
//        int i;

//        for (i = 0; i < nameList.size(); i++)
//            if (nameList.get(i).getPriority() == 1) {
//                name = nameList.get(i).getName();
//            }
//
//        for (i = 0; i < obj.nameList.size(); i++)
//            if (obj.nameList.get(i).getPriority() == 1) {
//                name1 = obj.nameList.get(i).getName();
//            }

        return name.compareToIgnoreCase(obj.name);
    }

}