package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {

    private final HashTagRepository hashTagRepository;
    private final ArticleRepository articleRepository;
    private final EventRepository eventRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;


    @Override
    public List<HashTag> findAll() {
        return hashTagRepository.findAll();
    }

    @Override
    public Optional<HashTag> findById(Integer id) {
        return hashTagRepository.findById(id);
    }

    @Override
    public List<HashTag> findLeafs(Integer id) {
        return hashTagRepository.findByParentId(id);
    }

    @Override
    public HashTag save(HashTag stock) {

        stock.setContent(stock.getContent().toLowerCase());
        HashTag hashTagByContent = hashTagRepository.getHashTagByContent(stock.getContent()); //ищем хештег в БД
        if (hashTagByContent == null) {
            return hashTagRepository.save(stock);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {

        List<HashTag> allHash = hashTagRepository.findAll();
        List<Integer> topLevelIdList = new ArrayList<>();
        List<Integer> bottomLevelIdList = new ArrayList<>();

        for (HashTag h : allHash) {
            if (h.getParentId().equals(id)) {
                topLevelIdList.add(h.getId());
            }
        }

        for (Integer branchId : topLevelIdList) {
            for (HashTag h : allHash) {
                if (h.getParentId().equals(branchId)) {
                    bottomLevelIdList.add(h.getId());
                }
            }
        }

        for (Integer leafId : bottomLevelIdList) {
            hashTagRepository.deleteById(leafId);
        }

        for (Integer branchId : topLevelIdList) {
            hashTagRepository.deleteById(branchId);
        }

        hashTagRepository.deleteById(id);
    }

    @Override
    public List<HashTag> search(String q) {
        return hashTagRepository.findByContentStartsWithIgnoreCaseOrderByContentDesc(q);
    }

/*    public void fillHashTagTableFromArticle() {   ////step1: fill hashtag table
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
    }*/

/*    public void initializeReferenceBetweenHashTagAndArticle() { //step2: create connecting table
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
    }*/

/*    public void fillHashTagTableFromEvent() {  //fill hashtag table
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
    }*/

/*    public void initializeReferenceBetweenHashTagAndEvent() { //step2: create connecting table
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
    }*/

/*    public void fillHashTagTableFromScpaper() {  //fill hashtag table
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
    }*/

/*    public void initializeReferenceBetweenHashTagAndScpaper() { //step2: create connecting table
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
    }*/

/*    public void fillHashTagTableFromIsource() {  //fill hashtag table
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
    }*/
}
