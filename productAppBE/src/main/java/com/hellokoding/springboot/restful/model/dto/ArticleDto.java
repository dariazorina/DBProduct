package com.hellokoding.springboot.restful.model.dto;

import com.hellokoding.springboot.restful.model.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
public class ArticleDto implements Comparable<ArticleDto> {

    private Integer id;
    private Movement movement;
    private Language language;

    private Date date;
    private String title;
    private Integer status;
    private String titleRus;

    private String description;
    private String url;
    private List<UrlLink> linkList;
    private List<ItemConnectionDto> personList;
    private List<ItemConnectionDto> locationList;
    private List<String> hashtagList;
    private String miscellany;

    private MaterialType mtype;

    public ArticleDto(Integer id, Movement movement, Language language, Date date, String title, Integer status,
                      String titleRus, String description,
                      String url, List<UrlLink> linkList, List<ItemConnectionDto> personList, List<ItemConnectionDto> locationList,
                      List<String> hashtagList, String miscellany, MaterialType mtype) {
        this.id = id;
        this.movement = movement;
        this.language = language;
        this.mtype = mtype;
        this.date = date;
        this.title = title;
        this.status = status;
        this.titleRus = titleRus;
        this.description = description;
        this.url = url;
        this.linkList = linkList;
        this.personList = personList;
        this.locationList = locationList;
        this.hashtagList = hashtagList;
        this.miscellany = miscellany;
    }

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.movement = article.getMovement();
        this.language = article.getLanguage();
        this.mtype = article.getMtype();
        this.date = article.getDate();
        this.title = article.getTitle();
        this.status = article.getStatus();
        this.titleRus = article.getTitleRus();
        this.description = article.getDescription();
        this.linkList = article.getLinkList();

        this.personList = new ArrayList<>();
        this.locationList = new ArrayList<>();

        ItemConnectionDto personConnectionDto;
        for (ArticlePersonConnection connection : article.getPersonConnections()) {
            personConnectionDto = new ItemConnectionDto();
            personConnectionDto.setItemId(connection.getPerson().getId());
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            this.personList.add(personConnectionDto);
        }

        ItemConnectionDto locationConnectionDto;
        for (ArticleLocationConnection connection : article.getLocationConnections()) {
            locationConnectionDto = new ItemConnectionDto();
            locationConnectionDto.setItemId(connection.getLocation().getId());
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            this.locationList.add(locationConnectionDto);
        }

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

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitleRus() {
        return titleRus;
    }

    public void setTitleRus(String titleRus) {
        this.titleRus = titleRus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<UrlLink> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<UrlLink> linkList) {
        this.linkList = linkList;
    }

    public List<ItemConnectionDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<ItemConnectionDto> personList) {
        this.personList = personList;
    }

    public List<ItemConnectionDto> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<ItemConnectionDto> locationList) {
        this.locationList = locationList;
    }

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
        return ((ArticleDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(ArticleDto obj) {
        return title.compareToIgnoreCase(obj.getTitle());
    }

}
