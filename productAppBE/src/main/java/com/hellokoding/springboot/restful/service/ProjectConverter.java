package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class ProjectConverter {

    public static void convertToProjectDtoForMainList(Project project, ProjectDtoForMainList projectDFML, ProjectService projectService) {
        projectDFML.setId(project.getId());
        projectDFML.setStatus(project.getStatus().getName());
        projectDFML.setMovementList(project.getMovementList());
        projectDFML.setRowColor(project.getRgbSelection());
        projectDFML.setFoundationYear(project.getFoundedYear());
        projectDFML.setClosureYear(project.getClosedYear());

        projectDFML.setTagList(new ArrayList<>());
        for (Tag tag: project.getTagList()){
            projectDFML.getTagList().add(tag.getContent());
        }

        projectDFML.setHashtagList(new ArrayList<>());
        for (ProjectHashtag projectHashtag : project.getHashtagList()) {
            if (projectHashtag.getHashtag().equals(projectHashtag.getAssigned_hashtag()))
                projectDFML.getHashtagList().add(projectHashtag.getHashtag().getContent());
        }


        List<String> locationStringList = new ArrayList<>();
        for (ProjectLocationConnection plC : project.getLocationConnections()) {
            locationStringList.add(plC.getLocation().getCountry());
        }
        projectDFML.setLocationList(locationStringList);


        List<String> personStringList = new ArrayList<>();
        for (ProjectPersonConnection projPersConn : project.getPersonConnections()) {
            List<SurnameNamePatr> snpList = projPersConn.getPerson().getSnpList();
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
            if (projPersConn.getConnection().length() > 0) {
                snp += ", " + projPersConn.getConnection();
            }
            personStringList.add(snp);
        }
        projectDFML.setPersonList(personStringList);


        List<String> orgStringList = new ArrayList<>();
        for (ProjectOrgConnection orgOC : project.getOrgConnections()) {
            List<OrgName> nameList = orgOC.getOrg().getNameList();
            String name = "";
            for (OrgName nameEl : nameList) {
                if (nameEl.getPriority() == 1) {
                    name = nameEl.getName();
                    if (nameEl.getAbbr().length() > 0) {
                        name += ", " + nameEl.getAbbr();
                    }
                    break;
                }
            }
            if (orgOC.getConnection().length() > 0) {
                name += ", " + orgOC.getConnection();
            }
            orgStringList.add(name);
        }
        projectDFML.setOrgList(orgStringList);


        List<OneTypeConnectionDto> projectList = projectService.findByIdsAndSymmetrically(project.getId());

        //from OneTypeConnectionDto to String: OneTypeConnectionDto -> String
        List<String> proStringList = new ArrayList<>();
        for (OneTypeConnectionDto ncdORG: projectList){
            proStringList.add(ncdORG.getName());
        }
        projectDFML.setProjectList(proStringList);


        String name = project.getMainTitle();

        if (project.getOtherTitle() != null && project.getOtherTitle().length() != 0) {
            name += "/ " + project.getOtherTitle();
        }
        projectDFML.setName(name);
    }

    public static void convertToProjectDto(Project project, ProjectDto projectDto) {
        projectDto.setId(project.getId());
        projectDto.setMainTitle(project.getMainTitle());
        projectDto.setOtherTitle(project.getOtherTitle());
        projectDto.setStatus(project.getStatus().getName());
        projectDto.setMovementList(project.getMovementList());
        projectDto.setDescription(project.getDescription());
        projectDto.setMiscellany(project.getMiscellany());
        projectDto.setRowColor(project.getRgbSelection());
        projectDto.setLinkList(project.getLinkList());
        projectDto.setFoundationYear(project.getFoundedYear());
        projectDto.setClosureYear(project.getClosedYear());
        projectDto.setTagList(project.getTagList());


        projectDto.setHashtagList(new ArrayList<>());
        for (ProjectHashtag projectHashtag : project.getHashtagList()) {
            if (projectHashtag.getHashtag().equals(projectHashtag.getAssigned_hashtag()))
                projectDto.getHashtagList().add(projectHashtag.getHashtag().getContent());
        }

        projectDto.setOrgList(new ArrayList<>());
        projectDto.setProjectList(new ArrayList<>());
        projectDto.setLocationList(new ArrayList<>());
        projectDto.setPersonList(new ArrayList<>());
//        this.eventList = new ArrayList<>();
        projectDto.setArticleList(new ArrayList<>());


        NameConnectionDto projectConnectionDto;
        String dtoName = "";


        //projectList defines in search simple and symm

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
//            projectDto.setProjectList.add(projectConnectionDto);
//            dtoName = "";
//        }

        NameConnectionDto locationConnectionDto;
        for (ProjectLocationConnection connection : project.getLocationConnections()) {
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

            projectDto.getLocationList().add(locationConnectionDto);
        }

        NameConnectionDto articleConnectionDto;
        for (ProjectArticleConnection connection : project.getArticleConnections()) {
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

            projectDto.getArticleList().add(articleConnectionDto);
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

            projectDto.getPersonList().add(personConnectionDto);
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

            projectDto.getOrgList().add(orgConnectionDto);
        }
    }

}
