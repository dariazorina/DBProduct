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
    private List<String> personList;
    private List<String> projectList;
    private List<String> locationList;
    private List<String> orgList;
    private List<String> hashtagList;
    private String miscellany;

    private MaterialType mtype;

    public ArticleDtoForMainList() {
    }

    public ArticleDtoForMainList(Integer id, List<Movement> movementList, Language language, Date date, String title, String status,
                                 String titleRus, String rowColor, List<String> locationList, List<String> projectList, List<String> orgList, List<String> personList,
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
        this.personList = personList;
        this.projectList = projectList;
        this.locationList = locationList;
        this.orgList = orgList;
        this.hashtagList = hashtagList;
        this.miscellany = miscellany;
    }

//    public ArticleDtoForMainList(Article article) {
//        this.id = article.getId();
//        this.movementList = article.getMovementList();
//        this.language = article.getLanguage();
//        this.mtype = article.getMtype();
//        this.date = article.getDate();
//        this.title = article.getTitle();
//        this.status = article.getStatus().getName();
//        this.titleRus = article.getTitleRus();
//        this.rowColor = article.getRgbSelection();
//
//        this.personList = new ArrayList<>();
//        this.locationList = new ArrayList<>();
//        this.orgList = new ArrayList<>();
//
////        ItemConnectionDto personConnectionDto;
////        for (ArticlePersonConnection connection : article.getPersonConnections()) {
////            personConnectionDto = new ItemConnectionDto();
////            personConnectionDto.setItemId(connection.getPerson().getId());
////            personConnectionDto.setConnection(connection.getConnection());
////            personConnectionDto.setComment(connection.getComment());
////
////            this.personList.add(personConnectionDto);
////        }
//
////        ItemConnectionDto locationConnectionDto;
////        for (ArticleLocationConnection connection : article.getLocationConnections()) {
////            locationConnectionDto = new ItemConnectionDto();
////            locationConnectionDto.setItemId(connection.getLocation().getId());
////            locationConnectionDto.setConnection(connection.getConnection());
////            locationConnectionDto.setComment(connection.getComment());
////
////            this.locationList.add(locationConnectionDto);
////        }
//
//        List<String> locationStringList = new ArrayList<>();
//        for (ArticleLocationConnection alC : article.getLocationConnections()) {
//            locationStringList.add(alC.getLocation().getCountry());
//        }
//        this.setLocationList(locationStringList);
//
//        List<String> personStringList = new ArrayList<>();
//        for (ArticlePersonConnection aPC : article.getPersonConnections()) {
//            List<SurnameNamePatr> snpList = aPC.getPerson().getSnpList();
//            String snp = "error";
//            for (SurnameNamePatr nameEl : snpList) {
//                if (nameEl.getPriority() == 1) {
//                    snp = nameEl.getSurname();
//                    if (nameEl.getName().length() > 0) {
//                        snp += " " + nameEl.getName();
//                    }
//
//                    break;
//                }
//            }
//            if (aPC.getConnection().length() > 0) {
//                snp += ", " + aPC.getConnection();
//            }
//            personStringList.add(snp);
//        }
//        this.setPersonList(personStringList);
//
//        List<String> orgStringList = new ArrayList<>();
//        for (ArticleOrgConnection aOC : article.getOrgConnections()) {
//            List<OrgName> nameList = aOC.getOrg().getNameList();
//            String name = "error";
//            for (OrgName nameEl : nameList) {
//                if (nameEl.getPriority() == 1) {
//                    name = nameEl.getName();
//                    if (nameEl.getAbbr().length() > 0) {
//                        name += ", " + nameEl.getAbbr();
//                    }
//                    break;
//                }
//            }
//            if (aOC.getConnection().length() > 0) {
//                name += ", " + aOC.getConnection();
//            }
//            orgStringList.add(name);
//        }
//        this.setOrgList(orgStringList);
//
////        ItemConnectionDto orgConnectionDto;
////        for (ArticleOrgConnection connection : article.getOrgConnections()) {
////            orgConnectionDto = new ItemConnectionDto();
////            orgConnectionDto.setItemId(connection.getOrg().getId());
////            orgConnectionDto.setConnection(connection.getConnection());
////            orgConnectionDto.setComment(connection.getComment());
////
////            this.orgList.add(orgConnectionDto);
////        }
//
//
//        this.hashtagList = new ArrayList<>();
//        for (ArticleHashtag articleHashtag : article.getHashtagList()) {
//            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
//                this.hashtagList.add(articleHashtag.getHashtag().getContent());
//            }
//        }
//        this.miscellany = article.getMiscellany();
//    }

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

    public List<String> getPersonList() {
        return personList;
    }

    public void setPersonList(List<String> personList) {
        this.personList = personList;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public List<String> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<String> projectList) {
        this.projectList = projectList;
    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
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
