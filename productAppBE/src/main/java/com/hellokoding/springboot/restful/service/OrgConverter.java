package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class OrgConverter {

    public static void convertToOrgDtoForMainList(Org org, OrgDtoForMainList orgDFML, OrgService orgService) {
        orgDFML.setId(org.getId());
        orgDFML.setStatus(org.getStatus().getName());
        orgDFML.setMovementList(org.getMovementList());
        orgDFML.setRowColor(org.getRgbSelection());
        orgDFML.setFoundationYear(org.getFoundedYear());
        orgDFML.setClosureYear(org.getClosedYear());

        orgDFML.setType(org.getOrgType());

        orgDFML.setHashtagList(new ArrayList<>());
        for (OrgHashtag orgHashtag : org.getHashtagList()) {
            if (orgHashtag.getHashtag().equals(orgHashtag.getAssigned_hashtag()))
                orgDFML.getHashtagList().add(orgHashtag.getHashtag().getContent());
        }


        List<String> locationStringList = new ArrayList<>();
        for (OrgLocationConnection plC : org.getLocationConnections()) {
            locationStringList.add(plC.getLocation().getCountry());
        }
        orgDFML.setLocationList(locationStringList);


        List<String> personStringList = new ArrayList<>();
        for (OrgPersonConnection orgPC : org.getPersonConnections()) {
            List<SurnameNamePatr> snpList = orgPC.getPerson().getSnpList();
            String snp = "some error";
            for (SurnameNamePatr nameEl : snpList) {
                if (nameEl.getPriority() == 1) {
                    snp = nameEl.getSurname();
                    if (nameEl.getName().length() > 0) {
                        snp += " " + nameEl.getName();
                    }
                    break;
                }
            }
            if (orgPC.getConnection().length() > 0) {
                snp += ", " + orgPC.getConnection();
            }
            personStringList.add(snp);
        }
        orgDFML.setPersonList(personStringList);

        List<OneTypeConnectionDto> orgList = orgService.findByIdsAndSymmetrically(org.getId());

        //from NameConnDto to String: NameConnectionDto -> String
        List<String> orgStringList = new ArrayList<>();
        for (OneTypeConnectionDto ncdORG: orgList){
            orgStringList.add(ncdORG.getName());
        }
        orgDFML.setOrgList(orgStringList);


        String compositeName = "";
        for (OrgName name : org.getNameList()) {
            if (name.getPriority() == 1) {
                compositeName = name.getName();

                if (name.getAbbr() != null && name.getAbbr().length() != 0) {
                    compositeName += "/ " + name.getAbbr();
                }
                break;
            }
        }
        orgDFML.setName(compositeName);
    }

    public static void convertToOrgDto(Org org, OrgDto orgDto) {
        orgDto.setId(org.getId());
        orgDto.setStatus(org.getStatus().getName());
        orgDto.setMovementList(org.getMovementList());
        orgDto.setDescription(org.getDescription());
        orgDto.setMiscellany(org.getMiscellany());
        orgDto.setRowColor(org.getRgbSelection());
        orgDto.setLinkList(org.getLinkList());
        orgDto.setFoundationYear(org.getFoundedYear());
        orgDto.setClosureYear(org.getClosedYear());

        orgDto.setType(org.getOrgType());

        orgDto.setHashtagList(new ArrayList<>());
        for (OrgHashtag orgHashtag : org.getHashtagList()) {
            if (orgHashtag.getHashtag().equals(orgHashtag.getAssigned_hashtag()))
                orgDto.getHashtagList().add(orgHashtag.getHashtag().getContent());
        }

        orgDto.setOrgList(new ArrayList<>());
        orgDto.setLocationList(new ArrayList<>());
        orgDto.setPersonList(new ArrayList<>());
        orgDto.setProjectList(new ArrayList<>());
        orgDto.setNameList(new ArrayList<>());
        orgDto.setIsourceList(new ArrayList<>());
        orgDto.setArticleList(new ArrayList<>());


//        NameConnectionDto orgConnectionDto;
        String dtoName = "";
//
//        for (OrgOrgConnection connection : org.getOrgConnections()) {
//            orgConnectionDto = new NameConnectionDto();
//
//            if (org.getNameList() != null) {
//                for (OrgName name : org.getNameList()) {
//                    if (name.getPriority() == 1) {
//                        dtoName += name.getName();
//
//                        if (name.getAbbr() != null && name.getAbbr().length() != 0) {
//                            dtoName += "/ " + name.getAbbr();
//                        }
//                    }
//                }
//            }
//
//            orgConnectionDto.setName(dtoName);
//            orgConnectionDto.setItemId(connection.getConnectedOrg().getId());
//            orgConnectionDto.setConnection(connection.getConnection());
//            orgConnectionDto.setComment(connection.getComment());
//
//            orgDto.getOrgList().add(orgConnectionDto);
//            dtoName = "";
//        }

        NameConnectionDto locationConnectionDto;
        for (OrgLocationConnection connection : org.getLocationConnections()) {
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

            orgDto.getLocationList().add(locationConnectionDto);
        }

//        NameConnectionDto isourceConnectionDto;
//        for (OrgIsourceConnection connection : org.getIsourceConnections()) {
//            isourceConnectionDto = new NameConnectionDto();
//            isourceConnectionDto.setItemId(connection.getIsource().getId());
//            isourceConnectionDto.setConnection(connection.getConnection());
//            isourceConnectionDto.setComment(connection.getComment());
//
//            orgDto.getIsourceList().add(isourceConnectionDto);
//        }

        NameConnectionDto articleConnectionDto;
        for (ArticleOrgConnection connection : org.getArticleConnections()) {
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

            orgDto.getArticleList().add(articleConnectionDto);
        }

        NameConnectionDto personConnectionDto;
        for (OrgPersonConnection connection : org.getPersonConnections()) {
            personConnectionDto = new NameConnectionDto();
            Person person = connection.getPerson();

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

            personConnectionDto.setItemId(person.getId());
            personConnectionDto.setName(dtoName);
            personConnectionDto.setConnection(connection.getConnection());
            personConnectionDto.setComment(connection.getComment());

            orgDto.getPersonList().add(personConnectionDto);
        }

        NameConnectionDto projectConnectionDto;
        for (ProjectOrgConnection connection : org.getProjectConnections()) {
            projectConnectionDto = new NameConnectionDto();
            Project project = connection.getProject();

            projectConnectionDto.setItemId(project.getId());
            projectConnectionDto.setName(project.getMainTitle());
            projectConnectionDto.setConnection(connection.getConnection());
            projectConnectionDto.setComment(connection.getComment());

            orgDto.getProjectList().add(projectConnectionDto);
        }

        OrgNameDto nameListDto;
        for (OrgName name : org.getNameList()) {
            nameListDto = new OrgNameDto();
            nameListDto.setId(name.getId());
            nameListDto.setName(name.getName());
            nameListDto.setAbbr(name.getAbbr());
            nameListDto.setPriority(name.getPriority());

            orgDto.getNameList().add(nameListDto);
        }

//        if (p.getPhoto() != null) {
//            String encodedString = Base64.getEncoder().encodeToString(p.getPhoto());
//            this.photo = encodedString;
//        }
    }
}
