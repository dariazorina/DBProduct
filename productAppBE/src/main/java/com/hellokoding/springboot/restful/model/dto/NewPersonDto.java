package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

//just Person Dto
public class NewPersonDto implements Comparable<NewPersonDto> {

    private Integer id;
    private String status;
    private List<SnpDto> snpList;

    private Integer location_id;
    private String description;
    private String miscellany;
    private String rowColor;

    private Integer birthYear;
    private Integer deathYear;

    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<NameConnectionDto> orgList;
    private List<NameConnectionDto> locationList;
    private List<NameConnectionDto> personList;
    private List<NameConnectionDto> projectList;
    private List<NameConnectionDto> articleList;
    private List<NameConnectionDto> eventList;
    private List<NameConnectionDto> isourceList;
    private List<Movement> movementList;
//    private String photo;
//    private byte[] photo;

    public NewPersonDto() {
    }

    public NewPersonDto(Integer id, List<Movement> movementList, List<SnpDto> snpList, String description,
                        String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                        List<NameConnectionDto> orgList, List<NameConnectionDto> articleList, List<NameConnectionDto> projectList,
                        List<NameConnectionDto> locationList, List<NameConnectionDto> personList, List<NameConnectionDto> eventList,
                        List<NameConnectionDto> isourceList, Integer bYear, Integer dYear, String status) {
        this.id = id;
        this.movementList = movementList;
        this.snpList = snpList;
        this.status = status;
        this.description = description;
        this.miscellany = miscellany;
        this.rowColor = rowColor;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.orgList = orgList;
        this.articleList = articleList;
        this.locationList = locationList;
        this.isourceList = isourceList;
        this.eventList = eventList;
        this.personList = personList;
        this.projectList = projectList;
        this.birthYear = bYear;
        this.deathYear = dYear;
//        this.photo = photo;
    }

    public NewPersonDto(Person p) {

        this.setId(p.getId());
        this.setStatus(p.getStatus().getName());
        this.setMovementList(p.getMovementList());
        this.setDescription(p.getDescription());
        this.setMiscellany(p.getMiscellany());
        this.setRowColor(p.getRgbSelection());
        this.setLinkList(p.getLinkList());
        this.setBirthYear(p.getBirthYear());
        this.setDeathYear(p.getDeathYear());

        this.setHashtagList(new ArrayList<>());
        for (PersonHashtag pers : p.getHashtagList()) {
            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
                this.getHashtagList().add(pers.getHashtag().getContent());
        }

        this.setOrgList(new ArrayList<>());
        this.setLocationList(new ArrayList<>());
        this.setPersonList(new ArrayList<>());
        this.setEventList(new ArrayList<>());
        this.setProjectList(new ArrayList<>());
        this.setArticleList(new ArrayList<>());
        this.setSnpList(new ArrayList<>());
        this.setIsourceList(new ArrayList<>());

        String dtoName;
        NameConnectionDto orgConnectionDto;
        for (OrgPersonConnection connection : p.getOrgConnections()) {
            orgConnectionDto = new NameConnectionDto();
            Org org = connection.getOrg();

            orgConnectionDto.setItemId(org.getId());

            dtoName = "";
            if (org.getNameList() != null) {
                for (OrgName name : org.getNameList()) {
                    if (name.getPriority() == 1) {
                        dtoName += name.getName();

                        if (name.getAbbr() != null && name.getAbbr().length() != 0) {
                            dtoName += "/ " + name.getAbbr();
                        }
                    }
                }
            }

            orgConnectionDto.setName(dtoName);
            orgConnectionDto.setConnection(connection.getConnection());
            orgConnectionDto.setComment(connection.getComment());

            this.getOrgList().add(orgConnectionDto);
        }

        NameConnectionDto projectConnectionDto;
        for (ProjectPersonConnection connection : p.getProjectConnections()) {
            projectConnectionDto = new NameConnectionDto();
            Project project = connection.getProject();

            projectConnectionDto.setItemId(project.getId());
            projectConnectionDto.setName(project.getMainTitle());
            projectConnectionDto.setConnection(connection.getConnection());
            projectConnectionDto.setComment(connection.getComment());

            this.getProjectList().add(projectConnectionDto);
        }

        NameConnectionDto eventConnectionDto;
        for (PersonEventConnection connection : p.getEventConnections()) {
            eventConnectionDto = new NameConnectionDto();
            Event event = connection.getEvent();

            eventConnectionDto.setItemId(event.getId());
            eventConnectionDto.setName(event.getTitle());
            eventConnectionDto.setConnection(connection.getConnection());
            eventConnectionDto.setComment(connection.getComment());

            this.getEventList().add(eventConnectionDto);
        }

        NameConnectionDto articleConnectionDto;
        for (ArticlePersonConnection connection : p.getArticleConnections()) {
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

        NameConnectionDto locationConnectionDto;
        for (PersonLocationConnection connection : p.getLocationConnections()) {
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

        NameConnectionDto isourceConnectionDto;  //todo
        for (PersonIsourceConnection connection : p.getIsourceConnections()) {
            isourceConnectionDto = new NameConnectionDto();
            isourceConnectionDto.setItemId(connection.getIsource().getId());
            isourceConnectionDto.setConnection(connection.getConnection());
            isourceConnectionDto.setComment(connection.getComment());

            this.getIsourceList().add(isourceConnectionDto);
        }

        SnpDto snpDto;
        for (SurnameNamePatr snp : p.getSnpList()) {
            snpDto = new SnpDto();
            snpDto.setId(snp.getId());
            snpDto.setSurname(snp.getSurname());
            snpDto.setName(snp.getName());
            snpDto.setPatronymic(snp.getPatronymic());
            snpDto.setPriority(snp.getPriority());

            this.getSnpList().add(snpDto);
        }
    }

//    public NewPersonDto(Person p) {
//        this.id = p.getId();
//        this.status = p.getStatus().getName();
//        this.movementList = p.getMovementList();
//        this.description = p.getDescription();
//        this.miscellany = p.getMiscellany();
//        this.rowColor = p.getRgbSelection();
//        this.linkList = p.getLinkList();
//        this.birthYear = p.getBirthYear();
//        this.deathYear = p.getDeathYear();
//
//        this.hashtagList = new ArrayList<>();
//        for (PersonHashtag pers: p.getHashtagList()) {
//            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
//                this.hashtagList.add(pers.getHashtag().getContent());
//        }
//
//        this.orgList = new ArrayList<>();
//        this.locationList = new ArrayList<>();
//        this.personList = new ArrayList<>();
//        this.articleList = new ArrayList<>();
//        this.snpList = new ArrayList<>();
//        this.isourceList = new ArrayList<>();
//
//
//        ItemConnectionDto orgConnectionDto;
//        for (OrgPersonConnection connection : p.getOrgConnections()) {
//            orgConnectionDto = new ItemConnectionDto();
//            orgConnectionDto.setItemId(connection.getOrg().getId());
//            orgConnectionDto.setConnection(connection.getConnection());
//            orgConnectionDto.setComment(connection.getComment());
//
//            this.orgList.add(orgConnectionDto);
//        }
//
//        ItemConnectionDto articleConnectionDto;
//        for (ArticlePersonConnection connection : p.getArticleConnections()) {
//            articleConnectionDto = new ItemConnectionDto();
//            articleConnectionDto.setItemId(connection.getArticle().getId());
//            articleConnectionDto.setConnection(connection.getConnection());
//            articleConnectionDto.setComment(connection.getComment());
//
//            this.articleList.add(articleConnectionDto);
//        }
//
//        ItemConnectionDto locationConnectionDto;
//        for (PersonLocationConnection connection : p.getLocationConnections()) {
//            locationConnectionDto = new ItemConnectionDto();
//            locationConnectionDto.setItemId(connection.getLocation().getId());
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.locationList.add(locationConnectionDto);
//        }
//
//        ItemConnectionDto isourceConnectionDto;
//        for (PersonIsourceConnection connection : p.getIsourceConnections()) {
//            isourceConnectionDto = new ItemConnectionDto();
//            isourceConnectionDto.setItemId(connection.getIsource().getId());
//            isourceConnectionDto.setConnection(connection.getConnection());
//            isourceConnectionDto.setComment(connection.getComment());
//
//            this.isourceList.add(isourceConnectionDto);
//        }
//
//        String dtoName = "";
//        NameConnectionDto personConnectionDto;
//        for (PersonPersonConnection connection : p.getPersonConnections()) {
//            personConnectionDto = new NameConnectionDto();
//
//            if (p.getSnpList() != null) {
//                for (SurnameNamePatr name : p.getSnpList()) {
//                    if (name.getPriority() == 1) {
//                        dtoName = name.getSurname();
//                        dtoName += " " + name.getName();
//                        if (name.getPatronymic() != null) {
//                            dtoName += " " + name.getPatronymic();
//                        }
//                    }
//                }
//            }
//
//            personConnectionDto.setName(dtoName);
//            personConnectionDto.setItemId(connection.getConnectedPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            this.personList.add(personConnectionDto);
//        }
//
//        SnpDto snpDto;
//        for (SurnameNamePatr snp : p.getSnpList()) {
//            snpDto = new SnpDto();
//            snpDto.setId(snp.getId());
//            snpDto.setSurname(snp.getSurname());
//            snpDto.setName(snp.getName());
//            snpDto.setPatronymic(snp.getPatronymic());
//            snpDto.setPriority(snp.getPriority());
//
//            this.snpList.add(snpDto);
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

    public List<NameConnectionDto> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<NameConnectionDto> orgList) {
        this.orgList = orgList;
    }

    public List<NameConnectionDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<NameConnectionDto> projectList) {
        this.projectList = projectList;
    }

    public List<NameConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<NameConnectionDto> locationList) {
        this.locationList = locationList;
    }

    public List<NameConnectionDto> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<NameConnectionDto> articleList) {
        this.articleList = articleList;
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

    public List<NameConnectionDto> getIsourceList() {
        return isourceList;
    }

    public void setIsourceList(List<NameConnectionDto> isourceList) {
        this.isourceList = isourceList;
    }

    public List<NameConnectionDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<NameConnectionDto> eventList) {
        this.eventList = eventList;
    }

    public List<NameConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<NameConnectionDto> personList) {
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

        for (int i = 0; i < snpList.size(); i++)
            if (snpList.get(i).getPriority() == 1) {
                surname = snpList.get(i).getSurname();
            }

        for (int i = 0; i < obj.snpList.size(); i++)
            if (obj.snpList.get(i).getPriority() == 1) {
                surname1 = obj.snpList.get(i).getSurname();
            }
        return surname.compareToIgnoreCase(surname1);
    }
}

