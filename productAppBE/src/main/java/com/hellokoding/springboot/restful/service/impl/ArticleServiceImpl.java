package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.dao.UrlLinkRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.model.UrlLink;
import com.hellokoding.springboot.restful.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlLinkRepository linkRepository;

    @Override
    public List<Article> findAll() {
        List<Article> all = articleRepository.findAll();
        return all;
    }

    @Override
    public Optional<Article> findById(Integer id) {
        Optional<Article> byId = articleRepository.findById(id);
        return byId;
    }

    @Override
    public Article save(Article stock) {

        HashTag hashTagByContent;
        HashTag hashTagWithID;
        List<HashTag> hashTagList = stock.getHashtagList();
        List<HashTag> hashTagListWithID = new ArrayList<>();


        UrlLink linkByContent;
        UrlLink linkWithID;
        List<UrlLink> linkList = stock.getLinkList();
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

        return articleRepository.save(stock);
    }
    @Override
    public List<Article> search(String q) {

        List<Article> titleSearchList = articleRepository.findByTitleStartsWithIgnoreCase(q);
//        List<Article> titleSearchList = articleRepository.findByTitleAndTitleRusStartsWith(q, q);

//        List<Article> hashSearchList = articleRepository.findByHashtagListStartsWithIgnoreCase(q);
//        List<Article> titleSearchList = articleRepository.findByTitleAndTitleRusContains(q);//IgnoreCase(q);

//                findByTitleStartsWithIgnoreCase(q);
//        List<Article> surnameRusSearchList = personRepository.findBySurnameRusStartsWithIgnoreCase(q);
//        List<> surnameEngSearchList = personRepository.findBySurnameEngStartsWithIgnoreCase(q);

        Set<Article> fooSet = new TreeSet<>();
        String dtoName;

//        for (Person person : surnameSearchList) {
//            dtoName = person.getSurname();
//            if (person.getName() != null) {
//                dtoName += " " + person.getName();
//            }
//            PersonDto personDto = new PersonDto(person.getId(), dtoName);
//            fooSet.add(personDto);
//        }
//
//        for (Person person : surnameRusSearchList) {
//            dtoName = person.getSurnameRus();
//            if (person.getNameRus() != null) {
//                dtoName += " " + person.getNameRus();
//            }
//            PersonDto personDto = new PersonDto(person.getId(), dtoName);
//            fooSet.add(personDto);
//        }
//
//        for (Person person : surnameEngSearchList) {
//            dtoName = person.getSurnameEng();
//            if (person.getNameEng() != null) {
//                dtoName += " " + person.getNameEng();
//            }
//            PersonDto personDto = new PersonDto(person.getId(), dtoName);
//            fooSet.add(personDto);
//        }


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


//        List<Article> finalList = new ArrayList<Article>(fooSet);
//        return finalList;

        return titleSearchList;
//        return hashSearchList;
    }




//    public LmUserSearchResult getLmUsers(LmUserSearchFilterDto filter) {
//        BooleanBuilder builder = new BooleanBuilder();
//        if (filter.getUserId() != null) {
//            builder.and(lmUserEntity.userId.likeIgnoreCase("%" + filter.getUserId() + "%"));
//        }
//        if (filter.getFirstName() != null) {
//            builder.and(lmUserEntity.firstName.likeIgnoreCase("%" + filter.getFirstName() + "%"));
//        }
//        if (filter.getLastName() != null) {
//            builder.and(lmUserEntity.lastName.likeIgnoreCase("%" + filter.getLastName() + "%"));
//        }
//        if (filter.getIsInvalid() != null) {
//            if (filter.getIsInvalid()) {
//                builder.and(lmUserEntity.invalidDate.isNotNull())
//                        .and(lmUserEntity.invalidDate.loe(LocalDate.now()));
//            } else {
//                builder.andAnyOf(lmUserEntity.invalidDate.isNull(),
//                        lmUserEntity.invalidDate.gt(LocalDate.now())
//                );
//            }
//        }
//
//        //default ordering
//        OrderSpecifier order = lmUserEntity.userId.asc();
//
//        LmUserSearchResultBuilder resultBuilder = LmUserSearchResult.builder();
//
//        //add pagination and perform query
//        if (filter.getPage() != null && filter.getPageSize() != null) {
//            resultBuilder.result(Lists.newArrayList(lmUserRepository
//                    .findAll(builder.getValue(),
//                            new QPageRequest(filter.getPage() - 1, filter.getPageSize(), order))));
//            resultBuilder.count(lmUserRepository.count(builder.getValue()));
//        } else {
//            Collection<LmUserEntity> queryResult = Lists
//                    .newArrayList(lmUserRepository.findAll(builder.getValue(), order));
//            resultBuilder.result(queryResult);
//            resultBuilder.count(Long.valueOf(queryResult.size()));
//        }
//
//        return resultBuilder.build();
//    }




    @Override
    public void deleteById(Integer id) {
        articleRepository.deleteById(id);
    }
}
