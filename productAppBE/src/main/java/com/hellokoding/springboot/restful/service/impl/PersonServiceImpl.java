package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.NewPersonDto;
import com.hellokoding.springboot.restful.model.dto.PersonDto;
import com.hellokoding.springboot.restful.model.dto.PositionDto;
import com.hellokoding.springboot.restful.service.PersonService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

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

    @Override
//    public List<Person> findAll() {
//        return personRepository.findAll();
//    }

    public List<NewPersonDto> findAll() {

        List<NewPersonDto> dtoAllPersonList = new ArrayList<>();
        List<Person> allPerson = personRepository.findAll();

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

//    public Person save(Person stock) {
//        return personRepository.save(stock);
//    }

    @Override
    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonDto> search(String q) {

        List<Person> surnameSearchList = personRepository.findBySurnameStartsWithIgnoreCase(q);
        List<Person> surnameRusSearchList = personRepository.findBySurnameRusStartsWithIgnoreCase(q);
        List<Person> surnameEngSearchList = personRepository.findBySurnameEngStartsWithIgnoreCase(q);

        Set<PersonDto> fooSet = new TreeSet<>();
        String dtoName;

        for (Person person : surnameSearchList) {
            dtoName = person.getSurname();
            if (person.getName() != null) {
                dtoName += " " + person.getName();
            }
            PersonDto personDto = new PersonDto(person.getId(), dtoName);
            fooSet.add(personDto);
        }

        for (Person person : surnameRusSearchList) {
            dtoName = person.getSurnameRus();
            if (person.getNameRus() != null) {
                dtoName += " " + person.getNameRus();
            }
            PersonDto personDto = new PersonDto(person.getId(), dtoName);
            fooSet.add(personDto);
        }

        for (Person person : surnameEngSearchList) {
            dtoName = person.getSurnameEng();
            if (person.getNameEng() != null) {
                dtoName += " " + person.getNameEng();
            }
            PersonDto personDto = new PersonDto(person.getId(), dtoName);
            fooSet.add(personDto);
        }


//        if (surnameSearchList.size() > 0) {
//            fooSet =
//
//            if (surnameRusSearchList.size() > 0) {
//                fooSet.addAll(surnameRusSearchList);
//
//                if (surnameEngSearchList.size() > 0){
//                    fooSet.addAll(surnameEngSearchList);
//                }
//            }
//        } else {
//            if (surnameRusSearchList.size() > 0) {
//                fooSet = new LinkedHashSet<>(surnameRusSearchList);
//
//                if (surnameEngSearchList.size() > 0) {
//                    fooSet.addAll(surnameEngSearchList);
//                }
//            } else {
//                fooSet = new LinkedHashSet<>(surnameEngSearchList);
//            }
//        }

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

        UrlLink linkByContent;
        UrlLink linkWithID;
        List<UrlLink> linkList = personDto.getLinkList();
        List<UrlLink> linkListWithID = new ArrayList<>();

        for (UrlLink link : linkList) {
            linkByContent = linkRepository.getUrlLinkByContent(link.getContent()); //ищем хештег в БД
            if (linkByContent == null) {
                linkRepository.save(link);

                linkWithID = linkRepository.getUrlLinkByContent(link.getContent());
                linkListWithID.add(linkWithID);

            } else {
                linkListWithID.add(linkByContent);
            }
        }

        Person person;// = new Person();

        if (personDto.getId() == null) {
            person = new Person();
        } else if (personRepository.findById(personDto.getId()).isPresent()) {
            person = personRepository.findById(personDto.getId()).get();
        } else
            return null;

        if (personDto.getCountry_id() != null) { //to avoid hiber error when country is empty (from user form)
            Country c = new Country();
            person.setCountry(c);
            person.getCountry().setId(personDto.getCountry_id());
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

        person.setSurname(personDto.getSurname());
        person.setName(personDto.getName());
        person.setPatronymic(personDto.getPatronymic());
        person.setBirthYear(personDto.getBirthYear());
        person.setDeathYear(personDto.getDeathYear());
        person.setSurnameEng(personDto.getSurnameEng());л
        person.setNameEng(personDto.getNameEng());
        person.setSurnameRus(personDto.getSurnameRus());
        person.setNameRus(personDto.getNameRus());
        person.setSettlement(personDto.getSettlement());
        person.setDescription(personDto.getDescription());
        person.setMiscellany(personDto.getMiscellany());



        if (person.getOccupation() != null) {
            person.getOccupation().clear();
            personRepository.flush();
        }

        Integer orgId;
        Position position;
        List<Position> occList = new ArrayList<>();
        for (PositionDto posDto : personDto.getTestList()) {

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


        String base64Image;
        if (personDto.getPhoto() != null) {
            String[] base64ImageParts = personDto.getPhoto().split(",");
            if (base64ImageParts.length > 1) {
                base64Image = base64ImageParts[1];
            } else  {
                base64Image = base64ImageParts[0];
            }

            byte[] imageByte;
            try {
                imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

                // Converting a Base64 String into Image byte array
//            imageByte = Base64.getDecoder().decode(personDto.getPhoto());
                person.setPhoto(imageByte);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
