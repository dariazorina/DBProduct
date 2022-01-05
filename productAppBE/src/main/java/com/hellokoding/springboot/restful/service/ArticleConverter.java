package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
public class ArticleConverter {

    public static void convertToArticleDtoForMainList(Article article, ArticleDtoForMainList articleDFML) {
        articleDFML.setId(article.getId());
        articleDFML.setMovementList(article.getMovementList());
        articleDFML.setLanguage(article.getLanguage());
        articleDFML.setMtype(article.getMtype());
        articleDFML.setDate(article.getDate());
        articleDFML.setTitle(article.getTitle());
        articleDFML.setStatus(article.getStatus().getName());
        articleDFML.setTitleRus(article.getTitleRus());
        articleDFML.setRowColor(article.getRgbSelection());


        List<String> locationStringList = new ArrayList<>();
        for (ArticleLocationConnection alC : article.getLocationConnections()) {
            locationStringList.add(alC.getLocation().getCountry());
        }
        articleDFML.setLocationList(locationStringList);


        List<String> projectStringList = new ArrayList<>();
        for (ProjectArticleConnection projectArticleConnection : article.getProjectConnections()) {
            projectStringList.add(projectArticleConnection.getProject().getMainTitle());
        }
        articleDFML.setProjectList(projectStringList);


        List<String> personStringList = new ArrayList<>();
        for (ArticlePersonConnection aPC : article.getPersonConnections()) {
            List<SurnameNamePatr> snpList = aPC.getPerson().getSnpList();
            String snp = "error";
            for (SurnameNamePatr nameEl : snpList) {
                if (nameEl.getPriority() == 1) {
                    snp = nameEl.getSurname();
                    if (nameEl.getName().length() > 0) {
                        snp += " " + nameEl.getName();
                    }
                    break;
                }
            }
            if (aPC.getConnection().length() > 0) {
                snp += ", " + aPC.getConnection();
            }
            personStringList.add(snp);
        }
        articleDFML.setPersonList(personStringList);


        List<String> orgStringList = new ArrayList<>();
        for (ArticleOrgConnection aOC : article.getOrgConnections()) {
            List<OrgName> nameList = aOC.getOrg().getNameList();
            String name = "error";
            for (OrgName nameEl : nameList) {
                if (nameEl.getPriority() == 1) {
                    name = nameEl.getName();
                    if (nameEl.getAbbr().length() > 0) {
                        name += ", " + nameEl.getAbbr();
                    }
                    break;
                }
            }
            if (aOC.getConnection().length() > 0) {
                name += ", " + aOC.getConnection();
            }
            orgStringList.add(name);
        }
        articleDFML.setOrgList(orgStringList);


        articleDFML.setHashtagList(new ArrayList<>());
        for (ArticleHashtag articleHashtag : article.getHashtagList()) {
            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
                articleDFML.getHashtagList().add(articleHashtag.getHashtag().getContent());
            }
        }
        articleDFML.setMiscellany(article.getMiscellany());
    }

//    public ArticleDto(Article article) {
//
//        this.id = article.getId();
//        this.movementList = article.getMovementList();
//        this.language = article.getLanguage();
//        this.mtype = article.getMtype();
//        this.date = article.getDate();
//        this.title = article.getTitle();
//        this.status = article.getStatus().getName();
//        this.titleRus = article.getTitleRus();
//        this.description = article.getDescription();
//        this.text = article.getText();
//        this.rowColor = article.getRgbSelection();
//        this.linkList = article.getLinkList();
//
//        this.personList = new ArrayList<>();
//        this.locationList = new ArrayList<>();
//        this.orgList = new ArrayList<>();
//        this.materialList = new ArrayList<>();
//
//        ItemConnectionDto personConnectionDto;
//        for (ArticlePersonConnection connection : article.getPersonConnections()) {
//            personConnectionDto = new ItemConnectionDto();
//            personConnectionDto.setItemId(connection.getPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            this.personList.add(personConnectionDto);
//        }
//
//        ItemConnectionDto locationConnectionDto;
//        for (ArticleLocationConnection connection : article.getLocationConnections()) {
//            locationConnectionDto = new ItemConnectionDto();
//            locationConnectionDto.setItemId(connection.getLocation().getId());
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.locationList.add(locationConnectionDto);
//        }
//
//        ItemConnectionDto orgConnectionDto;
//        for (ArticleOrgConnection connection : article.getOrgConnections()) {
//            orgConnectionDto = new ItemConnectionDto();
//            orgConnectionDto.setItemId(connection.getOrg().getId());
//            orgConnectionDto.setConnection(connection.getConnection());
//            orgConnectionDto.setComment(connection.getComment());
//
//            this.orgList.add(orgConnectionDto);
//        }
//
//
//        this.hashtagList = new ArrayList<>();
//        for ( ArticleHashtag articleHashtag : article.getHashtagList()) {
//            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
//                this.hashtagList.add(articleHashtag.getHashtag().getContent());
//            }
//        }
//        this.miscellany = article.getMiscellany();
//    }


    public static void convertToArticleDto(Article article, ArticleDto articleDto, List<ConnectionType> types) {  //for details, add/edit
        articleDto.setId(article.getId());
        articleDto.setMovementList(article.getMovementList());
        articleDto.setLanguage(article.getLanguage());
        articleDto.setMtype(article.getMtype());
        articleDto.setDate(article.getDate());
        articleDto.setTitle(article.getTitle());
        articleDto.setStatus(article.getStatus().getName());
        articleDto.setTitleRus(article.getTitleRus());
        articleDto.setDescription(article.getDescription());
        articleDto.setText(article.getText());
        articleDto.setRowColor(article.getRgbSelection());
        articleDto.setLinkList(article.getLinkList());

        articleDto.setPersonList(new ArrayList<>());
        articleDto.setLocationList(new ArrayList<>());
        articleDto.setOrgList(new ArrayList<>());
        articleDto.setMaterialList(new ArrayList<>());
        articleDto.setProjectList(new ArrayList<>());

        NameConnectionDto personConnectionDto;
        String dtoName = "";
        for (ArticlePersonConnection connection : article.getPersonConnections()) {
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

            articleDto.getPersonList().add(personConnectionDto);
        }

        NameConnectionDto locationConnectionDto;
        for (ArticleLocationConnection connection : article.getLocationConnections()) {
            locationConnectionDto = new NameConnectionDto();

            Location location = connection.getLocation();
            locationConnectionDto.setItemId(location.getId());

            dtoName = location.getCountry();
            if (location.getCity().length() > 0){
                dtoName += ", " + location.getCity();
            }

            if (location.getRegion() != null && location.getRegion().length() > 0){
                dtoName += ", " + location.getRegion();
            }

            if (location.getCity() != null && location.getCity().length() > 0){
                dtoName += ", " + location.getCity();
            }

            if (location.getAddress() != null && location.getAddress().length() > 0){
                dtoName += ", " + location.getAddress();
            }

            if (location.getPlacement() != null && location.getPlacement().length() > 0){
                dtoName += ", " + location.getPlacement();
            }

            locationConnectionDto.setName(dtoName);
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            articleDto.getLocationList().add(locationConnectionDto);
        }

        NameConnectionDto projectConnectionDto;
        for (ProjectArticleConnection connection : article.getProjectConnections()) {
            projectConnectionDto = new NameConnectionDto();
            Project project = connection.getProject();

            projectConnectionDto.setItemId(project.getId());
            projectConnectionDto.setName(project.getMainTitle());
            projectConnectionDto.setConnection(connection.getConnection());
            projectConnectionDto.setComment(connection.getComment());

            articleDto.getProjectList().add(projectConnectionDto);
        }

        NameConnectionDto orgConnectionDto;
        for (ArticleOrgConnection connection : article.getOrgConnections()) {
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

            articleDto.getOrgList().add(orgConnectionDto);
        }

        //replaced to article service (connection types there)
//        ItemConnectionDto materialConnectionDto;
//        for (ArticleMaterialConnection connection : article.getMaterialConnections()) {
//            materialConnectionDto = new ItemConnectionDto();
//            materialConnectionDto.setItemId(connection.getMaterial().getId());
//            materialConnectionDto.setConnection(connection.getConnection().toString());
//            materialConnectionDto.setComment(connection.getComment());
//
//            this.materialList.add(materialConnectionDto);
//        }

//        ArrayList<NameConnectionDto> materialList = new ArrayList<>();
        if (article.getMaterialConnections().size() > 0) {
            NameConnectionDto materialConnectionDto = new NameConnectionDto();
            dtoName = "";

            for (ArticleMaterialConnection connection : article.getMaterialConnections()) {
                materialConnectionDto = new NameConnectionDto();

                Optional<ConnectionType> matchingObject = types.stream().
                        filter(p -> p.getId().equals(connection.getConnection())).
                        findFirst();

                if (article.getTitleRus() != null) {
                    if (article.getTitleRus().length() > 0) {
                        dtoName += article.getTitleRus();
                        if (article.getTitle() != null) {
                            if (article.getTitle().length() > 0) {
                                dtoName += "/ " + article.getTitle();
                            }
                        }
                    }
                }
                LocalDate dateWithZeroTime = article.getDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (dateWithZeroTime != null) dtoName += ", " + dateWithZeroTime; //article.getDate().;
                else {
                    dtoName += "," + article.getLinkList().get(0);  //as the first help in fail with date
                }

                materialConnectionDto.setName(dtoName);
                materialConnectionDto.setItemId(connection.getMaterial().getId());
                materialConnectionDto.setConnection(matchingObject.get().getType());
                materialConnectionDto.setComment(connection.getComment());
                dtoName = "";

                articleDto.getMaterialList().add(materialConnectionDto);
            }//for
        }

        articleDto.setHashtagList(new ArrayList<>());
        for (ArticleHashtag articleHashtag : article.getHashtagList()) {
            if (articleHashtag.getHashtag().equals(articleHashtag.getAssigned_hashtag())) {
                articleDto.getHashtagList().add(articleHashtag.getHashtag().getContent());
            }
        }
        articleDto.setMiscellany(article.getMiscellany());
    }
}
