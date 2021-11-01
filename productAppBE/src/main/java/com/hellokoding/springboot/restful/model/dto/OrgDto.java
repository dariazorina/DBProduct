package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class OrgDto implements Comparable<OrgDto> {

    private Integer id;

    private String status;
    private OrgType type;  //String

    private List<OrgNameDto> nameList;

    private String description;
    private String miscellany;
    private String rowColor;

    private Integer foundationYear;
    private Integer closureYear;

    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<ItemConnectionDto> articleList;
    private List<NameConnectionDto> orgList;
    private List<ItemConnectionDto> locationList;
    private List<ItemConnectionDto> personList;
    private List<ItemConnectionDto> isourceList;
    private List<Movement> movementList;

    public OrgDto() {
    }

    public OrgDto(Integer id, List<Movement> movementList, List<OrgNameDto> nameList, String description,
                  String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                  List<NameConnectionDto> orgList, List<ItemConnectionDto> locationList, List<ItemConnectionDto> personList,
                  List<ItemConnectionDto> isourceList, List<ItemConnectionDto> articleList, Integer fYear, Integer cYear, String status, OrgType type) {
        this.id = id;
        this.movementList = movementList;
        this.status = status;
        this.description = description;
        this.miscellany = miscellany;
        this.rowColor = rowColor;
        this.nameList = nameList;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.orgList = orgList;
        this.articleList = articleList;
        this.locationList = locationList;
        this.isourceList = isourceList;
        this.personList = personList;
        this.foundationYear = fYear;
        this.closureYear = cYear;
        this.type = type;
//        this.photo = photo;
    }

    public OrgDto(Org org) {
        this.id = org.getId();
        this.status = org.getStatus().getName();
        this.movementList = org.getMovementList();
        this.description = org.getDescription();
        this.miscellany = org.getMiscellany();
        this.rowColor = org.getRgbSelection();
        this.linkList = org.getLinkList();
        this.foundationYear = org.getFoundedYear();
        this.closureYear = org.getClosedYear();

        this.type = org.getOrgType();

        this.hashtagList = new ArrayList<>();
        for (OrgHashtag orgHashtag : org.getHashtagList()) {
            if (orgHashtag.getHashtag().equals(orgHashtag.getAssigned_hashtag()))
                this.hashtagList.add(orgHashtag.getHashtag().getContent());
        }

        this.orgList = new ArrayList<>();
        this.locationList = new ArrayList<>();
        this.personList = new ArrayList<>();
        this.nameList = new ArrayList<>();
        this.isourceList = new ArrayList<>();
        this.articleList = new ArrayList<>();


        NameConnectionDto orgConnectionDto;
        String dtoName = "";

        for (OrgOrgConnection connection : org.getOrgConnections()) {
            orgConnectionDto = new NameConnectionDto();

            if (org.getNameList() != null) {
                for (OrgName name : org.getNameList()) {
                    if (name.getPriority() == 1) {
                        dtoName += name.getName();

                        if (name.getAbbr() != null) {
                            dtoName += "/ " + name.getAbbr();
                        }
                    }
                }
            }

            orgConnectionDto.setName(dtoName);
            orgConnectionDto.setItemId(connection.getConnectedOrg().getId());
            orgConnectionDto.setConnection(connection.getConnection());
            orgConnectionDto.setComment(connection.getComment());

            this.orgList.add(orgConnectionDto);
            dtoName = "";
        }

        ItemConnectionDto locationConnectionDto;
        for (OrgLocationConnection connection : org.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

        ItemConnectionDto isourceConnectionDto;
        for (OrgIsourceConnection connection : org.getIsourceConnections()) {
            isourceConnectionDto = new ItemConnectionDto();
            isourceConnectionDto.setItemId(connection.getIsource().getId());
            isourceConnectionDto.setConnection(connection.getConnection());
            isourceConnectionDto.setComment(connection.getComment());

            this.isourceList.add(isourceConnectionDto);
        }

        ItemConnectionDto articleConnectionDto;
        for (ArticleOrgConnection connection : org.getArticleConnections()) {
            articleConnectionDto = new ItemConnectionDto();
            articleConnectionDto.setItemId(connection.getArticle().getId());
            articleConnectionDto.setConnection(connection.getConnection());
            articleConnectionDto.setComment(connection.getComment());

            this.articleList.add(articleConnectionDto);
        }

        ItemConnectionDto personConnectionDto;
        for (OrgPersonConnection connection : org.getPersonConnections()) {
            personConnectionDto = new ItemConnectionDto();
            personConnectionDto.setItemId(connection.getPerson().getId());
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.personList.add(personConnectionDto);
        }

        OrgNameDto nameListDto;
        for (OrgName name : org.getNameList()) {
            nameListDto = new OrgNameDto();
            nameListDto.setId(name.getId());
            nameListDto.setName(name.getName());
            nameListDto.setAbbr(name.getAbbr());
            nameListDto.setPriority(name.getPriority());

            this.nameList.add(nameListDto);
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

    public List<OrgNameDto> getNameList() {
        return nameList;
    }

    public void setNameList(List<OrgNameDto> nameList) {
        this.nameList = nameList;
    }

    public List<ItemConnectionDto> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ItemConnectionDto> articleList) {
        this.articleList = articleList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<NameConnectionDto> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<NameConnectionDto> orgList) {
        this.orgList = orgList;
    }

    public List<ItemConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<ItemConnectionDto> locationList) {
        this.locationList = locationList;
    }

    public List<ItemConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<ItemConnectionDto> personList) {
        this.personList = personList;
    }

    public List<ItemConnectionDto> getIsourceList() {
        return isourceList;
    }

    public void setIsourceList(List<ItemConnectionDto> isourceList) {
        this.isourceList = isourceList;
    }

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
        return ((OrgDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(OrgDto obj) {

        String name = "", name1 = "";
        int i;

        for (i = 0; i < nameList.size(); i++)
            if (nameList.get(i).getPriority() == 1) {
                name = nameList.get(i).getName();
            }

        for (i = 0; i < obj.nameList.size(); i++)
            if (obj.nameList.get(i).getPriority() == 1) {
                name1 = obj.nameList.get(i).getName();
            }

        return name.compareToIgnoreCase(name1);
    }

}