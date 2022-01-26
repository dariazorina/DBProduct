package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.Movement;

import java.util.Date;
import java.util.List;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class EventDtoForMainList implements Comparable<EventDtoForMainList> {

    private Integer id;

    private String status;
    private String title;
    private String type;

    private String rowColor;
    private Date startDate;

    private List<String> hashtagList;
    private List<String> orgList;
    private List<String> locationList;
    private List<String> personList;
    private List<Movement> movementList;

    public EventDtoForMainList() {
    }

    public EventDtoForMainList(Integer id, List<Movement> movementList, String title,
                               String rowColor, List<String> hashtagList, String type,
                               List<String> orgList, List<String> locationList, List<String> personList,
                               Date startDate, String status) {
        this.id = id;
        this.movementList = movementList;
        this.status = status;
        this.rowColor = rowColor;
        this.title = title;
        this.type = type;
        this.hashtagList = hashtagList;
        this.orgList = orgList;
        this.locationList = locationList;
        this.personList = personList;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        this.locationList = locationList;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public List<String> getPersonList() {
        return personList;
    }

    public void setPersonList(List<String> personList) {
        this.personList = personList;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    @Override
    public boolean equals(Object obj) {
        return ((EventDtoForMainList) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(EventDtoForMainList obj) {
        return title.compareToIgnoreCase(obj.title);
    }

}