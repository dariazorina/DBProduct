package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.UrlLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final ArticleRepository articleRepository;
    private final PersonRepository personRepository;
    private final EventRepository eventRepository;
    private final OrgRepository orgRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlLinkRepository linkRepository;


    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }

//    public Person save(Person stock) {
//        return personRepository.save(stock);
//    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    public List<Person> search(String q) {
        //todo add ignore case
        return personRepository.findBySurnameStartsWithOrderBySurnameDesc(q);
    }

    public Person save(Person stock) {

        HashTag hashTagByContent;
        HashTag hashTagWithID;
        List<HashTag> hashTagList = stock.getHashtagList();
        List<HashTag> hashTagListWithID = new ArrayList<>();


        UrlLink linkByContent;
        UrlLink linkWithID;
        List <UrlLink> linkList = stock.getLinkList();
        List<UrlLink> linkListWithID = new ArrayList<>();


        for (HashTag hashtag : hashTagList) {
            hashTagByContent = hashTagRepository.getHashTagByContent(hashtag.getContent()); //ищем хештег в БД
            if (hashTagByContent == null) {
                hashTagRepository.save(hashtag);

                hashTagWithID = hashTagRepository.getHashTagByContent(hashtag.getContent());
                hashTagListWithID.add(hashTagWithID);

            } else {
                hashTagListWithID.add(hashTagByContent);
            }
        }

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

        stock.setHashtagList(hashTagListWithID);
        stock.setLinkList(linkListWithID);

        return personRepository.save(stock);
    }

    ////utils
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
