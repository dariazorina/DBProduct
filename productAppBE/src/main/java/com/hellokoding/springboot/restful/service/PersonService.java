package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final ArticleRepository     articleRepository;
    private final PersonRepository      personRepository;
    private final EventRepository       eventRepository;
    private final OrgRepository         orgRepository;
    private final ScpaperRepository     scpaperRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(Math.toIntExact(id));
    }

    public Person save(Person stock) {
        return personRepository.save(stock);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(Math.toIntExact(id));
    }

    public void fillPersonTableFromArticle() {   ////step1: create table author
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
    }

    public void initializeReferenceBetweenAuthorAndArticle() {  //step2: create connecting table
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
    }

    public void fillPersonTableFromOrg() {   ////step1: create table author
        List<Org> all = orgRepository.findAll();

//        for (Article article : all) {
//            String author = article.getAuthor();
//            if (author != null) {
//                author = author.substring(1, author.length() - 1); //убираем { }
//                String[] split = author.split(","); //разделяем по "," на массив строк
//
//                for (String authorNameWithQuotes : split) {
//                    String authorName = authorNameWithQuotes.substring(1, authorNameWithQuotes.length() - 1);//del "
//                    Person authorByName = personRepository.getPersonByName(authorName); //ищем автора в БД
//                    if (authorByName == null) {
//                        Person s1 = new Person();
//                        s1.setName(authorName);
//                        personRepository.save(s1);
//                    }
//                }
//            }
//        }
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
    }

    public void fillPerosnTableFromScpaper() {   ////step1: create table author
        List<Scpaper> all = scpaperRepository.findAll();

//        for (Article article : all) {
//            String author = article.getAuthor();
//            if (author != null) {
//                author = author.substring(1, author.length() - 1); //убираем { }
//                String[] split = author.split(","); //разделяем по "," на массив строк
//
//                for (String authorNameWithQuotes : split) {
//                    String authorName = authorNameWithQuotes.substring(1, authorNameWithQuotes.length() - 1);//del "
//                    Person authorByName = personRepository.getPersonByName(authorName); //ищем автора в БД
//                    if (authorByName == null) {
//                        Person s1 = new Person();
//                        s1.setName(authorName);
//                        personRepository.save(s1);
//                    }
//                }
//            }
//        }
    }

    public void initializeReferenceBetweenAuthorAndScpaper() {  //step2: create connecting table
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



}
