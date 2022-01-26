package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.EventDto;
import com.hellokoding.springboot.restful.model.dto.EventDtoForMainList;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;

import java.util.ArrayList;
import java.util.List;

public class EventConverter {

    public static void convertToEventDto(Event event, EventDto eventDto) {

        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setType(event.getType());
        eventDto.setStatus(event.getStatus().getName());
        eventDto.setMovementList(event.getMovementList());
        eventDto.setDescription(event.getDescription());
        eventDto.setMiscellany(event.getMiscellany());
        eventDto.setRowColor(event.getRgbSelection());
        eventDto.setLinkList(event.getLinkList());
        eventDto.setStartDate(event.getStartDate());
        eventDto.setEndDate(event.getEndDate());
        eventDto.setTagList(event.getTagList());


        eventDto.setHashtagList(new ArrayList<>());
        for (EventHashtag eventHashtag : event.getHashtagList()) {
            if (eventHashtag.getHashtag().equals(eventHashtag.getAssigned_hashtag()))
                eventDto.getHashtagList().add(eventHashtag.getHashtag().getContent());
        }

        eventDto.setOrgList(new ArrayList<>());
        eventDto.setProjectList(new ArrayList<>());
        eventDto.setLocationList(new ArrayList<>());
        eventDto.setPersonList(new ArrayList<>());
        eventDto.setEventList(new ArrayList<>());
        eventDto.setArticleList(new ArrayList<>());

        String dtoName = "";

//!!!!!!!!!!!!такой вариант не работает на серваке!!!!!!!!!!!!
//        NameConnectionDto locationConnectionDto;
//        for (ProjectLocationConnection connection : project.getLocationConnections()) {
//            locationConnectionDto = new NameConnectionDto();
//            Location location = connection.getLocation();
//            locationConnectionDto.setItemId(location.getId());
//
//            dtoName = "location error";
//            if (location.getCountry().length() > 0) {
//                dtoName = location.getCountry();
//            }
//
//            if (location.getRegion().length() > 0) {
//                dtoName += ", " + location.getRegion();
//            }
//
//            if (location.getCity().length() > 0) {
//                dtoName += ", " + location.getCity();
//            }
//
//            if (location.getAddress().length() > 0) {
//                dtoName += ", " + location.getAddress();
//            }
//
//            if (location.getPlacement().length() > 0) {
//                dtoName += ", " + location.getPlacement();
//            }
//
//            locationConnectionDto.setName(dtoName);
//            locationConnectionDto.setConnection(connection.getConnection());
//            locationConnectionDto.setComment(connection.getComment());
//
//            this.getLocationList().add(locationConnectionDto);
//        }


        NameConnectionDto locationConnectionDto;
        for (LocationEventConnection connection : event.getLocationConnections()) {
            locationConnectionDto = new NameConnectionDto();

            Location location = connection.getLocation();
            locationConnectionDto.setItemId(location.getId());

            dtoName = location.getCountry();

            if (location.getRegion() != null && location.getRegion().length() > 0) {
                dtoName += ", " + location.getRegion();
            }

            if (location.getCity() != null && location.getCity().length() > 0) {
                dtoName += ", " + location.getCity();
            }

            if (location.getAddress() != null && location.getAddress().length() > 0) {
                dtoName += ", " + location.getAddress();
            }

            if (location.getPlacement() != null && location.getPlacement().length() > 0) {
                dtoName += ", " + location.getPlacement();
            }

            locationConnectionDto.setName(dtoName);
            locationConnectionDto.setConnection(connection.getConnection());
            locationConnectionDto.setComment(connection.getComment());

            eventDto.getLocationList().add(locationConnectionDto);
        }


        NameConnectionDto articleConnectionDto;
        for (ArticleEventConnection connection : event.getArticleConnections()) {
            articleConnectionDto = new NameConnectionDto();
            Article article = connection.getArticle();
            articleConnectionDto.setItemId(article.getId());

            dtoName = article.getTitleRus();
            if (article.getTitle() != null && article.getTitle().length() > 0) {
                dtoName += "/ " + article.getTitle();
            }

            articleConnectionDto.setName(dtoName);
            articleConnectionDto.setConnection(connection.getConnection());
            articleConnectionDto.setComment(connection.getComment());

            eventDto.getArticleList().add(articleConnectionDto);
        }

        NameConnectionDto projectConnectionDto;
        for (ProjectEventConnection connection : event.getProjectConnections()) {
            projectConnectionDto = new NameConnectionDto();
            Project project = connection.getProject();

            projectConnectionDto.setItemId(project.getId());
            projectConnectionDto.setName(project.getMainTitle());
            projectConnectionDto.setConnection(connection.getConnection());
            projectConnectionDto.setComment(connection.getComment());

            eventDto.getProjectList().add(projectConnectionDto);
        }

        NameConnectionDto personConnectionDto;
        for (PersonEventConnection connection : event.getPersonConnections()) {
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

            eventDto.getPersonList().add(personConnectionDto);
        }

        NameConnectionDto orgConnectionDto;
        for (OrgEventConnection connection : event.getOrgConnections()) {
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

            eventDto.getOrgList().add(orgConnectionDto);
        }
    }

    public static void convertToEventDtoForMainList(Event event, EventDtoForMainList eventDFML, EventService eventService) {
        eventDFML.setId(event.getId());
        eventDFML.setStatus(event.getStatus().getName());
        eventDFML.setMovementList(event.getMovementList());
        eventDFML.setRowColor(event.getRgbSelection());
        eventDFML.setStartDate(event.getStartDate());
        eventDFML.setTitle(event.getTitle());
        eventDFML.setType(event.getType());


        eventDFML.setHashtagList(new ArrayList<>());
        for (EventHashtag eventHashtag : event.getHashtagList()) {
            if (eventHashtag.getHashtag().equals(eventHashtag.getAssigned_hashtag()))
                eventDFML.getHashtagList().add(eventHashtag.getHashtag().getContent());
        }

        List<String> locationStringList = new ArrayList<>();
        for (LocationEventConnection leC : event.getLocationConnections()) {
            locationStringList.add(leC.getLocation().getCountry());
        }
        eventDFML.setLocationList(locationStringList);

        List<String> personStringList = new ArrayList<>();
        for (PersonEventConnection eventPersConn : event.getPersonConnections()) {
            List<SurnameNamePatr> snpList = eventPersConn.getPerson().getSnpList();
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
            if (eventPersConn.getConnection().length() > 0) {
                snp += ", " + eventPersConn.getConnection();
            }
            personStringList.add(snp);
        }
        eventDFML.setPersonList(personStringList);

        List<String> orgStringList = new ArrayList<>();
        for (OrgEventConnection orgEventConnection : event.getOrgConnections()) {
            List<OrgName> nameList = orgEventConnection.getOrg().getNameList();
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
            if (orgEventConnection.getConnection().length() > 0) {
                name += ", " + orgEventConnection.getConnection();
            }
            orgStringList.add(name);
        }
        eventDFML.setOrgList(orgStringList);
    }
}
