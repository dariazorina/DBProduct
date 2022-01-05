package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class PersonConverter {

    public static void convertToNewPersonDtoForMainList(Person p, NewPersonDtoForMainList personDFML) {
        personDFML.setId(p.getId());
        personDFML.setStatus(p.getStatus().getName());
        personDFML.setMovementList(p.getMovementList());
        personDFML.setRowColor(p.getRgbSelection());
        personDFML.setBirthYear(p.getBirthYear());
        personDFML.setDeathYear(p.getDeathYear());

        personDFML.setHashtagList(new ArrayList<>());
        for (PersonHashtag pers: p.getHashtagList()) {
            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
                personDFML.getHashtagList().add(pers.getHashtag().getContent());
        }

        List<String> orgStringList = new ArrayList<>();
        String dtoName = "";
        for (OrgPersonConnection orgPC : p.getOrgConnections()) {

           Org org = orgPC.getOrg();
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
            orgStringList.add(dtoName);
            dtoName = "";
        }
        personDFML.setOrgList(orgStringList);

        List<String> locationStringList = new ArrayList<>();
        for (PersonLocationConnection plC : p.getLocationConnections()) {
            locationStringList.add(plC.getLocation().getCountry());
        }
        personDFML.setLocationList(locationStringList);


        String name = "";
        for (SurnameNamePatr snp : p.getSnpList()) {
            if (snp.getPriority() == 1) {
                name = snp.getSurname() + " " + snp.getName();
                if (snp.getPatronymic()!=null){
                    name += " " + snp.getPatronymic();
                }
            }
        }
        personDFML.setSnp(name);
    }



    public static void convertToNewPersonDto(Person p, NewPersonDto personDto) {
        personDto.setId(p.getId());
        personDto.setStatus(p.getStatus().getName());
        personDto.setMovementList(p.getMovementList());
        personDto.setDescription(p.getDescription());
        personDto.setMiscellany(p.getMiscellany());
        personDto.setRowColor(p.getRgbSelection());
        personDto.setLinkList(p.getLinkList());
        personDto.setBirthYear(p.getBirthYear());
        personDto.setDeathYear(p.getDeathYear());

        personDto.setHashtagList(new ArrayList<>());
        for (PersonHashtag pers: p.getHashtagList()) {
            if (pers.getHashtag().equals(pers.getAssigned_hashtag()))
                personDto.getHashtagList().add(pers.getHashtag().getContent());
        }

        personDto.setOrgList(new ArrayList<>());
        personDto.setLocationList(new ArrayList<>());
        personDto.setPersonList(new ArrayList<>());
        personDto.setProjectList(new ArrayList<>());
        personDto.setArticleList(new ArrayList<>());
        personDto.setSnpList(new ArrayList<>());
        personDto.setIsourceList(new ArrayList<>());

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

            personDto.getOrgList().add(orgConnectionDto);
        }

        NameConnectionDto projectConnectionDto;
        for (ProjectPersonConnection connection : p.getProjectConnections()) {
            projectConnectionDto = new NameConnectionDto();
            Project project = connection.getProject();

            projectConnectionDto.setItemId(project.getId());
            projectConnectionDto.setName(project.getMainTitle());
            projectConnectionDto.setConnection(connection.getConnection());
            projectConnectionDto.setComment(connection.getComment());

            personDto.getProjectList().add(projectConnectionDto);
        }

        NameConnectionDto articleConnectionDto;
        for (ArticlePersonConnection connection : p.getArticleConnections()) {
            articleConnectionDto = new NameConnectionDto();
            Article article = connection.getArticle();
            articleConnectionDto.setItemId(article.getId());

            dtoName = article.getTitleRus();
            if (article.getTitle()!= null && article.getTitle().length() > 0 ){
                dtoName += "/ " + article.getTitle();
            }

            articleConnectionDto.setName(dtoName);
            articleConnectionDto.setConnection(connection.getConnection());
            articleConnectionDto.setComment(connection.getComment());

            personDto.getArticleList().add(articleConnectionDto);
        }

        NameConnectionDto locationConnectionDto;
        for (PersonLocationConnection connection : p.getLocationConnections()) {
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

            personDto.getLocationList().add(locationConnectionDto);
        }

        NameConnectionDto isourceConnectionDto;  //todo
        for (PersonIsourceConnection connection : p.getIsourceConnections()) {
            isourceConnectionDto = new NameConnectionDto();
            isourceConnectionDto.setItemId(connection.getIsource().getId());
            isourceConnectionDto.setConnection(connection.getConnection());
            isourceConnectionDto.setComment(connection.getComment());

            personDto.getIsourceList().add(isourceConnectionDto);
        }

        dtoName = "";
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
//            personConnectionDto.setName(dtoName);
//            personConnectionDto.setItemId(connection.getConnectedPerson().getId());
//            personConnectionDto.setConnection(connection.getConnection());
//            personConnectionDto.setComment(connection.getComment());
//
//            personDto.getPersonList().add(personConnectionDto);
//        }

        SnpDto snpDto;
        for (SurnameNamePatr snp : p.getSnpList()) {
            snpDto = new SnpDto();
            snpDto.setId(snp.getId());
            snpDto.setSurname(snp.getSurname());
            snpDto.setName(snp.getName());
            snpDto.setPatronymic(snp.getPatronymic());
            snpDto.setPriority(snp.getPriority());

            personDto.getSnpList().add(snpDto);
        }

//        if (p.getPhoto() != null) {
//            String encodedString = Base64.getEncoder().encodeToString(p.getPhoto());
//            this.photo = encodedString;
//        }
    }
}
