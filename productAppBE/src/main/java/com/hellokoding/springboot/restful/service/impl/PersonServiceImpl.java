package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.PersonService;
import com.hellokoding.springboot.restful.service.UrlLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {

    private final ArticleRepository articleRepository;
    private final PersonRepository personRepository;
    private final PersonPersonRepository personPersonRepository;

    private final EventRepository eventRepository;
    private final OrgRepository orgRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlLinkRepository linkRepository;
    private final LocationRepository locationRepository;
    private final SnpRepository snpRepository;
    private final StatusRepository statusRepository;

    private final UrlLinkService urlLinkService;

    @Override
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

    public List<IdContentDto> findByIds(List<Integer> idList) {

        List<Person> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Person> p = personRepository.findById(id);

            if (p != null) {
                searchRes.add(p.get());
            }
        }
        return transformOriginToDto(searchRes);
    }

    public List<NameConnectionDto> findByIdsAndSymmetrically(List<Integer> idList, Integer itemId) {

        Person connectedPerson, person;
        String dtoName = "", connection = "", comment = "";
        List<NameConnectionDto> finalList = new ArrayList<>();

        List<PersonPersonConnection> searchResSymm;// = new ArrayList<>();

        Optional<Person> personOpt = personRepository.findById(itemId);
        if (personOpt.isPresent()) {
            person = personOpt.get();             //   searchRes.add(l.get());

            searchResSymm = personPersonRepository.findByIdSymm(itemId); //searching symm connections for this itemId
            for (Integer id : idList) { //simple connections
                Optional<Person> connPersonOpt = personRepository.findById(id);

                if (connPersonOpt.isPresent()) {
                    connectedPerson = connPersonOpt.get();             //   searchRes.add(l.get());
                    if (connectedPerson.getSnpList() != null) {
                        for (SurnameNamePatr name : connectedPerson.getSnpList()) {
                            if (name.getPriority() == 1) {
                                dtoName = name.getSurname();
                                dtoName += " " + name.getName();
                                if (name.getPatronymic() != null) {
                                    dtoName += " " + name.getPatronymic();
                                }
                            }
                        }
                    }
                    for (PersonPersonConnection personPersonConnection : person.getPersonConnections()) {
                        if (personPersonConnection.getConnectedPerson().getId().equals(connectedPerson.getId())) {
                            connection = personPersonConnection.getConnection();

                            if (personPersonConnection.getComment() != null) {
                                if (personPersonConnection.getComment().length() != 0) {
                                    comment = personPersonConnection.getComment();
                                }
                            }
                        }
                    }
                    NameConnectionDto personDto = new NameConnectionDto(connectedPerson.getId(), dtoName, connection, comment);
                    dtoName = "";
                    connection = "";
                    comment = "";
                    finalList.add(personDto);
                }
            }//for

            for (PersonPersonConnection personPersonConnection : searchResSymm) {
                if (personPersonConnection.getConnectedPerson().getId().equals(person.getId())) {

                    if (personPersonConnection.getPerson().getSnpList() != null) {
                        for (SurnameNamePatr name : personPersonConnection.getPerson().getSnpList()) {
                            if (name.getPriority() == 1) {
                                dtoName = name.getSurname();
                                dtoName += " " + name.getName();
                                if (name.getPatronymic() != null) {
                                    dtoName += " " + name.getPatronymic();
                                }
                            }
                        }
                    }

                    connection = personPersonConnection.getConnection();
                    if (personPersonConnection.getComment() != null) {
                        if (personPersonConnection.getComment().length() != 0) {
                            comment = personPersonConnection.getComment();
                        }
                    }
                    NameConnectionDto personDto = new NameConnectionDto(personPersonConnection.getPerson().getId(), dtoName, connection, comment);
                    finalList.add(personDto);
                    dtoName = "";
                    connection = "";
                    comment = "";
                }
            }
        }
        return finalList;
    }

    public List<IdContentDto> transformOriginToDto(List<Person> list) {
        Set<IdContentDto> fooSet = new TreeSet<>();
        String dtoName = "";

        for (Person person : list) {
            if (person.getSnpList() != null) {
                for (SurnameNamePatr name : person.getSnpList()) {
                    if (name.getPriority() == 1) {
                        dtoName += name.getSurname();

                        if (name.getName() != null) {
                            dtoName += " " + name.getName();
                        }
                    }
                }
            }
            IdContentDto pDto = new IdContentDto(person.getId(), dtoName);
            dtoName = "";
            fooSet.add(pDto);
        }
        List<IdContentDto> finalList = new ArrayList<>(fooSet);
        return finalList;
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
    public List<IdContentDto> searchBySurname(String q) {

        List<Person> surnameSearchList = personRepository.findBySurname(q.toLowerCase() + "%");//findBySurnameStartsWithIgnoreCase(q);
        //  List<Person> surnameRusSearchList = personRepository.findBySurnameRusAndMovement(q.toLowerCase() + "%", mov);//findBySurnameRusStartsWithIgnoreCase(q);
        //  List<Person> surnameEngSearchList = personRepository.findBySurnameEngAndMovement(q.toLowerCase() + "%", mov);//findBySurnameEngStartsWithIgnoreCase(q);

//        Set<PersonDto> fooSet = new TreeSet<>();
//        List<SurnameNamePatr> snpList;
//        String dtoName = "";
//
//        for (Person person : surnameSearchList) {
//            snpList = person.getSnpList();
//            for (SurnameNamePatr snp : snpList) {
//                if (snp.getSurname() != null) {
//                    dtoName = snp.getSurname();
//                    if (snp.getName() != null) {
//                        dtoName += " " + snp.getName();
//                    }
//                }
//                PersonDto personDto = new PersonDto(person.getId(), dtoName);
//                fooSet.add(personDto);
//            }
//        }
//
//        List<PersonDto> finalList = new ArrayList<PersonDto>(fooSet);
        return transformOriginToDto(surnameSearchList);
    }

    @Override
    public Person save(NewPersonDto personDto) {

        List<UrlLink> linkList = personDto.getLinkList();
        List<UrlLink> linkListWithID = urlLinkService.getLinkListID(linkList);
        Person person;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(username);

        if (personDto.getId() == null) {
            person = new Person();
        } else if (personRepository.findById(personDto.getId()).isPresent()) {
            person = personRepository.findById(personDto.getId()).get();
        } else
            return null;


//        //todo with Location when remember the sense))
//        if (personDto.getLocation_id() != null) {        //to avoid hiber error when country is empty (from user form)
//            List<ItemConnectionDto> locationList = new ArrayList<>();
//        }


        if (person.getLinkList() != null) {
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


        List<SurnameNamePatr> currentPersonSnpList = null;
        if (person.getSnpList() != null) {
            currentPersonSnpList = new ArrayList<>(person.getSnpList());
        }


        if (currentPersonSnpList != null) {
            person.getSnpList().clear();
            personRepository.flush();

            for (SurnameNamePatr el : currentPersonSnpList) {
                snpRepository.deleteById(el.getId());
            }
            snpRepository.flush();
        }

        List<SurnameNamePatr> snpList = new ArrayList<>();
        List<SnpDto> snpDtoList = personDto.getSnpList();
        SurnameNamePatr snp;

        for (SnpDto snpDto : snpDtoList) {
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
        }
        //personRepository.flush();//??

        person.setBirthYear(personDto.getBirthYear());
        person.setDeathYear(personDto.getDeathYear());
        person.setDescription(personDto.getDescription());
        person.setMiscellany(personDto.getMiscellany());
        person.setRgbSelection(personDto.getRowColor());

        Optional<Status> byName = statusRepository.getByName(personDto.getStatus());
        if (byName.isPresent()) {
            person.setStatus(byName.get());
        }


        if (person.getOrgConnections() != null) {
            person.getOrgConnections().clear();
            personRepository.flush();
        }

        Integer orgId;
        OrgPersonConnection position;
        List<OrgPersonConnection> occList = new ArrayList<>();
        for (ItemConnectionDto posDto : personDto.getOrgList()) {

            orgId = posDto.getItemId();
            if (orgRepository.findById(orgId).isPresent()) {
                position = new OrgPersonConnection();
                position.setOrg(orgRepository.findById(orgId).get());
                position.setPerson(person);
                position.setConnection(posDto.getConnection());
                position.setComment(posDto.getComment());

                occList.add(position);
            }
        }

        if (person.getOrgConnections() == null) {
            person.setOrgConnections(occList);
        } else {
            person.getOrgConnections().addAll(occList);
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


//        //isource
//        if (person.getIsourceConnections() != null) {
//            person.getIsourceConnections().clear();
//            personRepository.flush();
//        }
//
//        Integer isourceId;
//        PersonIsourceConnection isourceConnection;
//        List<PersonIsourceConnection> isourceConnectionList = new ArrayList<>();
//        for (ItemConnectionDto connectionDto : personDto.getIsourceList()) {
//
//            isourceId = connectionDto.getItemId();
//            if (isourceRepository.findById(isourceId).isPresent()) {
//                isourceConnection = new PersonIsourceConnection();
//                isourceConnection.setIsource(isourceRepository.findById(isourceId).get());
//                isourceConnection.setPerson(person);
//                isourceConnection.setConnection(connectionDto.getConnection());
//                isourceConnection.setComment(connectionDto.getComment());
//
//                isourceConnectionList.add(isourceConnection);
//            }
//        }
//
//        if (person.getIsourceConnections() == null) {
//            person.setIsourceConnections(isourceConnectionList);
//        } else {
//            person.getIsourceConnections().addAll(isourceConnectionList);
//        }

        //event
//        if (person.getEventConnections() != null) {
//            person.getEventConnections().clear();
//            personRepository.flush();
//        }
//
//        Integer eventId;
//        PersonIsourceConnection eventConnection;
//        List<PersonIsourceConnection> eventConnectionList = new ArrayList<>();
//        for (ItemConnectionDto connectionDto : personDto.getEventList()) {
//
//            eventId = connectionDto.getItemId();
//            if (isourceRepository.findById(eventId).isPresent()) {
//                eventConnection = new PersonIsourceConnection();
//                eventConnection.setIsource(isourceRepository.findById(eventId).get());
//                eventConnection.setPerson(person);
//                eventConnection.setConnection(connectionDto.getConnection());
//                eventConnection.setComment(connectionDto.getComment());
//
//                eventConnectionList.add(eventConnection);
//            }
//        }
//
//        if (person.getIsourceConnections() == null) {
//            person.setIsourceConnections(eventConnectionList);
//        } else {
//            person.getIsourceConnections().addAll(eventConnectionList);
//        }


        ///persons
        if (person.getPersonConnections() != null) {
            person.getPersonConnections().clear();
            personRepository.flush();
        }

        Integer connectedPersonId;
        boolean isSymmConnection = false;
        Person connectedPerson = null;

        PersonPersonConnection personPersonConnection;
        PersonPersonConnection connectedPersonConnection;

        List<PersonPersonConnection> personPersonList = new ArrayList<>();
        List<PersonPersonConnection> connectedPersonList = new ArrayList<>();
        List<PersonPersonConnection> symmConnectionList;

        for (ItemConnectionDto connectionDto : personDto.getPersonList()) {
            connectedPersonId = connectionDto.getItemId();
            if (personRepository.findById(connectedPersonId).isPresent()) {

                symmConnectionList = personPersonRepository.findByIdSimple(connectedPersonId);
                for (PersonPersonConnection personPersonConn : symmConnectionList) {
                    if (personPersonConn.getConnectedPerson().getId().equals(person.getId())) {
                        isSymmConnection = true;
                        break;
                    }
                }
                if (!isSymmConnection) {

                    personPersonConnection = new PersonPersonConnection();
                    personPersonConnection.setConnectedPerson(personRepository.findById(connectedPersonId).get());
                    personPersonConnection.setPerson(person);
                    personPersonConnection.setConnection(connectionDto.getConnection());
                    personPersonConnection.setComment(connectionDto.getComment());

                    personPersonList.add(personPersonConnection);

                } else {//save connection for material
                    connectedPerson = personRepository.findById(connectedPersonId).get();

                    connectedPersonConnection = new PersonPersonConnection();
                    connectedPersonConnection.setPerson(personRepository.findById(connectedPersonId).get());
                    connectedPersonConnection.setConnectedPerson(person);
                    connectedPersonConnection.setConnection(connectionDto.getConnection());
                    connectedPersonConnection.setComment(connectionDto.getComment());

                    connectedPersonList.add(connectedPersonConnection);

                    if (connectedPerson.getPersonConnections() == null) {
                        connectedPerson.setPersonConnections(connectedPersonList);
                    } else {
                        //remove already existed entities
                        List<PersonPersonConnection> elList = new ArrayList<>();
                        for (PersonPersonConnection personPersonConn : connectedPerson.getPersonConnections()) {
                            if (personPersonConn.getConnectedPerson().getId().equals(person.getId())) {
                                elList.add(personPersonConn);
                            }
                        }
                        connectedPerson.getPersonConnections().removeAll(elList);
                        personRepository.flush();
                        connectedPerson.getPersonConnections().addAll(connectedPersonList);
                    }
                }
                isSymmConnection = false;
            }
        }

        if (person.getPersonConnections() == null) {
            person.setPersonConnections(personPersonList);
        } else {
            person.getPersonConnections().addAll(personPersonList);
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

        if (connectedPerson != null)
            personRepository.save(connectedPerson);
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
