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
public class HashTagService {

    private final HashTagRepository hashTagRepository;
    private final ArticleRepository articleRepository;
    private final EventRepository eventRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;


    public List<HashTag> findAll() {
        return hashTagRepository.findAll();
    }

    public Optional<HashTag> findById(Long id) {
        return hashTagRepository.findById(Math.toIntExact(id));
    }

    public HashTag save(HashTag stock) {
        return hashTagRepository.save(stock);
    }

    public void deleteById(Long id) {
        hashTagRepository.deleteById(Math.toIntExact(id));
    }

    public void fillHashTagTableFromArticle() {   ////step1: fill hashtag table
        List<Article> all = articleRepository.findAll();

        for (Article article : all) {
            String hashtag = article.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1); //убираем { }
                String[] split = hashtag.split(","); //разделяем по "," на массив строк

                for (String hashTagWithSharp : split) {
                    String pureHashtag = hashTagWithSharp.substring(1, hashTagWithSharp.length()); //del #
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag); //ищем хештег в БД
                    if (hashTagByContent == null) {
                        HashTag s1 = new HashTag();
                        s1.setContent(pureHashtag);
                        hashTagRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenHashTagAndArticle() { //step2: create connecting table
        List<Article> all = articleRepository.findAll();

        for (Article article : all) {
            String hashtag = article.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1);
                String[] split = hashtag.split(",");
                List<HashTag> hashtags = new LinkedList<>();
                for (String s : split) {
                    String pureHashtag = s.substring(1, s.length());
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag);
                    hashtags.add(hashTagByContent);
                }
                article.setHashtagList(hashtags);
                articleRepository.save(article);
            }
        }
    }

    public void fillHashTagTableFromEvent() {  //fill hashtag table
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String hashtag = event.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1); //убираем { }
                String[] split = hashtag.split(","); //разделяем по "," на массив строк

                for (String hashTagWithSharp : split) {
                    String pureHashtag = hashTagWithSharp.substring(1, hashTagWithSharp.length()); //del #
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag); //ищем хештег в БД
                    if (hashTagByContent == null) {
                        HashTag s1 = new HashTag();
                        s1.setContent(pureHashtag);
                        hashTagRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenHashTagAndEvent() { //step2: create connecting table
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String hashtag = event.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1);
                String[] split = hashtag.split(",");

                List<HashTag> hashtags = new LinkedList<>();
                for (String s : split) {
                    String pureHashtag = s.substring(1, s.length());
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag);
                    hashtags.add(hashTagByContent);
                }

                event.setHashtagList(hashtags);
                eventRepository.save(event);
            }
        }
    }

    public void fillHashTagTableFromScpaper() {  //fill hashtag table
        List<Scpaper> all = scpaperRepository.findAll();

        for (Scpaper scpaper : all) {
            String hashtag = scpaper.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1); //убираем { }
                String[] split = hashtag.split(","); //разделяем по "," на массив строк

                for (String hashTagWithSharp : split) {
                    String pureHashtag = hashTagWithSharp.substring(1, hashTagWithSharp.length()); //del #
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag); //ищем хештег в БД
                    if (hashTagByContent == null) {
                        HashTag s1 = new HashTag();
                        s1.setContent(pureHashtag);
                        hashTagRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenHashTagAndScpaper() { //step2: create connecting table
        List<Scpaper> all = scpaperRepository.findAll();

        for (Scpaper scpaper : all) {
            String hashtag = scpaper.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1);
                String[] split = hashtag.split(",");

                List<HashTag> hashtags = new LinkedList<>();
                for (String s : split) {
                    String pureHashtag = s.substring(1, s.length());
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag);
                    hashtags.add(hashTagByContent);
                }
                scpaper.setHashtagList(hashtags);
                scpaperRepository.save(scpaper);
            }
        }
    }

    public void fillHashTagTableFromIsource() {  //fill hashtag table
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String hashtag = isource.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1); //убираем { }
                String[] split = hashtag.split(","); //разделяем по "," на массив строк

                for (String hashTagWithSharp : split) {
                    String pureHashtag = hashTagWithSharp.substring(1, hashTagWithSharp.length()); //del #
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag); //ищем хештег в БД
                    if (hashTagByContent == null) {
                        HashTag s1 = new HashTag();
                        s1.setContent(pureHashtag);
                        hashTagRepository.save(s1);
                    }
                }
            }
        }
    }

    public void initializeReferenceBetweenHashTagAndIsource() { //step2: create connecting table
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String hashtag = isource.getHashtags();
            if (hashtag != null) {
                hashtag = hashtag.substring(1, hashtag.length() - 1);
                String[] split = hashtag.split(",");

                List<HashTag> hashtags = new LinkedList<>();
                for (String s : split) {
                    String pureHashtag = s.substring(1, s.length());
                    HashTag hashTagByContent = hashTagRepository.getHashTagByContent(pureHashtag);
                    hashtags.add(hashTagByContent);
                }
                isource.setHashtagList(hashtags);
                isourceRepository.save(isource);
            }
        }
    }
}
