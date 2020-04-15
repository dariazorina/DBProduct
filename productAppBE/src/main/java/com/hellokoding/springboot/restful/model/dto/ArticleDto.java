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

    private List<Person> authorList;
    private String description;
    private String url;
    private List<String> hashtagList;
    private String miscellany;

    public ArticleDto(Integer id, Movement movement, Language language, Date date, String title, Integer status, String titleRus, List<Person> authorList, String description, String url, List<String> hashtagList, String miscellany) {
        this.id = id;
        this.movement = movement;
        this.language = language;
        this.date = date;
        this.title = title;
        this.status = status;
        this.titleRus = titleRus;
        this.authorList = authorList;
        this.description = description;
        this.url = url;
        this.hashtagList = hashtagList;
        this.miscellany = miscellany;
    }

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.movement = article.getMovement();
        this.language = article.getLanguage();
        this.date = article.getDate();
        this.title = article.getTitle();
        this.status = article.getStatus();
        this.titleRus = article.getTitleRus();
        this.authorList = article.getAuthorList();
        this.description = article.getDescription();
        this.url = article.getUrl();
        this.miscellany = article.getMiscellany();

        this.hashtagList = new ArrayList<>();
        for (ArticleHashtag articleHashtag : article.getHashtagList()) {
            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag()))
                this.hashtagList.add(articleHashtag.getHashtag().getContent());
        }
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

    public List<Person> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Person> authorList) {
        this.authorList = authorList;
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
