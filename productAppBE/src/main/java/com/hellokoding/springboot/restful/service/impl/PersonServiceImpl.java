package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.PersonService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {

    private final ArticleRepository articleRepository;
    private final PersonRepository personRepository;
    private final PositionRepository positionRepository;
    private final EventRepository eventRepository;
    private final OrgRepository orgRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;
    private final HashTagRepository hashTagRepository;
    //    private final PersonHashtagRepository personHashtagRepository;
    private final UrlLinkRepository linkRepository;
    private final LocationRepository locationRepository;
//    private final LanguageRepository languageRepository;
    private final SnpRepository snpRepository;
    private final StatusRepository statusRepository;

    @Override
//    public List<Person> findAll() {
//        return personRepository.findAll();
//    }

    public List<NewPersonDto> findAll(Integer mov) {

        List<NewPersonDto> dtoAllPersonList = new ArrayList<>();
        List<Person> allPerson = personRepository.findAllWithMovement(mov);

        // hack for org creation, for relase
//        Random objGenerator = new Random();
//        int randomNumber = objGenerator.nextInt(100);
//        Org newOrg = new Org();
//        String name = "Org " + randomNumber;
//        newOrg.setName(name);
//
//        Country country = new Country();
//        newOrg.setCountry(country);
//        newOrg.getCountry().setId(1);
//        orgRepository.save(newOrg);

        NewPersonDto currentNewDtoP;
        for (Person p : allPerson) {
            currentNewDtoP = new NewPersonDto(p);
//            currentNewDtoP.newPersonDtoConverter(p);

            dtoAllPersonList.add(currentNewDtoP);
        }

        return dtoAllPersonList;
    }

    @Override
    public Optional<NewPersonDto> findById(Integer id) {

        Optional<Person> p = personRepository.findById(id);
        Optional<NewPersonDto> newPersonDto;

        newPersonDto = Optional.of(new NewPersonDto(p.get()));

//        newPersonDto = Optional.of(new NewPersonDto());
//        newPersonDto.get().newPersonDtoConverter(p.get());

        return newPersonDto;
    }


    public List<NewPersonDto> findByIds(List<Integer> idList) {

        List<NewPersonDto> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Person> p = personRepository.findById(id);
            NewPersonDto newPersonDto;

            if (p != null) {
                newPersonDto = new NewPersonDto(p.get());
                searchRes.add(newPersonDto);
            }
        }
        return searchRes;
    }

    @Override
    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<NewPersonDto> search(List<String> hash, List<String> surname, List<String> org, List<String> location) {
        List<NewPersonDto> dtoSearchList = new ArrayList<>();
        Set<Person> searchList = new HashSet<>();

        boolean isSingleFilter = false;
        int hashCurrentSize = 0;
        int orgCurrentSize = 0;
        int locationCurrentSize = 0;
        int surnameCurrentSize = 0;


        List<String> hashList = new ArrayList<>();
        List<String> authorList = new ArrayList<>();
        List<String> orgList = new ArrayList<>();
        List<String> locationList = new ArrayList<>();


        if (hash != null) {
            hashCurrentSize = hash.size();
        }
        if (surname != null) {
            surnameCurrentSize = surname.size();
        }
        if (location != null) {
            locationCurrentSize = location.size();
        }
        if (org != null) {
            orgCurrentSize = org.size();
        }

        if (hashCurrentSize >= 1) {
            isSingleFilter = true;

            if (surnameCurrentSize >= 1) {
                isSingleFilter = false;

            } else if (orgCurrentSize >= 1) {
                isSingleFilter = false;

            } else if (locationCurrentSize >= 1) {
                isSingleFilter = false;
            }
        } else {
            if (surnameCurrentSize >= 1) {
                isSingleFilter = true;

                if (orgCurrentSize >= 1) {
                    isSingleFilter = false;

                } else if (locationCurrentSize >= 1) {
                    isSingleFilter = false;
                }
            } else {
                if (orgCurrentSize >= 1) {
                    isSingleFilter = true;

                    if (locationCurrentSize >= 1) {
                        isSingleFilter = false;
                    }
                } else {
                    if (locationCurrentSize >= 1) {
                        isSingleFilter = true;
                    }
                }
            }
        }

        if (hash != null && !hash.isEmpty()) {
            for (String h : hash) hashList.add(h + "%");

            if (isSingleFilter) {
                for (String s : hashList) searchList.addAll(personRepository.findByHash(s));
            }
        }

        if (surname != null && !surname.isEmpty()) {
            for (String a : surname) authorList.add(a + "%");

            if (isSingleFilter) {
                for (String s : authorList) searchList.addAll(personRepository.findBySurname(s));
            }
        }

        if (org != null && !org.isEmpty()) {
            for (String o : org) orgList.add("%" + o + "%");

            if (isSingleFilter) {
                for (String s : orgList) searchList.addAll(personRepository.findByOrg(s));
            }
        }

        if (location != null && !location.isEmpty()) {
            for (String l : location) locationList.add(l + "%");

            if (isSingleFilter) {
                for (String s : locationList) searchList.addAll(personRepository.findByLocation(s));
            }
        }

        if (!isSingleFilter) {
            searchList = personRepository.findByFilters(
                    hashList.size() == 0 ? null : hashList.get(0).toLowerCase(),
                    authorList.size() == 0 ? null : authorList.get(0).toLowerCase(),
                    orgList.size() == 0 ? null : orgList.get(0).toLowerCase(),
                    locationList.size() == 0 ? null : locationList.get(0).toLowerCase());
        }

        NewPersonDto dtoPerson;
        for (Person p : searchList) {
            dtoPerson = new NewPersonDto(p);
            dtoSearchList.add(dtoPerson);
        }
        return dtoSearchList;
    }

    @Override
    public List<PersonDto> searchBySurname(String q, Integer mov) {

        List<Person> surnameSearchList = personRepository.findBySurnameAndMovement(q.toLowerCase() + "%", mov);//findBySurnameStartsWithIgnoreCase(q);
      //  List<Person> surnameRusSearchList = personRepository.findBySurnameRusAndMovement(q.toLowerCase() + "%", mov);//findBySurnameRusStartsWithIgnoreCase(q);
      //  List<Person> surnameEngSearchList = personRepository.findBySurnameEngAndMovement(q.toLowerCase() + "%", mov);//findBySurnameEngStartsWithIgnoreCase(q);

        Set<PersonDto> fooSet = new TreeSet<>();
        List<SurnameNamePatr> snpList;
        String dtoName = "";

        for (Person person : surnameSearchList) {
            snpList = person.getSnpList();
            for (SurnameNamePatr snp : snpList) {
                if (snp.getSurname() != null) {
                    dtoName = snp.getSurname();
                    if (snp.getName() != null) {
                        dtoName += " " + snp.getName();
                    }
                }
                PersonDto personDto = new PersonDto(person.getId(), dtoName);
                fooSet.add(personDto);
            }
        }

        List<PersonDto> finalList = new ArrayList<PersonDto>(fooSet);
        return finalList;
    }

    @Override
    public Person save(NewPersonDto personDto) {

//        HashTag hashTagByContent;
//        HashTag hashTagWithID;
//        List<HashTag> hashTagList = personDto.getHashtagList();
//        List<HashTag> hashTagListWithID = new ArrayList<>();
        //        for (HashTag hashtag : hashTagList) {
//            hashTagByContent = hashTagRepository.getHashTagByContent(hashtag.getContent()); //ищем хештег в БД
//            if (hashTagByContent == null) {
//                hashTagRepository.save(hashtag);
//
//                hashTagWithID = hashTagRepository.getHashTagByContent(hashtag.getContent());
//                hashTagListWithID.add(hashTagWithID);
//
//            } else {
//                hashTagListWithID.add(hashTagByContent);
//            }
//        }
        //        person.setHashtagList(hashTagListWithID);


//        UrlLink linkByContent;
//        UrlLink linkWithID;
        List<UrlLink> linkList = personDto.getLinkList();
        List<UrlLink> linkListWithID = new ArrayList<>();

//        for (UrlLink link : linkList) {
//            linkByContent = linkRepository.getUrlLinkByContent(link.getContent()); //ищем хештег в БД
//            if (linkByContent == null) {
//                linkRepository.save(link);
//
//                linkWithID = linkRepository.getUrlLinkByContent(link.getContent());
//                linkListWithID.add(linkWithID);
//
//            } else {
//                linkListWithID.add(linkByContent);
//            }
//        }

        LinkListIDCreation ll = new LinkListIDCreation(linkRepository);
        ll.getLinkListID(linkList, linkListWithID);  //todo - maybe.. to remove this class?


        Person person;// = new Person();

        if (personDto.getId() == null) {
            person = new Person();
        } else if (personRepository.findById(personDto.getId()).isPresent()) {
            person = personRepository.findById(personDto.getId()).get();
        } else
            return null;


        //todo with Location when remember the sense))
        if (personDto.getLocation_id() != null) { //to avoid hiber error when country is empty (from user form)
//            Country c = new Country();
//            person.setCountry(c);
//            person.getCountry().setId(personDto.getCountry_id());

            ItemConnectionDto l = new ItemConnectionDto();
            List<ItemConnectionDto> locationList = new ArrayList<>();
        }


        if (person.getLinkList() != null) {
//            for (UrlLink lnk : person.getLinkList()) {
//                lnk.setArticle(null);
//            }
            person.getLinkList().clear();
            personRepository.flush();
        } else {
            person.setLinkList(new ArrayList<>());
        }

        if (person.getLinkList() == null) {
            person.setLinkList(linkListWithID);
        } else {
            person.getLinkList().addAll(linkListWithID);
        }
        //person.setLinkList(linkListWithID);

        if (person.getMovementList() != null) {
            person.getMovementList().clear();
            personRepository.flush();
        } else {
            person.setMovementList(new ArrayList<>());
        }
        if (person.getMovementList() == null) {
            person.setMovementList(personDto.getMovementList());
        } else {
            person.getMovementList().addAll(personDto.getMovementList());
            //personRepository.flush();
        }


        List<SurnameNamePatr> snpList = new ArrayList<>();
        if (person.getSnpList() != null) {
            person.getSnpList().clear();
            personRepository.flush();
        }
//        else {
//            person.setSnpList(snpList);
//        }

        List<SnpDto> snpDtoList = personDto.getSnpList();
        SurnameNamePatr snp;
        for (SnpDto snpDto: snpDtoList){
            snp = new SurnameNamePatr();
            snp.setSurname(snpDto.getSurname());
            snp.setName(snpDto.getName());
            snp.setPatronymic(snpDto.getPatronymic());
            snp.setPriority(snpDto.getPriority());
            snpList.add(snp);
            snpRepository.save(snp);
        }
        snpRepository.flush();


        if (person.getSnpList() == null) {
            person.setSnpList(snpList);
        } else {
            person.getSnpList().addAll(snpList);
            //personRepository.flush();
        }

        person.setBirthYear(personDto.getBirthYear());
        person.setDeathYear(personDto.getDeathYear());
        person.setDescription(personDto.getDescription());
        person.setMiscellany(personDto.getMiscellany());
        person.setRgbSelection(personDto.getRowColor());

        Optional<Status> byName = statusRepository.getByName(personDto.getStatus());
        if (byName.isPresent()) {
            person.setStatus(byName.get());
        }


        if (person.getOccupation() != null) {
            person.getOccupation().clear();
            personRepository.flush();
        }

        Integer orgId;
        Position position;
        List<Position> occList = new ArrayList<>();
        for (PositionDto posDto : personDto.getPositionDtoList()) {

            orgId = posDto.getOrgId();
            if (orgRepository.findById(orgId).isPresent()) {
                position = new Position();
                position.setOrg(orgRepository.findById(orgId).get());
                position.setPerson(person);
                position.setPosition(posDto.getPosition());
                position.setComment(posDto.getComment());

                occList.add(position);
            }
        }

        if (person.getOccupation() == null) {
            person.setOccupation(occList);
        } else {
            person.getOccupation().addAll(occList);
        }

            ///location
        if (person.getLocationConnections() != null) {
            person.getLocationConnections().clear();
            personRepository.flush();
        }

        Integer locatonId;
        PersonLocationConnection locationConnection;
        List<PersonLocationConnection> locationConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : personDto.getLocationList()) {

            locatonId = connectionDto.getItemId();
            if (locationRepository.findById(locatonId).isPresent()) {
                locationConnection = new PersonLocationConnection();
                locationConnection.setLocation(locationRepository.findById(locatonId).get());
                locationConnection.setPerson(person);
                locationConnection.setConnection(connectionDto.getConnection());
                locationConnection.setComment(connectionDto.getComment());

                locationConnectionList.add(locationConnection);
            }
        }

        if (person.getLocationConnections() == null) {
            person.setLocationConnections(locationConnectionList);
        } else {
            person.getLocationConnections().addAll(locationConnectionList);
        }


        //isource
        if (person.getIsourceConnections() != null) {
            person.getIsourceConnections().clear();
            personRepository.flush();
        }

        Integer isourceId;
        PersonIsourceConnection isourceConnection;
        List<PersonIsourceConnection> isourceConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : personDto.getIsourceList()) {

            isourceId = connectionDto.getItemId();
            if (isourceRepository.findById(isourceId).isPresent()) {
                isourceConnection = new PersonIsourceConnection();
                isourceConnection.setIsource(isourceRepository.findById(isourceId).get());
                isourceConnection.setPerson(person);
                isourceConnection.setConnection(connectionDto.getConnection());
                isourceConnection.setComment(connectionDto.getComment());

                isourceConnectionList.add(isourceConnection);
            }
        }

        if (person.getIsourceConnections() == null) {
            person.setIsourceConnections(isourceConnectionList);
        } else {
            person.getIsourceConnections().addAll(isourceConnectionList);
        }

        //event
        if (person.getEventConnections() != null) {
            person.getEventConnections().clear();
            personRepository.flush();
        }

        Integer eventId;
        PersonIsourceConnection eventConnection;
        List<PersonIsourceConnection> eventConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : personDto.getEventList()) {

            eventId = connectionDto.getItemId();
            if (isourceRepository.findById(eventId).isPresent()) {
                eventConnection = new PersonIsourceConnection();
                eventConnection.setIsource(isourceRepository.findById(eventId).get());
                eventConnection.setPerson(person);
                eventConnection.setConnection(connectionDto.getConnection());
                eventConnection.setComment(connectionDto.getComment());

                eventConnectionList.add(eventConnection);
            }
        }

        if (person.getIsourceConnections() == null) {
            person.setIsourceConnections(eventConnectionList);
        } else {
            person.getIsourceConnections().addAll(eventConnectionList);
        }

        ///persons
        if (person.getPersonConnections() != null) {
            person.getPersonConnections().clear();
            personRepository.flush();
        }

        Integer personId;
        PersonPersonConnection personConnection;
        List<PersonPersonConnection> personConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : personDto.getPersonList()) {

            personId = connectionDto.getItemId();
            if (personRepository.findById(personId).isPresent()) {
                personConnection = new PersonPersonConnection();
                personConnection.setConnectedPerson(personRepository.findById(personId).get());
                personConnection.setPerson(person);
                personConnection.setConnection(connectionDto.getConnection());
                personConnection.setComment(connectionDto.getComment());

                personConnectionList.add(personConnection);
            }
        }

        if (person.getPersonConnections() == null) {
            person.setPersonConnections(personConnectionList);
        } else {
            person.getPersonConnections().addAll(personConnectionList);
        }


        /////////////////////person-hashtag////////////////////////
        PersonHashtag personHashtag, previousPersonHashtag, previousPreviousPersonHashtag;
        HashTag hashTag, hashTagPrevious, hashTagPreviousPrevious;
        List<PersonHashtag> hashtagList = new ArrayList<>();
        Integer id;

        if (person.getHashtagList() != null) {
            for (PersonHashtag pt : person.getHashtagList()) {
                pt.setPerson(null);
            }
            person.getHashtagList().clear();
            personRepository.flush();
        } else {
            person.setHashtagList(new ArrayList<>());
        }

        for (String hashtag_content : personDto.getHashtagList()) {

            id = hashTagRepository.getHashTagByContent(hashtag_content).getId();
            personHashtag = new PersonHashtag();
            hashTag = hashTagRepository.findById(id).get();

            if (hashTag.getParentId() == 0) {    ///////////////////////hashtag level 1

                personHashtag.setHashtag(hashTag);
                personHashtag.setLevel(1);
                personHashtag.setAssigned_hashtag(hashTag);
                personHashtag.setPerson(person);

                hashtagList.add(personHashtag);

            } else {  ///////////////////////hashtag level 2/3

                hashTagPrevious = hashTagRepository.findById(hashTag.getParentId()).get();
                previousPersonHashtag = new PersonHashtag();

                if (hashTagPrevious.getParentId() == 0) {   ///////////////////////hashtag level 2

                    previousPersonHashtag.setHashtag(hashTagPrevious);
                    previousPersonHashtag.setLevel(1);
                    previousPersonHashtag.setAssigned_hashtag(hashTag);
                    previousPersonHashtag.setPerson(person);

                    personHashtag.setHashtag(hashTag);
                    personHashtag.setLevel(2);
                    personHashtag.setAssigned_hashtag(hashTag);
                    personHashtag.setPerson(person);

                    hashtagList.add(personHashtag);
                    hashtagList.add(previousPersonHashtag);

                } else {   ///////////////////////hashtag level 3

                    hashTagPreviousPrevious = hashTagRepository.findById(hashTagPrevious.getParentId()).get();
                    previousPreviousPersonHashtag = new PersonHashtag();

                    if (hashTagPreviousPrevious.getParentId() == 0) {

                        previousPreviousPersonHashtag.setHashtag(hashTagPreviousPrevious);
                        previousPreviousPersonHashtag.setLevel(1);
                        previousPreviousPersonHashtag.setAssigned_hashtag(hashTag);
                        previousPreviousPersonHashtag.setPerson(person);

                        previousPersonHashtag.setHashtag(hashTagPrevious);
                        previousPersonHashtag.setLevel(2);
                        previousPersonHashtag.setAssigned_hashtag(hashTag);
                        previousPersonHashtag.setPerson(person);

                        personHashtag.setHashtag(hashTag);
                        personHashtag.setLevel(3);
                        personHashtag.setAssigned_hashtag(hashTag);
                        personHashtag.setPerson(person);

                        hashtagList.add(personHashtag);
                        hashtagList.add(previousPersonHashtag);
                        hashtagList.add(previousPreviousPersonHashtag);
                    }
                }//level 3
            } //level 2/3
        }//for

        if (person.getHashtagList() == null) {
            person.setHashtagList(hashtagList);
        } else {
            person.getHashtagList().addAll(hashtagList);
        }


        //old, first
        //Position pos = person.getOccupation1().get(0);
        //person.getOccupation1().clear();


//        Position pos = new Position();
//        pos.setPosition("test position =))))");
//        Org org = orgRepository.findById(3).get();
//        org.addPosition(person.getOccupation1());
//        person.addPosition(pos);


        //Мое, работало))
//        Position pos =  person.getOccupation1().get(0);
//        pos.setPerson(person);


//        BufferedImage image = null;


//******************working version
//        String base64Image;
//        if (personDto.getPhoto() != null) {
//            String[] base64ImageParts = personDto.getPhoto().split(",");
//            if (base64ImageParts.length > 1) {
//                base64Image = base64ImageParts[1];
//            } else {
//                base64Image = base64ImageParts[0];
//            }
//            byte[] imageByte;
//            try {
//                imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

                // Converting a Base64 String into Image byte array
//////            imageByte = Base64.getDecoder().decode(personDto.getPhoto());
//                person.setPhoto(imageByte);

//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//************************




//        byte[] imageByte;
//        try {
//            BASE64Decoder decoder = new BASE64Decoder();
//            imageByte = decoder.decodeBuffer(personDto.getPhoto());

//            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//            image = ImageIO.read(bis);
//            bis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return personRepository.save(person);
    }


    ///////////////////////////////////////utils///////////////////////////////////////////////////////
/*    public void fillPersonTableFromArticle() {   ////step1: create table author
        List<Article> all = articleRepository.findAll();

        for (Article article : all) {
            String author = article.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1); //убираем { }
                String[] split = author.split(","); //разделяем по "," на массив строк

                for (String authorNameWithQuotes : split) {
                    String authorName = authorNameWithQuotes.substring(1, authorNameWithQuotes.length() - 1);//del "
                    Person authorByName = personRepository.getPersonByName(authorName); //ищем автора в БД
                    if (authorByName == null) {
                        Person s1 = new Person();
                        s1.setName(authorName);
                        s1.setMovement_id(1);
                        s1.setCountry_id(1);
                        personRepository.save(s1);
                    }
                }
            }
        }
    }*/

/*    public void initializeReferenceBetweenAuthorAndArticle() {  //step2: create connecting table
        List<Article> all = articleRepository.findAll();

        for (Article article : all) {
            String author = article.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1);
                String[] split = author.split(",");
                List<Person> authors = new LinkedList<>();
                for (String s : split) {
                    String authorName = s.substring(1, s.length() - 1);
                    Person authorByName = personRepository.getPersonByName(authorName);
                    authors.add(authorByName);
                }
                article.setAuthorList(authors);
                articleRepository.save(article);
            }
        }
    }

    public void fillPersonTableFromEvent() {
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String actor = event.getActors();
            if (actor != null) {
                actor = actor.substring(1, actor.length() - 1); //убираем { }
                String[] split = actor.split(","); //разделяем по "," на массив строк

                for (String actorNameWithQuotes : split) {
                    String actorName = actorNameWithQuotes.substring(1, actorNameWithQuotes.length() - 1);//del "
                    Person actorByName = personRepository.getPersonByName(actorName); //ищем автора в БД
                    if (actorByName == null) {
                        Person s1 = new Person();
                        s1.setName(actorName);
                        s1.setMovement_id(1);
                        s1.setCountry_id(1);
                        personRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenActorAndEvent() {  //step2: create connecting table
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String events = event.getActors();
            if (events != null) {
                events = events.substring(1, events.length() - 1);
                String[] split = events.split(",");
                List<Person> authors = new LinkedList<>();
                for (String s : split) {
                    String actorName = s.substring(1, s.length() - 1);
                    Person actorByName = personRepository.getPersonByName(actorName);
                    authors.add(actorByName);
                }
                event.setActorList(authors);
                eventRepository.save(event);
            }
        }
    }*/

/*    public void fillPersonTableFromScpaper() {
//        Optional<Scpaper> all = scpaperRepository.findById(10); //All();
        List<Scpaper> all = scpaperRepository.findAll();

        for (Scpaper scpaper : all) {
            String author = scpaper.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1); //убираем { }
                String[] split = author.split(","); //разделяем по "," на массив строк

                for (String authorNameWithQuotes : split) {
                    String authorName = authorNameWithQuotes.substring(1, authorNameWithQuotes.length() - 1);//del "
                    Person authorByName = personRepository.getPersonByName(authorName); //ищем автора в БД
                    if (authorByName == null) {
                        Person s1 = new Person();
                        s1.setName(authorName);
                        s1.setMovement_id(1);
                        s1.setCountry_id(1);
                        // System.out.println(s1.getName());
                        personRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenAuthorAndScpaper() {
        List<Scpaper> all = scpaperRepository.findAll();

        for (Scpaper scpaper : all) {
            String author = scpaper.getAuthor();
            if (author != null) {
                author = author.substring(1, author.length() - 1);
                String[] split = author.split(",");
                List<Person> authors = new LinkedList<>();
                for (String s : split) {
                    String authorName = s.substring(1, s.length() - 1);
                    Person authorByName = personRepository.getPersonByName(authorName);
                    authors.add(authorByName);
                }
                scpaper.setAuthorList(authors);
                scpaperRepository.save(scpaper);
            }
        }
    }


    public void fillPersonTableFromIsource() {
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String moderator = isource.getModerator();
            if (moderator != null) {
                moderator = moderator.substring(1, moderator.length() - 1); //убираем { }
                String[] split = moderator.split(","); //разделяем по "," на массив строк

                for (String authorName : split) {
                    Person authorByName = personRepository.getPersonByName(authorName); //ищем автора в БД
                    if (authorByName == null) {
                        Person s1 = new Person();
                        s1.setName(authorName);
                        s1.setMovement_id(1);
                        s1.setCountry_id(1);
                        personRepository.save(s1);
                    }
                }
            }
        }
    }*/

/*    public void initializeReferenceBetweenModeratorAndIsource() {
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String moderator = isource.getModerator();
            if (moderator != null) {
                moderator = moderator.substring(1, moderator.length() - 1); ///{ }
                String[] split = moderator.split(",");
                List<Person> moderators = new LinkedList<>();
                for (String s : split) {
                    Person authorByName = personRepository.getPersonByName(s);
                    moderators.add(authorByName);
                }
                isource.setModeratorList(moderators);
                isourceRepository.save(isource);
            }
        }
    }

    public void fillPersonTableFromOrg() {
        List<Org> all = orgRepository.findAll();

        for (Org org : all) {
            String actor = org.getActors();
            if (actor != null) {
                actor = actor.substring(1, actor.length() - 1); //убираем { }
                String[] split = actor.split(","); //разделяем по "," на массив строк

                for (String actorNameWithQuotes : split) {
                    String actorName = actorNameWithQuotes.substring(1, actorNameWithQuotes.length() - 1);//del "
                    Person actorByName = personRepository.getPersonByName(actorName); //ищем автора в БД
                    if (actorByName == null) {
                        Person s1 = new Person();
                        s1.setName(actorName);
                        s1.setMovement_id(1);
                        s1.setCountry_id(1);
                        personRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenActorAndOrg() {  //step2: create connecting table
        List<Org> all = orgRepository.findAll();

        for (Org org : all) {
            String actors = org.getActors();
            if (actors != null) {
                actors = actors.substring(1, actors.length() - 1);
                String[] split = actors.split(",");
                List<Person> authors = new LinkedList<>();
                for (String s : split) {
                    String actorName = s.substring(1, s.length() - 1);
                    Person actorByName = personRepository.getPersonByName(actorName);
                    authors.add(actorByName);
                }
                org.setActorList(authors);
                orgRepository.save(org);
            }
        }
    }*/
}
