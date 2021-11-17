package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
public class ArticleDtoForMainList implements Comparable<ArticleDtoForMainList> {

    private Integer id;
    private List<Movement> movementList;
    private Language language;

    private Date date;
    private String title;
    private String status;
    private String titleRus;

    private String rowColor;
//    private List<ItemConnectionDto> personList;
    private List<ItemConnectionDto> locationList;
//    private List<ItemConnectionDto> orgList;
    private List<String> hashtagList;
    private String miscellany;

    private MaterialType mtype;

    public ArticleDtoForMainList(){
    }

    public ArticleDtoForMainList(Integer id, List<Movement> movementList, Language language, Date date, String title, String status,
                                 String titleRus, String rowColor, List<ItemConnectionDto> locationList,
                                 List<String> hashtagList, String miscellany, MaterialType mtype) {
        this.id = id;
        this.movementList = movementList;
        this.language = language;
        this.mtype = mtype;
        this.date = date;
        this.title = title;
        this.status = status;
        this.titleRus = titleRus;
        this.rowColor = rowColor;
//        this.personList = personList;
        this.locationList = locationList;
//        this.orgList = orgList;
        this.hashtagList = hashtagList;
        this.miscellany = miscellany;
    }

    public ArticleDtoForMainList(Article article) {
        this.id = article.getId();
        this.movementList = article.getMovementList();
        this.language = article.getLanguage();
        this.mtype = article.getMtype();
        this.date = article.getDate();
        this.title = article.getTitle();
        this.status = article.getStatus().getName();
        this.titleRus = article.getTitleRus();
        this.rowColor = article.getRgbSelection();

//        this.personList = new ArrayList<>();
        this.locationList = new ArrayList<>();
//        this.orgList = new ArrayList<>();

//        ItemConnectionDto personConnectionDto;
//        for (ArticlePersonConnection connection : article.getPersonConnections()) {
//            personConnectionDto = new ItemConnectionDto();
//            personConnectionDto.setItemId(connection.getPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            this.personList.add(personConnectionDto);
//        }

        ItemConnectionDto locationConnectionDto;
        for (ArticleLocationConnection connection : article.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

//        ItemConnectionDto orgConnectionDto;
//        for (ArticleOrgConnection connection : article.getOrgConnections()) {
//            orgConnectionDto = new ItemConnectionDto();
//            orgConnectionDto.setItemId(connection.getOrg().getId());
//            orgConnectionDto.setConnection(connection.getConnection());
//            orgConnectionDto.setComment(connection.getComment());
//
//            this.orgList.add(orgConnectionDto);
//        }


        this.hashtagList = new ArrayList<>();
        for (ArticleHashtag articleHashtag : article.getHashtagList()) {
            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
                this.hashtagList.add(articleHashtag.getHashtag().getContent());
            }
        }
        this.miscellany = article.getMiscellany();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitleRus() {
        return titleRus;
    }

    public void setTitleRus(String titleRus) {
        this.titleRus = titleRus;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

//    public List<ItemConnectionDto> getPersonList() {
//        return personList;
//    }

//    public void setPersonList(List<ItemConnectionDto> personList) {
//        this.personList = personList;
//    }

    public List<ItemConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<ItemConnectionDto> locationList) {
        this.locationList = locationList;
    }

//    public List<ItemConnectionDto> getOrgList() {
//        return orgList;
//    }

//    public void setOrgList(List<ItemConnectionDto> orgList) {
//        this.orgList = orgList;
//    }

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public String getMiscellany() {
        return miscellany;
    }

    public void setMiscellany(String miscellany) {
        this.miscellany = miscellany;
    }

    public MaterialType getMtype() {
        return mtype;
    }

    public void setMtype(MaterialType mtype) {
        this.mtype = mtype;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ArticleDtoForMainList) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(ArticleDtoForMainList obj) {
        return title.compareToIgnoreCase(obj.getTitle());
    }
}
