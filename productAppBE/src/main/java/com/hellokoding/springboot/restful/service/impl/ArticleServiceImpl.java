package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import com.hellokoding.springboot.restful.model.dto.ItemConnectionDto;
import com.hellokoding.springboot.restful.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlLinkRepository linkRepository;
    private final LocationRepository locationRepository;
    private final PersonRepository personRepository;
    private final OrgRepository orgRepository;
    private final MTypeRepository materialTypeRepository;
    private final ConnectionTypeRepository ctypeRepository;

    @Override
    public List<ArticleDto> findAll() {
        List<ArticleDto> dtoAllArticleList = new ArrayList<>();
        List<Article> all = articleRepository.findAll();

        ArticleDto articleDto;
        for (Article article : all) {
            articleDto = new ArticleDto(article);
            articleDto.setMaterialList(createMaterialConnectionsListForArticleDtoFromArticle(article));

            dtoAllArticleList.add(articleDto);
        }
        return dtoAllArticleList;
    }

    @Override
    public Optional<ArticleDto> findById(Integer id) {
        Optional<Article> byId = articleRepository.findById(id);
        Optional<ArticleDto> byIdDto;

        byIdDto = Optional.of(new ArticleDto(byId.get()));
        byIdDto.get().setMaterialList(createMaterialConnectionsListForArticleDtoFromArticle(byId.get()));
        return byIdDto;
    }


    public ArrayList<ItemConnectionDto> createMaterialConnectionsListForArticleDtoFromArticle(Article article) {

        ArrayList<ItemConnectionDto> materialList = new ArrayList<>();
        if (article.getMaterialConnections().size() > 0) {
            ItemConnectionDto materialConnectionDto;

            for (ArticleMaterialConnection connection : article.getMaterialConnections()) {
                materialConnectionDto = new ItemConnectionDto();

                Optional<ConnectionType> ct = (ctypeRepository.findById(connection.getConnection()));
                if (ct.isPresent()) {
                    materialConnectionDto.setItemId(connection.getMaterial().getId());
                    materialConnectionDto.setConnection(ct.get().getType());
                    materialConnectionDto.setComment(connection.getComment());
                }
                materialList.add(materialConnectionDto);
            }
        }
        return materialList;
    }

    @Override
    @Transactional
    public Article save(ArticleDto articleDto) {

//        UrlLink linkByContent;
//        UrlLink linkWithID;
        List<UrlLink> linkList = articleDto.getLinkList();
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
        ll.getLinkListID(linkList, linkListWithID);

        Article article;
        if (articleDto.getId() == null) {
            article = new Article();
        } else if (articleRepository.findById(articleDto.getId()).isPresent()) {
            article = articleRepository.findById(articleDto.getId()).get();
        } else
            return null;

        if (article.getLinkList() != null) {
            article.getLinkList().clear();
            articleRepository.flush();
        } else {
            article.setLinkList(new ArrayList<>());
        }

        if (article.getLinkList() == null) {
            article.setLinkList(linkListWithID);
        } else {
            article.getLinkList().addAll(linkListWithID);
        }

        article.setTitle(articleDto.getTitle());
        article.setTitleRus(articleDto.getTitleRus());
        article.setMovement(articleDto.getMovement());
        article.setLanguage(articleDto.getLanguage());


        MaterialType mT = articleDto.getMtype();
        if (mT != null) {
            if (mT.getId() != null) {
                Optional<MaterialType> byId = materialTypeRepository.findById(mT.getId());
                if (byId != null) {
                    article.setMtype(byId.get());
                }
            }
        }

        article.setDate(articleDto.getDate());
        article.setStatus(articleDto.getStatus());
        article.setDescription(articleDto.getDescription());
        article.setMiscellany(articleDto.getMiscellany());

        /////////////////////PERSON CONNECTIONS///////////////////
        if (article.getPersonConnections() != null) {
            article.getPersonConnections().clear();
            articleRepository.flush();
        }

        Integer personId;
        ArticlePersonConnection personConnection;
        List<ArticlePersonConnection> personConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : articleDto.getPersonList()) {

            personId = connectionDto.getItemId();
            if (personRepository.findById(personId).isPresent()) {
                personConnection = new ArticlePersonConnection();
                personConnection.setPerson(personRepository.findById(personId).get());
                personConnection.setArticle(article);
                personConnection.setConnection(connectionDto.getConnection());
                personConnection.setComment(connectionDto.getComment());

                personConnectionList.add(personConnection);
            }
        }
        if (article.getPersonConnections() == null) {
            article.setPersonConnections(personConnectionList);
        } else {
            article.getPersonConnections().addAll(personConnectionList);
        }

        if (article.getLocationConnections() != null) {
            article.getLocationConnections().clear();
            articleRepository.flush();
        }

        /////////////////////ORG CONNECTIONS///////////////////
        if (article.getOrgConnections() != null) {
            article.getOrgConnections().clear();
            articleRepository.flush();
        }

        Integer orgId;
        ArticleOrgConnection orgConnection;
        List<ArticleOrgConnection> orgConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : articleDto.getOrgList()) {

            orgId = connectionDto.getItemId();
            if (orgRepository.findById(orgId).isPresent()) {
                orgConnection = new ArticleOrgConnection();
                orgConnection.setOrg(orgRepository.findById(orgId).get());
                orgConnection.setArticle(article);
                orgConnection.setConnection(connectionDto.getConnection());
                orgConnection.setComment(connectionDto.getComment());

                orgConnectionList.add(orgConnection);
            }
        }

        if (article.getOrgConnections() == null) {
            article.setOrgConnections(orgConnectionList);
        } else {
            article.getOrgConnections().addAll(orgConnectionList);
        }

        /////////////////////LOCATION CONNECTIONS///////////////////
        if (article.getLocationConnections() != null) {
            article.getLocationConnections().clear();
            articleRepository.flush();
        }

        Integer locatonId;
        ArticleLocationConnection locationConnection;
        List<ArticleLocationConnection> locationConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : articleDto.getLocationList()) {

            locatonId = connectionDto.getItemId();
            if (locationRepository.findById(locatonId).isPresent()) {
                locationConnection = new ArticleLocationConnection();
                locationConnection.setLocation(locationRepository.findById(locatonId).get());
                locationConnection.setArticle(article);
                locationConnection.setConnection(connectionDto.getConnection());
                locationConnection.setComment(connectionDto.getComment());

                locationConnectionList.add(locationConnection);
            }
        }

        if (article.getLocationConnections() == null) {
            article.setLocationConnections(locationConnectionList);
        } else {
            article.getLocationConnections().addAll(locationConnectionList);
        }

        /////////////////////MATERIAL CONNECTIONS///////////////////
        if (article.getMaterialConnections() != null) {
            article.getMaterialConnections().clear();
            articleRepository.flush();
        }
        Integer materialId;
        ArticleMaterialConnection materialConnection;
        List<ArticleMaterialConnection> materialConnectionList = new ArrayList<>();


        for (ItemConnectionDto connectionDto : articleDto.getMaterialList()) {
            materialId = connectionDto.getItemId();
            if (articleRepository.findById(materialId).isPresent()) {
                Optional<ConnectionType> ct = (ctypeRepository.findByType(connectionDto.getConnection()));
                if (ct.isPresent()) {
                    materialConnection = new ArticleMaterialConnection();
                    materialConnection.setMaterial(articleRepository.findById(materialId).get());
                    materialConnection.setArticle(article);
                    materialConnection.setConnection(ct.get().getId());
                    materialConnection.setComment(connectionDto.getComment());
                    materialConnectionList.add(materialConnection);
                }
            }
        }

        if (article.getMaterialConnections() == null) {
            article.setMaterialConnections(materialConnectionList);
        } else {
            article.getMaterialConnections().addAll(materialConnectionList);
        }


        /////////////////////article-hashtag////////////////////////
        ArticleHashtag articleHashtag, previousArticleHashtag, previousPreviousArticleHashtag;
        HashTag hashTag, hashTagPrevious, hashTagPreviousPrevious;
        //List<ArticleHashtag> hashtagList = new ArrayList<>();

        if (article.getHashtagList() != null) {
            for (ArticleHashtag at : article.getHashtagList()) {
                at.setArticle(null);
            }
            article.getHashtagList().clear();
            articleRepository.flush();
        } else {
            article.setHashtagList(new ArrayList<>());
        }

        for (String hashtag_content : articleDto.getHashtagList()) {
            hashTag = hashTagRepository.getHashTagByContent(hashtag_content);
            articleHashtag = new ArticleHashtag();

            if (hashTag != null) { // hashtag was found in repo

                if (hashTag.getParentId() == 0) {    ///////////////////////hashtag level 1
                    articleHashtag.setHashtag(hashTag);
                    articleHashtag.setLevel(1);
                    articleHashtag.setAssigned_hashtag(hashTag);
                    articleHashtag.setArticle(article);

                    article.getHashtagList().add(articleHashtag);

                } else {  ///////////////////////hashtag level 2/3
                    hashTagPrevious = hashTagRepository.findById(hashTag.getParentId()).get();
                    previousArticleHashtag = new ArticleHashtag();

                    if (hashTagPrevious.getParentId() == 0) {   ///////////////////////hashtag level 2

                        previousArticleHashtag.setHashtag(hashTagPrevious);
                        previousArticleHashtag.setLevel(1);
                        previousArticleHashtag.setAssigned_hashtag(hashTag);
                        previousArticleHashtag.setArticle(article);

                        articleHashtag.setHashtag(hashTag);
                        articleHashtag.setLevel(2);
                        articleHashtag.setAssigned_hashtag(hashTag);
                        articleHashtag.setArticle(article);

                        article.getHashtagList().add(articleHashtag);
                        article.getHashtagList().add(previousArticleHashtag);

                    } else {   ///////////////////////hashtag level 3
                        hashTagPreviousPrevious = hashTagRepository.findById(hashTagPrevious.getParentId()).get();
                        previousPreviousArticleHashtag = new ArticleHashtag();

                        if (hashTagPreviousPrevious.getParentId() == 0) {

                            previousPreviousArticleHashtag.setHashtag(hashTagPreviousPrevious);
                            previousPreviousArticleHashtag.setLevel(1);
                            previousPreviousArticleHashtag.setAssigned_hashtag(hashTag);
                            previousPreviousArticleHashtag.setArticle(article);

                            previousArticleHashtag.setHashtag(hashTagPrevious);
                            previousArticleHashtag.setLevel(2);
                            previousArticleHashtag.setAssigned_hashtag(hashTag);
                            previousArticleHashtag.setArticle(article);

                            articleHashtag.setHashtag(hashTag);
                            articleHashtag.setLevel(3);
                            articleHashtag.setAssigned_hashtag(hashTag);
                            articleHashtag.setArticle(article);

                            article.getHashtagList().add(articleHashtag);
                            article.getHashtagList().add(previousArticleHashtag);
                            article.getHashtagList().add(previousPreviousArticleHashtag);
                        }
                    }//level 3
                } //level 2/3
            }
        }//for
        //article.setHashtagList(null);
        //article.setHashtagList(hashtagList);
        return articleRepository.save(article);
//        return article;
    }

    public List<Article> findByIds(List<Integer> idList) {

        List<Article> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Article> l = articleRepository.findById(id);

            if (l != null) {
                searchRes.add(l.get());
            }
        }
        return searchRes;
    }

    @Override
    public List<Article> searchMaterial(String q) {
        List<Article> rrr = articleRepository.findMaterialByTitle("%" + q + "%");
        return rrr;
    }

    public List<ArticleDto> search(String title, String hash, String author, String language, String description, List<Integer> status, String startDate, String endDate) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date frmtStartDate = format.parse(startDate);
        Date frmtEndDate = format.parse(endDate);

        List<Article> searchList = new ArrayList<>();
        List<ArticleDto> dtoSearchList = new ArrayList<>();

        if (status != null && status.size() > 0) {
            if (title != null && !title.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByTitleAndDate("%" + title + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByTitleAndStatus("%" + title + "%", status);

                } else {
                    searchList = articleRepository.findByTitleAndStatusAndDate("%" + title + "%", status, frmtStartDate, frmtEndDate);
                }


            } else if (hash != null && !hash.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByHashAndDate(hash + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByHashAndStatus(hash + "%", status);

                } else {
                    searchList = articleRepository.findByHashAndStatusAndDate(hash + "%", status, frmtStartDate, frmtEndDate);
                }


            } else if (author != null && !author.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByAuthorAndDate(author + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByAuthorAndStatus(author + "%", status);

                } else {
                    searchList = articleRepository.findByAuthorAndStatusAndDate(author + "%", status, frmtStartDate, frmtEndDate);
                }

            } else if (language != null && !language.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByLangAndDate(language + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByLangAndStatus(language + "%", status);

                } else {
                    searchList = articleRepository.findByLangAndStatusAndDate(language + "%", status, frmtStartDate, frmtEndDate);
                }

            } else if (description != null && !description.isEmpty()) {

                if (status.get(0) == -1) {
                    searchList = articleRepository.findByDescriptionAndDate("%" + description + "%", frmtStartDate, frmtEndDate);

                } else if (status.get(0) == 3) {
                    searchList = articleRepository.findByDescriptionAndStatus("%" + description + "%", status);

                } else {
                    searchList = articleRepository.findByDescriptionAndStatusAndDate("%" + description + "%", status, frmtStartDate, frmtEndDate);
                }

            } else if (status.get(0) == -1) {
                searchList = articleRepository.findAllByDateBetween(frmtStartDate, frmtEndDate);
            } else if (status.get(0) == 3) {
                searchList = articleRepository.findAllByStatus(status);
            } else {
                searchList = articleRepository.findByDateAndStatus(status, frmtStartDate, frmtEndDate);
            }
        }

        ArticleDto dtoArticle;
        for (Article article : searchList) {
            dtoArticle = new ArticleDto(article);
            dtoArticle.setMaterialList(createMaterialConnectionsListForArticleDtoFromArticle(article));
            dtoSearchList.add(dtoArticle);
        }
        return dtoSearchList;
    }


    @Override
    public void deleteById(Integer id) {
        articleRepository.deleteById(id);
    }
}
