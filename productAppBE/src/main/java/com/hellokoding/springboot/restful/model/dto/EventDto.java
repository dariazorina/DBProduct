package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class EventDto implements Comparable<EventDto> {

    private Integer id;

    private String status;

    private String title;
    private String type;

    private String description;
    private String miscellany;
    private String rowColor;

    private Date startDate;
    private Date endDate;

    private List<Tag> tagList;
    private List<UrlLink> linkList;
    private List<String> hashtagList;
    private List<NameConnectionDto> articleList;
    private List<NameConnectionDto> projectList;
    private List<NameConnectionDto> locationList;
    private List<NameConnectionDto> personList;
    private List<OneTypeConnectionDto> eventList;
    private List<NameConnectionDto> orgList;
    private List<Movement> movementList;

    public EventDto() {
    }

    public EventDto(Integer id, List<Movement> movementList, String title, String type, String description,
                    String miscellany, String rowColor, List<UrlLink> linkList, List<String> hashtagList,
                    List<NameConnectionDto> projectList, List<NameConnectionDto> locationList, List<NameConnectionDto> personList,
                    List<OneTypeConnectionDto> eventList, List<NameConnectionDto> orgList, List<Tag> tagList,
                    List<NameConnectionDto> articleList, Date eDate, Date sDate, String status) {
        this.id = id;
        this.movementList = movementList;
        this.status = status;
        this.description = description;
        this.miscellany = miscellany;
        this.rowColor = rowColor;
        this.title = title;
        this.type = type;
        this.linkList = linkList;
        this.hashtagList = hashtagList;
        this.orgList = orgList;
        this.projectList = projectList;
        this.articleList = articleList;
        this.locationList = locationList;
        this.eventList = eventList;
        this.personList = personList;
        this.startDate = sDate;
        this.endDate = eDate;
        this.tagList = tagList;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<NameConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<NameConnectionDto> personList) {
        this.personList = personList;
    }

    public List<OneTypeConnectionDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<OneTypeConnectionDto> eventList) {
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
        return ((EventDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(EventDto obj) {
        return this.title.compareToIgnoreCase(obj.getTitle());
    }
}