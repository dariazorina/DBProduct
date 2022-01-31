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
    private List<NameConnectionDto> articleList;
    private List<OneTypeConnectionDto> orgList;
    private List<NameConnectionDto> locationList;
    private List<NameConnectionDto> personList;
    private List<NameConnectionDto> projectList;
    private List<NameConnectionDto> eventList;
    private List<NameConnectionDto> isourceList;
    private List<Movement> movementList;

    public OrgDto() {
    }

    public OrgDto(Integer id, List<Movement> movementList, List<OrgNameDto> nameList, String description,
                  String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                  List<OneTypeConnectionDto> orgList, List<NameConnectionDto> locationList, List<NameConnectionDto> projectList, List<NameConnectionDto> personList,
                  List<NameConnectionDto> isourceList, List<NameConnectionDto> articleList, List<NameConnectionDto> eventList, Integer fYear, Integer cYear, String status, OrgType type) {
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
        this.eventList = eventList;
        this.articleList = articleList;
        this.locationList = locationList;
        this.isourceList = isourceList;
        this.personList = personList;
        this.projectList = projectList;
        this.foundationYear = fYear;
        this.closureYear = cYear;
        this.type = type;
//        this.photo = photo;
    }

    public OrgDto(Org org) {

        this.setId(org.getId());
        this.setStatus(org.getStatus().getName());
        this.setMovementList(org.getMovementList());
        this.setDescription(org.getDescription());
        this.setMiscellany(org.getMiscellany());
        this.setRowColor(org.getRgbSelection());
        this.setLinkList(org.getLinkList());
        this.setFoundationYear(org.getFoundedYear());
        this.setClosureYear(org.getClosedYear());

        this.setType(org.getOrgType());

        this.setHashtagList(new ArrayList<>());
        for (OrgHashtag orgHashtag : org.getHashtagList()) {
            if (orgHashtag.getHashtag().equals(orgHashtag.getAssigned_hashtag()))
                this.getHashtagList().add(orgHashtag.getHashtag().getContent());
        }

        this.setOrgList(new ArrayList<>());
        this.setLocationList(new ArrayList<>());
        this.setPersonList(new ArrayList<>());
        this.setProjectList(new ArrayList<>());
        this.setEventList(new ArrayList<>());
        this.setNameList(new ArrayList<>());
        this.setIsourceList(new ArrayList<>());
        this.setArticleList(new ArrayList<>());


        String dtoName = "";

        NameConnectionDto locationConnectionDto;
        for (OrgLocationConnection connection : org.getLocationConnections()) {
            locationConnectionDto = new NameConnectionDto();

            Location location = connection.getLocation();
            locationConnectionDto.setItemId(location.getId());

            dtoName = location.getCountry();

            if (location.getRegion() != null && location.getRegion().length() > 0) {
                dtoName += ", " + location.getRegion();
            }

            if (location.getCity() != null && location.getCity().length() > 0) {
                dtoName += ", " + location.getCity();
            }

            if (location.getAddress() != null && location.getAddress().length() > 0) {
                dtoName += ", " + location.getAddress();
            }

            if (location.getPlacement() != null && location.getPlacement().length() > 0) {
                dtoName += ", " + location.getPlacement();
            }

            locationConnectionDto.setName(dtoName);
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.getLocationList().add(locationConnectionDto);
        }

//        NameConnectionDto isourceConnectionDto;
//        for (OrgIsourceConnection connection : org.getIsourceConnections()) {
//            isourceConnectionDto = new NameConnectionDto();
//            isourceConnectionDto.setItemId(connection.getIsource().getId());
//            isourceConnectionDto.setConnection(connection.getConnection());
//            isourceConnectionDto.setComment(connection.getComment());
//
//            this.getIsourceList().add(isourceConnectionDto);
//        }

        NameConnectionDto articleConnectionDto;
        for (ArticleOrgConnection connection : org.getArticleConnections()) {
            articleConnectionDto = new NameConnectionDto();
            Article article = connection.getArticle();
            articleConnectionDto.setItemId(article.getId());

            dtoName = article.getTitleRus();
            if (article.getTitle() != null && article.getTitle().length() > 0) {
                dtoName += "/ " + article.getTitle();
            }

            articleConnectionDto.setName(dtoName);
            articleConnectionDto.setConnection(connection.getConnection());
            articleConnectionDto.setComment(connection.getComment());

            this.getArticleList().add(articleConnectionDto);
        }

        NameConnectionDto personConnectionDto;
        for (OrgPersonConnection connection : org.getPersonConnections()) {
            personConnectionDto = new NameConnectionDto();
            Person person = connection.getPerson();

            if (person.getSnpList() != null) {
                for (SurnameNamePatr name : person.getSnpList()) {
                    if (name.getPriority() == 1) {
                        dtoName = name.getSurname();
                        dtoName += " " + name.getName();
                        if (name.getPatronymic() != null) {
                            dtoName += " " + name.getPatronymic();
                        }
                    }
                }
            }

            personConnectionDto.setItemId(person.getId());
            personConnectionDto.setName(dtoName);
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.getPersonList().add(personConnectionDto);
        }

        NameConnectionDto projectConnectionDto;
        for (ProjectOrgConnection connection : org.getProjectConnections()) {
            projectConnectionDto = new NameConnectionDto();
            Project project = connection.getProject();

            projectConnectionDto.setItemId(project.getId());
            projectConnectionDto.setName(project.getMainTitle());
            projectConnectionDto.setConnection(connection.getConnection());
            projectConnectionDto.setComment(connection.getComment());

            this.getProjectList().add(projectConnectionDto);
        }

        NameConnectionDto eventConnectionDto;
        for (OrgEventConnection connection : org.getEventConnections()) {
            eventConnectionDto = new NameConnectionDto();
            Event event = connection.getEvent();

            eventConnectionDto.setItemId(event.getId());
            eventConnectionDto.setName(event.getTitle());
            eventConnectionDto.setConnection(connection.getConnection());
            eventConnectionDto.setComment(connection.getComment());

            this.getEventList().add(eventConnectionDto);
        }

        OrgNameDto nameListDto;
        for (OrgName name : org.getNameList()) {
            nameListDto = new OrgNameDto();
            nameListDto.setId(name.getId());
            nameListDto.setName(name.getName());
            nameListDto.setAbbr(name.getAbbr());
            nameListDto.setPriority(name.getPriority());

            this.getNameList().add(nameListDto);
        }
    }

//    public OrgDto(Org org) {
//        this.id = org.getId();
//        this.status = org.getStatus().getName();
//        this.movementList = org.getMovementList();
//        this.description = org.getDescription();
//        this.miscellany = org.getMiscellany();
//        this.rowColor = org.getRgbSelection();
//        this.linkList = org.getLinkList();
//        this.foundationYear = org.getFoundedYear();
//        this.closureYear = org.getClosedYear();
//
//        this.type = org.getOrgType();
//
//        this.hashtagList = new ArrayList<>();
//        for (OrgHashtag orgHashtag : org.getHashtagList()) {
//            if (orgHashtag.getHashtag().equals(orgHashtag.getAssigned_hashtag()))
//                this.hashtagList.add(orgHashtag.getHashtag().getContent());
//        }
//
//        this.orgList = new ArrayList<>();
//        this.locationList = new ArrayList<>();
//        this.personList = new ArrayList<>();
//        this.nameList = new ArrayList<>();
//        this.isourceList = new ArrayList<>();
//        this.articleList = new ArrayList<>();
//
//
//        NameConnectionDto orgConnectionDto;
//        String dtoName = "";
//
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
//
//        NameConnectionDto locationConnectionDto;
//        for (OrgLocationConnection connection : org.getLocationConnections()) {
//            locationConnectionDto = new NameConnectionDto();
//            locationConnectionDto.setItemId(connection.getLocation().getId());
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.locationList.add(locationConnectionDto);
//        }
//
//        NameConnectionDto isourceConnectionDto;
//        for (OrgIsourceConnection connection : org.getIsourceConnections()) {
//            isourceConnectionDto = new NameConnectionDto();
//            isourceConnectionDto.setItemId(connection.getIsource().getId());
//            isourceConnectionDto.setConnection(connection.getConnection());
//            isourceConnectionDto.setComment(connection.getComment());
//
//            this.isourceList.add(isourceConnectionDto);
//        }
//
//        NameConnectionDto articleConnectionDto;
//        for (ArticleOrgConnection connection : org.getArticleConnections()) {
//            articleConnectionDto = new NameConnectionDto();
//            articleConnectionDto.setItemId(connection.getArticle().getId());
//            articleConnectionDto.setConnection(connection.getConnection());
//            articleConnectionDto.setComment(connection.getComment());
//
//            this.articleList.add(articleConnectionDto);
//        }
//
//        NameConnectionDto personConnectionDto;
//        for (OrgPersonConnection connection : org.getPersonConnections()) {
//            personConnectionDto = new NameConnectionDto();
//            personConnectionDto.setItemId(connection.getPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            this.personList.add(personConnectionDto);
//        }
//
//        OrgNameDto nameListDto;
//        for (OrgName name : org.getNameList()) {
//            nameListDto = new OrgNameDto();
//            nameListDto.setId(name.getId());
//            nameListDto.setName(name.getName());
//            nameListDto.setAbbr(name.getAbbr());
//            nameListDto.setPriority(name.getPriority());
//
//            this.nameList.add(nameListDto);
//        }
//
////        if (p.getPhoto() != null) {
////            String encodedString = Base64.getEncoder().encodeToString(p.getPhoto());
////            this.photo = encodedString;
////        }
//    }

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

    public List<NameConnectionDto> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<NameConnectionDto> articleList) {
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

    public List<NameConnectionDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<NameConnectionDto> eventList) {
        this.eventList = eventList;
    }

    public List<NameConnectionDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<NameConnectionDto> projectList) {
        this.projectList = projectList;
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

    public List<OneTypeConnectionDto> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<OneTypeConnectionDto> orgList) {
        this.orgList = orgList;
    }

    public List<NameConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<NameConnectionDto> locationList) {
        this.locationList = locationList;
    }

    public List<NameConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<NameConnectionDto> personList) {
        this.personList = personList;
    }

    public List<NameConnectionDto> getIsourceList() {
        return isourceList;
    }

    public void setIsourceList(List<NameConnectionDto> isourceList) {
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