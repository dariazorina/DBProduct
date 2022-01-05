package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class ProjectDto implements Comparable<ProjectDto> {

    private Integer id;

    private String status;

    private String mainTitle;
    private String otherTitle;

    private String description;
    private String miscellany;
    private String rowColor;

    private Integer foundationYear;
    private Integer closureYear;

    private List<Tag> tagList;
    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<NameConnectionDto> articleList;
    private List<OneTypeConnectionDto> projectList;
    private List<NameConnectionDto> locationList;
    private List<NameConnectionDto> personList;
    private List<NameConnectionDto> eventList;
    private List<NameConnectionDto> orgList;
    private List<Movement> movementList;

    public ProjectDto() {
    }

    public ProjectDto(Integer id, List<Movement> movementList, String mainTitle, String otherTitle, String description,
                      String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                      List<OneTypeConnectionDto> projectList, List<NameConnectionDto> locationList, List<NameConnectionDto> personList,
                      List<NameConnectionDto> eventList, List<NameConnectionDto> orgList, List<Tag> tagList, List<NameConnectionDto> articleList, Integer fYear, Integer cYear, String status) {
        this.id = id;
        this.movementList = movementList;
        this.status = status;
        this.description = description;
        this.miscellany = miscellany;
        this.rowColor = rowColor;
        this.mainTitle = mainTitle;
        this.otherTitle = otherTitle;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.orgList = orgList;
        this.projectList = projectList;
        this.articleList = articleList;
        this.locationList = locationList;
        this.eventList = eventList;
        this.personList = personList;
        this.foundationYear = fYear;
        this.closureYear = cYear;
        this.tagList = tagList;
    }

    public ProjectDto(Project project) {

        this.setId(project.getId());
        this.setMainTitle(project.getMainTitle());
        this.setOtherTitle(project.getOtherTitle());
        this.setStatus(project.getStatus().getName());
        this.setMovementList(project.getMovementList());
        this.setDescription(project.getDescription());
        this.setMiscellany(project.getMiscellany());
        this.setRowColor(project.getRgbSelection());
        this.setLinkList(project.getLinkList());
        this.setFoundationYear(project.getFoundedYear());
        this.setClosureYear(project.getClosedYear());
        this.setTagList(project.getTagList());


        this.setHashtagList(new ArrayList<>());
        for (ProjectHashtag projectHashtag : project.getHashtagList()) {
            if (projectHashtag.getHashtag().equals(projectHashtag.getAssigned_hashtag()))
                this.getHashtagList().add(projectHashtag.getHashtag().getContent());
        }

        this.setOrgList(new ArrayList<>());
        this.setProjectList(new ArrayList<>());
        this.setLocationList(new ArrayList<>());
        this.setPersonList(new ArrayList<>());
//        this.eventList = new ArrayList<>();
        this.setArticleList(new ArrayList<>());


        NameConnectionDto projectConnectionDto;
        String dtoName = "";

//!!!!!!!!!!!!такой вариант не работает на серваке!!!!!!!!!!!!
//        NameConnectionDto locationConnectionDto;
//        for (ProjectLocationConnection connection : project.getLocationConnections()) {
//            locationConnectionDto = new NameConnectionDto();
//            Location location = connection.getLocation();
//            locationConnectionDto.setItemId(location.getId());
//
//            dtoName = "location error";
//            if (location.getCountry().length() > 0) {
//                dtoName = location.getCountry();
//            }
//
//            if (location.getRegion().length() > 0) {
//                dtoName += ", " + location.getRegion();
//            }
//
//            if (location.getCity().length() > 0) {
//                dtoName += ", " + location.getCity();
//            }
//
//            if (location.getAddress().length() > 0) {
//                dtoName += ", " + location.getAddress();
//            }
//
//            if (location.getPlacement().length() > 0) {
//                dtoName += ", " + location.getPlacement();
//            }
//
//            locationConnectionDto.setName(dtoName);
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.getLocationList().add(locationConnectionDto);
//        }


        NameConnectionDto locationConnectionDto;
        for (ProjectLocationConnection connection : project.getLocationConnections()) {
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


        NameConnectionDto articleConnectionDto;
        for (ProjectArticleConnection connection : project.getArticleConnections()) {
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
        for (ProjectPersonConnection connection : project.getPersonConnections()) {
            personConnectionDto = new NameConnectionDto();
            Person person = connection.getPerson();

            personConnectionDto.setItemId(person.getId());

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

            personConnectionDto.setName(dtoName);
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.getPersonList().add(personConnectionDto);
        }

        NameConnectionDto orgConnectionDto;
        for (ProjectOrgConnection connection : project.getOrgConnections()) {
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


    }
//    public ProjectDto(Project project) {
//        this.id = project.getId();
//        this.mainTitle = project.getMainTitle();
//        this.otherTitle = project.getOtherTitle();
//        this.status = project.getStatus().getName();
//        this.movementList = project.getMovementList();
//        this.description = project.getDescription();
//        this.miscellany = project.getMiscellany();
//        this.rowColor = project.getRgbSelection();
//        this.linkList = project.getLinkList();
//        this.foundationYear = project.getFoundedYear();
//        this.closureYear = project.getClosedYear();
//        this.tagList = project.getTagList();
//
//
//        this.hashtagList = new ArrayList<>();
//        for (ProjectHashtag projectHashtag : project.getHashtagList()) {
//            if (projectHashtag.getHashtag().equals(projectHashtag.getAssigned_hashtag()))
//                this.hashtagList.add(projectHashtag.getHashtag().getContent());
//        }
//
//        this.orgList = new ArrayList<>();
//        this.projectList = new ArrayList<>();
//        this.locationList = new ArrayList<>();
//        this.personList = new ArrayList<>();
////        this.eventList = new ArrayList<>();
//        this.articleList = new ArrayList<>();
//
//
//        NameConnectionDto projectConnectionDto;
//        String dtoName = "";
//
//        for (ProjectProjectConnection connection : project.getProjectConnections()) {
//            projectConnectionDto = new NameConnectionDto();
//
//            if (project.getMainTitle() != null) {
//                dtoName = project.getMainTitle();
//
//                if (project.getOtherTitle() != null && project.getOtherTitle().length() != 0) {
//                    dtoName += "/ " + project.getOtherTitle();
//                }
//            }
//
//            projectConnectionDto.setName(dtoName);
//            projectConnectionDto.setItemId(connection.getConnectedProject().getId());
//            projectConnectionDto.setConnection(connection.getConnection());
//            projectConnectionDto.setComment(connection.getComment());
//
//            this.projectList.add(projectConnectionDto);
//            dtoName = "";
//        }
//
//        ItemConnectionDto locationConnectionDto;
//        for (ProjectLocationConnection connection : project.getLocationConnections()) {
//            locationConnectionDto = new ItemConnectionDto();
//            locationConnectionDto.setItemId(connection.getLocation().getId());
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.locationList.add(locationConnectionDto);
//        }
//
//        ItemConnectionDto articleConnectionDto;
//        for (ProjectArticleConnection connection : project.getArticleConnections()) {
//            articleConnectionDto = new ItemConnectionDto();
//            articleConnectionDto.setItemId(connection.getArticle().getId());
//            articleConnectionDto.setConnection(connection.getConnection());
//            articleConnectionDto.setComment(connection.getComment());
//
//            this.articleList.add(articleConnectionDto);
//        }
//
//        ItemConnectionDto personConnectionDto;
//        for (ProjectPersonConnection connection : project.getPersonConnections()) {
//            personConnectionDto = new ItemConnectionDto();
//            personConnectionDto.setItemId(connection.getPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            this.personList.add(personConnectionDto);
//        }
//
//        ItemConnectionDto orgConnectionDto;
//        for (ProjectOrgConnection connection : project.getOrgConnections()) {
//            orgConnectionDto = new ItemConnectionDto();
//            orgConnectionDto.setItemId(connection.getOrg().getId());
//            orgConnectionDto.setConnection(connection.getConnection());
//            orgConnectionDto.setComment(connection.getComment());
//
//            this.orgList.add(orgConnectionDto);
//        }
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

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getOtherTitle() {
        return otherTitle;
    }

    public void setOtherTitle(String otherTitle) {
        this.otherTitle = otherTitle;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
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

    public List<NameConnectionDto> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<NameConnectionDto> articleList) {
        this.articleList = articleList;
    }

    public List<OneTypeConnectionDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<OneTypeConnectionDto> projectList) {
        this.projectList = projectList;
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

    public List<NameConnectionDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<NameConnectionDto> eventList) {
        this.eventList = eventList;
    }

    public List<NameConnectionDto> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<NameConnectionDto> orgList) {
        this.orgList = orgList;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ProjectDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(ProjectDto obj) {
        return this.mainTitle.compareToIgnoreCase(obj.getMainTitle());
    }
}