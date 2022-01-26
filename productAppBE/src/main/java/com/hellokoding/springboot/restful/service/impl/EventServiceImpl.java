package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.EventConverter;
import com.hellokoding.springboot.restful.service.EventService;
import com.hellokoding.springboot.restful.service.UrlLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventEventRepository eventEventRepository;

    private final ProjectRepository projectRepository;
    private final OrgRepository orgRepository;
    private final StatusRepository statusRepository;
    private final HashTagRepository hashTagRepository;
    private final LocationRepository locationRepository;
//    private final IsourceRepository isourceRepository;
    private final PersonRepository personRepository;
    private final ArticleRepository articleRepository;

    private final UrlLinkService urlLinkService;

//    @Override
//    public List<Event> findAll() {
//        List<Event> all = eventRepository.findAll();
//        return all;
//    }

//    @Override
//    public Optional<Event> findById(Integer id) {
//        Optional<Event> byId = eventRepository.findById(id);
//        return byId;
//    }

    public List<EventDtoForMainList> findAll(List<Integer> mov, Integer page, Integer size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Event> pageTuts;

        List<EventDtoForMainList> dtoAllEventList = new ArrayList<>();
        pageTuts = eventRepository.findAllWithMovements(paging, mov);

        EventDtoForMainList currentEventDto;
        for (Event event : pageTuts) {
            currentEventDto = new EventDtoForMainList();
            EventConverter.convertToEventDtoForMainList(event, currentEventDto, this);
            dtoAllEventList.add(currentEventDto);
        }
        return dtoAllEventList;
    }

    @Override
    public List<OneTypeConnectionDto> findByIdsAndSymmetrically(Integer itemId) {

        Event connectedEvent = new Event(), event = new Event();
        String dtoName = "", connection = "", comment = "";
        List<OneTypeConnectionDto> finalList = new ArrayList<>();

        List<EventEventConnection> searchResSymm = new ArrayList<>();
        Optional<Event> eventOpt = eventRepository.findById(itemId);

        if (eventOpt.isPresent()) {
            event = eventOpt.get();
            searchResSymm = eventEventRepository.findByIdSymm(itemId); //searching symm connections for this itemId

            for (EventEventConnection el : event.getEventConnections()) { //simple connections
                Optional<Event> connEventOpt = eventRepository.findById(el.getConnectedEvent().getId());

                if (connEventOpt.isPresent()) {
                    connectedEvent = connEventOpt.get();             //   searchRes.add(l.get());
                    dtoName = connectedEvent.getTitle();
                    dtoName += "/ " + connectedEvent.getType();

//                    if (connectedEvent.geterTitle() != null && connectedEvent.getOtherTitle().length() != 0) {
//                        dtoName += "/ " + connectedEvent.getOtherTitle();
//                    }
                }
                for (EventEventConnection evEventConnection : event.getEventConnections()) {
                    if (evEventConnection.getConnectedEvent().getId().equals(event.getId())) {
                        connection = evEventConnection.getConnection();

                        if (evEventConnection.getComment() != null) {
                            if (evEventConnection.getComment().length() != 0) {
                                comment = evEventConnection.getComment();
                            }
                        }
                    }
                }
                OneTypeConnectionDto eventDto = new OneTypeConnectionDto(connectedEvent.getId(), dtoName, connection, comment, false, false);
                finalList.add(eventDto);
            }
        }//for

        for (EventEventConnection eventEventConnection : searchResSymm) {
            if (eventEventConnection.getConnectedEvent().getId().equals(event.getId())) {

                dtoName = eventEventConnection.getEvent().getTitle();
                dtoName += "/ " + eventEventConnection.getEvent().getType();

//                if (eventEventConnection.getProject().getOtherTitle() != null && eventEventConnection.getProject().getOtherTitle().length() > 0) {
//                    dtoName += "/ " + eventEventConnection.getProject().getOtherTitle();
//                }
            }

            connection = eventEventConnection.getConnection();  //todo to test if connection is empty
            if (eventEventConnection.getComment() != null) {
                if (eventEventConnection.getComment().length() != 0) {
                    comment = eventEventConnection.getComment();
                }
            }
            OneTypeConnectionDto proDto = new OneTypeConnectionDto(eventEventConnection.getEvent().getId(), dtoName, connection, comment, true, false);
            finalList.add(proDto);
        }

        return finalList;
    }

    @Override
    public List<IdContentDto> search(String q) {

        List<Event> eventSearchList = new ArrayList<>();
        String query = "%" + q.toLowerCase() + "%";

        eventSearchList = eventRepository.findByName(query);
//        System.out.println(orgSearchList);

        if (eventSearchList.size() > 0) {
            return transformOriginToDto(eventSearchList);
        }
        return null;
    }

    public List<IdContentDto> transformOriginToDto(List<Event> eventList) {
        Set<IdContentDto> fooSet = new TreeSet<>();
        String dtoName;

        for (Event event : eventList) {
            dtoName = event.getTitle();
            //maybe add type?
//            if (pro.getOtherTitle() != null && pro.getOtherTitle().length() > 0) {
//                dtoName += " /" + pro.getOtherTitle();
//            }

            IdContentDto eventDto = new IdContentDto(event.getId(), dtoName);
            fooSet.add(eventDto);
        }
        List<IdContentDto> finalList = new ArrayList<>(fooSet);
        return finalList;
    }

    @Override
    public Event saveColor(EventDtoForMainList eventDto) {

        Event event;
        if (eventRepository.findById(eventDto.getId()).isPresent()) {
            event = eventRepository.findById(eventDto.getId()).get();

            event.setRgbSelection(eventDto.getRowColor());
            return eventRepository.save(event);

        } else
            return null;
    }

    @Override
    public EventDto findById(Integer id) {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            Event ev = event.get();

            EventDto eventDto = new EventDto();
            EventConverter.convertToEventDto(ev, eventDto);
            return eventDto;
        }
        return null;
    }


    @Override
    public Event save(EventDto eventDto) {

        Event event = null;

        List<UrlLink> linkList = eventDto.getLinkList();
        List<UrlLink> linkListWithID = urlLinkService.getLinkListID(linkList);

        if (eventDto.getId() == null) {
            event = new Event();
        } else if (eventRepository.findById(eventDto.getId()).isPresent()) {
            event = eventRepository.findById(eventDto.getId()).get();
        } else
            return null;

        if (event.getLinkList() != null) {
            event.getLinkList().clear();
            eventRepository.flush();
        } else {
            event.setLinkList(new ArrayList<>());
        }

        if (event.getLinkList() == null) {
            event.setLinkList(linkListWithID);
        } else {
            event.getLinkList().addAll(linkListWithID);
        }

        if (event.getTagList() != null) {
            event.getTagList().clear();
            eventRepository.flush();
        } else {
            event.setTagList(new ArrayList<>());
        }

        if (event.getTagList() == null) {
            event.setTagList(eventDto.getTagList());
        } else {
            event.getTagList().addAll(eventDto.getTagList());
        }

        if (event.getMovementList() != null) {
            event.getMovementList().clear();
            eventRepository.flush();
        } else {
            event.setMovementList(new ArrayList<>());
        }
        if (event.getMovementList() == null) {
            event.setMovementList(eventDto.getMovementList());
        } else {
            event.getMovementList().addAll(eventDto.getMovementList());
            //personRepository.flush();
        }


        event.setTitle(eventDto.getTitle());
        event.setType(eventDto.getType());

        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setDescription(eventDto.getDescription());
        event.setMiscellany(eventDto.getMiscellany());
        event.setRgbSelection(eventDto.getRowColor());

        Optional<Status> byName = statusRepository.getByName(eventDto.getStatus());
        if (byName.isPresent()) {
            event.setStatus(byName.get());
        }

        /////////////////org////
        if (event.getOrgConnections() != null) {
            event.getOrgConnections().clear();
            eventRepository.flush();
        }

        Integer orgId;
        OrgEventConnection orgConnection;
        List<OrgEventConnection> orgConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : eventDto.getOrgList()) {

            orgId = connectionDto.getItemId();
            if (orgRepository.findById(orgId).isPresent()) {
                orgConnection = new OrgEventConnection();
                orgConnection.setOrg(orgRepository.findById(orgId).get());
                orgConnection.setEvent(event);
                orgConnection.setConnection(connectionDto.getConnection());
                orgConnection.setComment(connectionDto.getComment());

                orgConnectionList.add(orgConnection);
            }
        }
        if (event.getOrgConnections() == null) {
            event.setOrgConnections(orgConnectionList);
        } else {
            event.getOrgConnections().addAll(orgConnectionList);
        }

        /////////////////project////
        if (event.getProjectConnections() != null) {
            event.getProjectConnections().clear();
            eventRepository.flush();
        }

        Integer proId;
        ProjectEventConnection proConnection;
        List<ProjectEventConnection> proConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : eventDto.getProjectList()) {

            proId = connectionDto.getItemId();
            if (projectRepository.findById(proId).isPresent()) {
                proConnection = new ProjectEventConnection();
                proConnection.setProject(projectRepository.findById(proId).get());
                proConnection.setEvent(event);
                proConnection.setConnection(connectionDto.getConnection());
                proConnection.setComment(connectionDto.getComment());

                proConnectionList.add(proConnection);
            }
        }
        if (event.getProjectConnections() == null) {
            event.setProjectConnections(proConnectionList);
        } else {
            event.getProjectConnections().addAll(proConnectionList);
        }

        //////////////location
        if (event.getLocationConnections() != null) {
            event.getLocationConnections().clear();
            eventRepository.flush();
        }

        Integer locatonId;
        LocationEventConnection locationConnection;
        List<LocationEventConnection> locationConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : eventDto.getLocationList()) {

            locatonId = connectionDto.getItemId();
            if (locationRepository.findById(locatonId).isPresent()) {
                locationConnection = new LocationEventConnection();
                locationConnection.setLocation(locationRepository.findById(locatonId).get());
                locationConnection.setEvent(event);
                locationConnection.setConnection(connectionDto.getConnection());
                locationConnection.setComment(connectionDto.getComment());

                locationConnectionList.add(locationConnection);
            }
        }

        if (event.getLocationConnections() == null) {
            event.setLocationConnections(locationConnectionList);
        } else {
            event.getLocationConnections().addAll(locationConnectionList);
        }


        ///article
        if (event.getArticleConnections() != null) {
            event.getArticleConnections().clear();
            eventRepository.flush();
        }

        Integer articleId;
        ArticleEventConnection articleConnection;
        List<ArticleEventConnection> articleConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : eventDto.getArticleList()) {

            articleId = connectionDto.getItemId();
            if (articleRepository.findById(articleId).isPresent()) {
                articleConnection = new ArticleEventConnection();
                articleConnection.setArticle(articleRepository.findById(articleId).get());
                articleConnection.setEvent(event);
                articleConnection.setConnection(connectionDto.getConnection());
                articleConnection.setComment(connectionDto.getComment());

                articleConnectionList.add(articleConnection);
            }
        }

        if (event.getArticleConnections() == null) {
            event.setArticleConnections(articleConnectionList);
        } else {
            event.getArticleConnections().addAll(articleConnectionList);
        }


//        if (project.getIsourceConnections() != null) {
//            project.getIsourceConnections().clear();
//            eventRepository.flush();
//        }
//
//        Integer isourceId;
//        ProjectIsourceConnection isourceConnection;
//        List<ProjectIsourceConnection> isourceConnectionList = new ArrayList<>();
//        for (ItemConnectionDto connectionDto : eventDto.getIsourceList()) {
//
//            isourceId = connectionDto.getItemId();
//            if (isourceRepository.findById(isourceId).isPresent()) {
//                isourceConnection = new ProjectIsourceConnection();
//                isourceConnection.setIsource(isourceRepository.findById(isourceId).get());
//                isourceConnection.setProject(project);
//                isourceConnection.setConnection(connectionDto.getConnection());
//                isourceConnection.setComment(connectionDto.getComment());
//
//                isourceConnectionList.add(isourceConnection);
//            }
//        }
//
//        if (project.getIsourceConnections() == null) {
//            project.setIsourceConnections(isourceConnectionList);
//        } else {
//            project.getIsourceConnections().addAll(isourceConnectionList);
//        }

        ///persons
        if (event.getPersonConnections() != null) {
            event.getPersonConnections().clear();
            eventRepository.flush();
        }

        Integer personId;
        PersonEventConnection personConnection;
        List<PersonEventConnection> personConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : eventDto.getPersonList()) {

            personId = connectionDto.getItemId();
            if (personRepository.findById(personId).isPresent()) {
                personConnection = new PersonEventConnection();
                personConnection.setPerson(personRepository.findById(personId).get());
                personConnection.setEvent(event);
                personConnection.setConnection(connectionDto.getConnection());
                personConnection.setComment(connectionDto.getComment());

                personConnectionList.add(personConnection);
            }
        }

        if (event.getPersonConnections() == null) {
            event.setPersonConnections(personConnectionList);
        } else {
            event.getPersonConnections().addAll(personConnectionList);
        }


        /////////////////event
        if (event.getEventConnections() != null) {
            event.getEventConnections().clear();
            eventRepository.flush();
        }

        Integer connectedEventId;
        boolean isSymmConnection = false;

        Event connectedEvent = null;
        EventEventConnection eventEventConnection;
        EventEventConnection connectedEventConnection;

        List<EventEventConnection> eventEventList = new ArrayList<>();
        List<EventEventConnection> connectedEventList = new ArrayList<>();
        List<EventEventConnection> symmConnectionList;


        for (OneTypeConnectionDto posDto : eventDto.getEventList()) {
            connectedEventId = posDto.getItemId();
            if (eventRepository.findById(connectedEventId).isPresent()) {

                symmConnectionList = eventEventRepository.findByIdSimple(connectedEventId);
                for (EventEventConnection eventEventConn : symmConnectionList) {
                    if (eventEventConn.getConnectedEvent().getId().equals(event.getId())) {
                        isSymmConnection = true;
                        break;
                    }
                }

                connectedEvent = eventRepository.findById(connectedEventId).get();

                if (!isSymmConnection) {

                    eventEventConnection = new EventEventConnection();
                    eventEventConnection.setConnection(posDto.getConnection());
                    eventEventConnection.setComment(posDto.getComment());


                    if (posDto.getIsParent()) {
                        eventEventConnection.setConnectedEvent(event);
                        eventEventConnection.setEvent(connectedEvent);

                    } else {
                        eventEventConnection.setConnectedEvent(connectedEvent);
                        eventEventConnection.setEvent(event);
                    }
                    eventEventList.add(eventEventConnection);

                } else {
                    connectedEventConnection = new EventEventConnection();
                    if (posDto.getIsParent()) {
                        connectedEventConnection.setEvent(connectedEvent);
                        connectedEventConnection.setConnectedEvent(event);
                    } else {
                        connectedEventConnection.setEvent(event);
                        connectedEventConnection.setConnectedEvent(connectedEvent);
                    }
                    connectedEventConnection.setConnection(posDto.getConnection());
                    connectedEventConnection.setComment(posDto.getComment());

                    connectedEventList.add(connectedEventConnection);

                    if (connectedEvent.getEventConnections() == null) {
                        connectedEvent.setEventConnections(connectedEventList);
                    } else {
                        //remove already existed entities
                        List<EventEventConnection> elList = new ArrayList<>();
                        for (EventEventConnection eventEventConnect : connectedEvent.getEventConnections()) {
                            if (eventEventConnect.getConnectedEvent().getId().equals(event.getId())) {
                                elList.add(eventEventConnect);
                            }
                        }
                        connectedEvent.getEventConnections().removeAll(elList);
                        eventRepository.flush();
                        connectedEvent.getEventConnections().addAll(connectedEventList);
                    }
                }
                isSymmConnection = false;
            }
        }//for (OneTypeConnectionDto posDto : orgDto.getOrgList())

        if (event.getId() != null) {
            //delete symm connections
            List<OneTypeConnectionDto> startListConnectedEventsForEvent = findByIdsAndSymmetrically(event.getId());
            List<OneTypeConnectionDto> resultListConnectedEventsForEvent = eventDto.getEventList();

            List<OneTypeConnectionDto> differences = startListConnectedEventsForEvent.stream()
                    .filter(element -> !resultListConnectedEventsForEvent.contains(element))
                    .collect(Collectors.toList());

            for (OneTypeConnectionDto nmdto : differences) {
                connectedEvent = eventRepository.findById(nmdto.getItemId()).get();

                //remove already existed entities
                List<EventEventConnection> elList = new ArrayList<>();
                for (EventEventConnection eventEventConn : connectedEvent.getEventConnections()) {
                    if (eventEventConn.getConnectedEvent().getId().equals(event.getId())) {
                        elList.add(eventEventConn);
                    }
                }
                connectedEvent.getEventConnections().removeAll(elList);
                eventRepository.flush();
            }
        }

        if (event.getEventConnections() == null) {
            event.setEventConnections(eventEventList);
        } else {
            event.getEventConnections().addAll(eventEventList);
        }


        /////////////////////event-hashtag////////////////////////
        EventHashtag eventHashtag, previousEventHashtag, previousPreviousEventHashtag;
        HashTag hashTag, hashTagPrevious, hashTagPreviousPrevious;
        List<EventHashtag> hashtagList = new ArrayList<>();
        Integer id;

        if (event.getHashtagList() != null) {
            for (EventHashtag eventH : event.getHashtagList()) {
                eventH.setEvent(null);
            }
            event.getHashtagList().clear();
            eventRepository.flush();
        } else {
            event.setHashtagList(new ArrayList<>());
        }

        for (String hashtag_content : eventDto.getHashtagList()) {

//            id = hashTagRepository.getHashTagByContent(hashtag_content).getId();
            hashTag = hashTagRepository.getHashTagByContent(hashtag_content);
            eventHashtag = new EventHashtag();
//            hashTag = hashTagRepository.findById(id).get();

            if (hashTag.getParentId() == 0) {    ///////////////////////hashtag level 1

                eventHashtag.setHashtag(hashTag);
                eventHashtag.setLevel(1);
                eventHashtag.setAssigned_hashtag(hashTag);
                eventHashtag.setEvent(event);

                hashtagList.add(eventHashtag);

            } else {  ///////////////////////hashtag level 2/3

                hashTagPrevious = hashTagRepository.findById(hashTag.getParentId()).get();
                previousEventHashtag = new EventHashtag();

                if (hashTagPrevious.getParentId() == 0) {   ///////////////////////hashtag level 2

                    previousEventHashtag.setHashtag(hashTagPrevious);
                    previousEventHashtag.setLevel(1);
                    previousEventHashtag.setAssigned_hashtag(hashTag);
                    previousEventHashtag.setEvent(event);

                    eventHashtag.setHashtag(hashTag);
                    eventHashtag.setLevel(2);
                    eventHashtag.setAssigned_hashtag(hashTag);
                    eventHashtag.setEvent(event);

                    hashtagList.add(eventHashtag);
                    hashtagList.add(previousEventHashtag);

                } else {   ///////////////////////hashtag level 3

                    hashTagPreviousPrevious = hashTagRepository.findById(hashTagPrevious.getParentId()).get();
                    previousPreviousEventHashtag = new EventHashtag();

                    if (hashTagPreviousPrevious.getParentId() == 0) {

                        previousPreviousEventHashtag.setHashtag(hashTagPreviousPrevious);
                        previousPreviousEventHashtag.setLevel(1);
                        previousPreviousEventHashtag.setAssigned_hashtag(hashTag);
                        previousPreviousEventHashtag.setEvent(event);

                        previousEventHashtag.setHashtag(hashTagPrevious);
                        previousEventHashtag.setLevel(2);
                        previousEventHashtag.setAssigned_hashtag(hashTag);
                        previousEventHashtag.setEvent(event);

                        eventHashtag.setHashtag(hashTag);
                        eventHashtag.setLevel(3);
                        eventHashtag.setAssigned_hashtag(hashTag);
                        eventHashtag.setEvent(event);

                        hashtagList.add(eventHashtag);
                        hashtagList.add(previousEventHashtag);
                        hashtagList.add(previousPreviousEventHashtag);
                    }
                }//level 3
            } //level 2/3
        }//for

        if (event.getHashtagList() == null) {
            event.setHashtagList(hashtagList);
        } else {
            event.getHashtagList().addAll(hashtagList);
        }
        return eventRepository.save(event);
//        return eventRepository.save(stock);
    }


    @Override
    public PagedDataDto filter(List<String> hash, List<String> name, List<String> location, List<String> org, List<Integer> mov, Integer page, Integer size) {

        List<EventDtoForMainList> dtoSearchList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Event> pageTuts = null;


        //to add filter body))
        pageTuts = eventRepository.findAllWithMovements(paging, mov); //tmp


        EventDtoForMainList eventDto;
        for (Event currEv : pageTuts) {
            eventDto = new EventDtoForMainList();
            EventConverter.convertToEventDtoForMainList(currEv, eventDto, this);
            dtoSearchList.add(eventDto);
        }
        PagedDataDto pdd = new PagedDataDto(pageTuts.getTotalElements(), dtoSearchList);
        return pdd;
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }
}
