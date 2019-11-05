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
public class UrlLinkService {

    private final UrlLinkRepository urlLinkRepository;
    private final ArticleRepository articleRepository;
    private final EventRepository eventRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;


    public List<UrlLink> findAll() {
        return urlLinkRepository.findAll();
    }

    public Optional<UrlLink> findById(Long id) {
        return urlLinkRepository.findById(Math.toIntExact(id));
    }

    public UrlLink save(UrlLink stock) {
        return urlLinkRepository.save(stock);
    }

    public void deleteById(Long id) {
        urlLinkRepository.deleteById(Math.toIntExact(id));
    }

    public void fillLinkTableFromArticle() {   //step1: create link table
        List<Article> all = articleRepository.findAll();

        for (Article article : all) {
            String links = article.getUrl_links();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link); //ищем link в БД
                    if (linkByContent == null) {
                        UrlLink s1 = new UrlLink();
                        s1.setContent(link);
                        urlLinkRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenLinkAndArticle() {  //step2: create connecting table
        List<Article> all = articleRepository.findAll();

        for (Article article : all) {
            String links = article.getUrl_links();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                List<UrlLink> linkList = new LinkedList<>();
                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link);
                    linkList.add(linkByContent);
                }
                article.setLinkList(linkList);
                articleRepository.save(article);
            }
        }
    }

    public void fillLinkTableFromEvent() {
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String links = event.getUrl();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link); //ищем link в БД
                    if (linkByContent == null) {
                        UrlLink s1 = new UrlLink();
                        s1.setContent(link);
                        urlLinkRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenLinkAndEvent() {
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String links = event.getUrl();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                List<UrlLink> linkList = new LinkedList<>();
                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link);
                    linkList.add(linkByContent);
                }
                event.setLinkList(linkList);
                eventRepository.save(event);
            }
        }
    }

    public void fillLinkTableFromScpaper() {
        List<Scpaper> all = scpaperRepository.findAll();

        for (Scpaper scpaper : all) {
            String links = scpaper.getUrl();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link); //ищем link в БД
                    if (linkByContent == null) {
                        UrlLink s1 = new UrlLink();
                        s1.setContent(link);
                        urlLinkRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenLinkAndScpaper() {
        List<Scpaper> all = scpaperRepository.findAll();

        for (Scpaper scpaper : all) {
            String links = scpaper.getUrl();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                List<UrlLink> linkList = new LinkedList<>();
                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link);
                    linkList.add(linkByContent);
                }
                scpaper.setLinkList(linkList);
                scpaperRepository.save(scpaper);
            }
        }
    }

    public void fillLinkTableFromIsource() {
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String links = isource.getUrl();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link); //ищем link в БД
                    if (linkByContent == null) {
                        UrlLink s1 = new UrlLink();
                        s1.setContent(link);
                        urlLinkRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenLinkAndIsource() {
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String links = isource.getUrl();
            if (links != null) {
                links = links.substring(1, links.length() - 1); //убираем { }
                String[] split = links.split(","); //разделяем по "," на массив строк

                List<UrlLink> linkList = new LinkedList<>();
                for (String link : split) {
                    UrlLink linkByContent = urlLinkRepository.getUrlLinkByContent(link);
                    linkList.add(linkByContent);
                }
                isource.setLinkList(linkList);
                isourceRepository.save(isource);
            }
        }
    }
}
