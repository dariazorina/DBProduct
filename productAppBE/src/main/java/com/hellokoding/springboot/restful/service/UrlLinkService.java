package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.UrlLinkRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.UrlLink;
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

    public void fillLinkTable() {   //step1: create link table
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
}
