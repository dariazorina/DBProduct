package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.ArticleConverter;
import com.hellokoding.springboot.restful.service.ArticleService;
import com.hellokoding.springboot.restful.service.UrlLinkService;
import lombok.RequiredArgsConstructor;
//import org.hibernate.proxy.HibernateProxy;
//import org.hibernate.proxy.LazyInitializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.naming.Name;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMaterialRepository articleMaterialRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlLinkRepository linkRepository;
    private final LocationRepository locationRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;
    private final OrgRepository orgRepository;
    private final EventRepository eventRepository;
    private final MTypeRepository materialTypeRepository;
    private final ConnectionTypeRepository ctypeRepository;
    private final StatusRepository statusRepository;

    private final UrlLinkService urlLinkService;

    @Override
    public Optional<ArticleDto> findById(Integer id) {

        Optional<Article> byId = articleRepository.findById(id);
        Optional<ArticleDto> byIdDto;

        byIdDto = Optional.of(new ArticleDto(byId.get(), ctypeRepository.findAll()));

        return byIdDto;
    }

//    @Override
//    public ArticleDto findById(Integer id) {
//
//        Optional<Article> byId = articleRepository.findById(id);
//        if (byId.isPresent()) {
//            Article art = new Article(byId.get());
//            ArticleDto articleDto = new ArticleDto();
////            Article articleParameter = new Article(byId.get().getId(), );
//            ArticleConverter.convertToArticleDto(art, articleDto, ctypeRepository.findAll());
//            return articleDto;
//        }
//
//        return null;
//    }


//    public ArrayList<NameConnectionDto> createMaterialConnectionsListForArticleDtoFromArticle(Article article) {
//
//        ArrayList<NameConnectionDto> materialList = new ArrayList<>();
//        if (article.getMaterialConnections().size() > 0) {
//            ItemConnectionDto materialConnectionDto;
//
//            for (ArticleMaterialConnection connection : article.getMaterialConnections()) {
//                materialConnectionDto = new NameConnectionDto();
//
//                Optional<ConnectionType> ct = (ctypeRepository.findById(connection.getConnection()));
//                if (ct.isPresent()) {
//                    materialConnectionDto.setItemId(connection.getMaterial().getId());
//                    materialConnectionDto.setConnection(ct.get().getType());
//                    materialConnectionDto.setComment(connection.getComment());
//                }
//                materialList.add(materialConnectionDto);
//            }
//        }
//        return materialList;
//    }

    @Override
    @Transactional
    public Article saveColor(ArticleDtoForMainList articleDto) {

        Article article;
        if (articleRepository.findById(articleDto.getId()).isPresent()) {
            article = articleRepository.findById(articleDto.getId()).get();

            article.setRgbSelection(articleDto.getRowColor());
            return articleRepository.save(article);

        } else
            return null;
    }

    @Override
    @Transactional
    public Article save(ArticleDto articleDto) {

//        UrlLink linkByContent;
//        UrlLink linkWithID;
        List<UrlLink> linkList = articleDto.getLinkList();
//        List<UrlLink> linkListWithID = new ArrayList<>();

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

//        LinkListIDCreation ll = new LinkListIDCreation(linkRepository);
//        ll.getLinkListID(linkList, linkListWithID);
        List<UrlLink> linkListWithID = urlLinkService.getLinkListID(linkList);

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
        article.setLanguage(articleDto.getLanguage());


        if (article.getMovementList() != null) {
            article.getMovementList().clear();
            articleRepository.flush();
        } else {
            article.setMovementList(new ArrayList<>());
        }
        if (article.getMovementList() == null) {
            article.setMovementList(articleDto.getMovementList());
        } else
            article.getMovementList().addAll(articleDto.getMovementList());


        MaterialType mT = articleDto.getMtype();
        if (mT != null) {
            if (mT.getId() != null) {
                Optional<MaterialType> byId = materialTypeRepository.findById(mT.getId());
                byId.ifPresent(article::setMtype);
            } else {
                article.setMtype(null);
            }
        }

        article.setDate(articleDto.getDate());

        Optional<Status> byName = statusRepository.getByName(articleDto.getStatus());
        if (byName.isPresent()) {
            article.setStatus(byName.get());
        }

        article.setDescription(articleDto.getDescription());
        article.setText(articleDto.getText());
        article.setRgbSelection(articleDto.getRowColor());
        article.setMiscellany(articleDto.getMiscellany());

        /////////////////////PERSON CONNECTIONS///////////////////
        if (article.getPersonConnections() != null) {
            article.getPersonConnections().clear();
            articleRepository.flush();
        }

        Integer personId;
        ArticlePersonConnection personConnection;
        List<ArticlePersonConnection> personConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : articleDto.getPersonList()) {

            personId = connectionDto.getItemId();
            if (personRepository.findById(personId).isPresent()) {
                personConnection = new ArticlePersonConnection();

                Person person = personRepository.findById(personId).get();

                personConnection.setPerson(person);
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
        for (NameConnectionDto connectionDto : articleDto.getOrgList()) {

            orgId = connectionDto.getItemId();
            if (orgRepository.findById(orgId).isPresent()) {
                orgConnection = new ArticleOrgConnection();

                Org org = orgRepository.findById(orgId).get();

                orgConnection.setOrg(org);
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
        for (NameConnectionDto connectionDto : articleDto.getLocationList()) {

            locatonId = connectionDto.getItemId();
            if (locationRepository.findById(locatonId).isPresent()) {
                locationConnection = new ArticleLocationConnection();

                Location location = locationRepository.findById(locatonId).get();

                locationConnection.setLocation(location);
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

        /////////////////////event CONNECTIONS///////////////////
        if (article.getEventConnections() != null) {
            article.getEventConnections().clear();
            personRepository.flush();
        }

        Integer eventId;
        ArticleEventConnection eventArticleConnection;
        List<ArticleEventConnection> eventArticleConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : articleDto.getEventList()) {

            eventId = connectionDto.getItemId();
            if (eventRepository.findById(eventId).isPresent()) {
                eventArticleConnection = new ArticleEventConnection();
                Event event = eventRepository.findById(eventId).get();

                eventArticleConnection.setEvent(event);
                eventArticleConnection.setArticle(article);
                eventArticleConnection.setConnection(connectionDto.getConnection());
                eventArticleConnection.setComment(connectionDto.getComment());

                eventArticleConnectionList.add(eventArticleConnection);
            }
        }

        if (article.getEventConnections() == null) {
            article.setEventConnections(eventArticleConnectionList);
        } else {
            article.getEventConnections().addAll(eventArticleConnectionList);
        }

        /////////////////////PROJECT CONNECTIONS///////////////////
        if (article.getProjectConnections() != null) {
            article.getProjectConnections().clear();
            articleRepository.flush();
        }

        Integer projectId;
        ProjectArticleConnection projectArticleConnection;
        List<ProjectArticleConnection> projectArticleConnectionList = new ArrayList<>();
        for (NameConnectionDto connectionDto : articleDto.getProjectList()) {

            projectId = connectionDto.getItemId();
            if (projectRepository.findById(projectId).isPresent()) {

                Project dromDB = projectRepository.findById(projectId).get();
                //Project pro = dromDB.clone();

                projectArticleConnection = new ProjectArticleConnection();
                projectArticleConnection.setProject(dromDB);
                projectArticleConnection.setArticle(article);
                projectArticleConnection.setConnection(connectionDto.getConnection());
                projectArticleConnection.setComment(connectionDto.getComment());

                projectArticleConnectionList.add(projectArticleConnection);
            }
        }

        if (article.getProjectConnections() == null) {
            article.setProjectConnections(projectArticleConnectionList);
        } else {
            article.getProjectConnections().addAll(projectArticleConnectionList);
        }

        /////////////////////MATERIAL CONNECTIONS///////////////////
        if (article.getMaterialConnections() != null) {
            article.getMaterialConnections().clear();
            articleRepository.flush();
        }
        Integer materialId;
        boolean isSymmConnection = false;
        Article material = null;
        ArticleMaterialConnection articleConnection;
        ArticleMaterialConnection materialConnection;
        List<ArticleMaterialConnection> articleConnectionList = new ArrayList<>();
        List<ArticleMaterialConnection> materialConnectionList = new ArrayList<>();
        List<ArticleMaterialConnection> symmConnectionList;// = new ArrayList<>();


        Optional<ConnectionType> ct = Optional.empty();
        for (NameConnectionDto connectionDto : articleDto.getMaterialList()) {
            materialId = connectionDto.getItemId();
            if (articleRepository.findById(materialId).isPresent()) {
                if (connectionDto.getConnection().length() > 0) {
                    ct = (ctypeRepository.findByType(connectionDto.getConnection()));
                }

                //article - article.id, material - material.id - simple conn
                //article - material.id, material - article.id - symm conn - need to exclude here and save for material (not for current article)

                symmConnectionList = articleMaterialRepository.findByIdSimple(materialId);
                for (ArticleMaterialConnection artMatConn : symmConnectionList) {
                    if (artMatConn.getMaterial().getId().equals(article.getId())) {
                        isSymmConnection = true;
                        break;
                    }
                }
                if (!isSymmConnection) {
                    articleConnection = new ArticleMaterialConnection();

                    Article materialFromDB = articleRepository.findById(materialId).get();

                    articleConnection.setMaterial(materialFromDB);
                    articleConnection.setArticle(article);

                    if (ct.isPresent()) {
                        ConnectionType connType = ct.get();
                        articleConnection.setConnection(connType.getId());
                    }

                    articleConnection.setComment(connectionDto.getComment());
                    articleConnectionList.add(articleConnection);

                } else {  //save connection for material
                    material = articleRepository.findById(materialId).get();

                    materialConnection = new ArticleMaterialConnection();

                    Article articleFromDb = articleRepository.findById(materialId).get();

                    materialConnection.setArticle(articleFromDb);
                    materialConnection.setMaterial(article);

                    if (ct.isPresent()) {
                        ConnectionType connType = ct.get();
                        materialConnection.setConnection(connType.getId());
                    }

                    materialConnection.setComment(connectionDto.getComment());
                    materialConnectionList.add(materialConnection);


                    if (material.getMaterialConnections() == null) {
                        material.setMaterialConnections(materialConnectionList);
                    } else {
                        //remove already existed entities
                        List<ArticleMaterialConnection> elList = new ArrayList<>();
                        for (ArticleMaterialConnection artMatConn : material.getMaterialConnections()) {
                            if (artMatConn.getMaterial().getId().equals(article.getId())) {
                                elList.add(artMatConn);
                            }
                        }
                        material.getMaterialConnections().removeAll(elList);
                        articleRepository.flush();
                        material.getMaterialConnections().addAll(materialConnectionList);
                    }
                }
                isSymmConnection = false;

            }


            if (article.getId() != null) {
                //delete symm connections
                List<NameConnectionDto> startListConnectedOrgsForOrg = findByIdsAndSymmetrically(article.getId());
                List<NameConnectionDto> resultListConnectedOrgsForOrg = articleDto.getMaterialList();

                List<NameConnectionDto> differences = startListConnectedOrgsForOrg.stream()
                        .filter(element -> !resultListConnectedOrgsForOrg.contains(element))
                        .collect(Collectors.toList());

                for (NameConnectionDto nmdto : differences) {
                    material = articleRepository.findById(nmdto.getItemId()).get();

                    //remove already existed entities
                    List<ArticleMaterialConnection> elList = new ArrayList<>();
                    for (ArticleMaterialConnection articleMaterialConnection : material.getMaterialConnections()) {
                        if (articleMaterialConnection.getMaterial().getId().equals(article.getId())) {
                            elList.add(articleMaterialConnection);
                        }
                    }
                    material.getMaterialConnections().removeAll(elList);
                    articleRepository.flush();
                }
            }
        }
        if (article.getMaterialConnections() == null) {
            article.setMaterialConnections(articleConnectionList);
        } else {
            article.getMaterialConnections().addAll(articleConnectionList);
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
        if (material != null)
            articleRepository.save(material);
        return articleRepository.save(article);
//        return article;
    }

    public List<IdContentDto> findByIds(List<Integer> idList) {

        List<Article> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Article> l = articleRepository.findById(id);

            l.ifPresent(searchRes::add);

//            if (l != null) {  //first variant
//                searchRes.add(l.get());
//            }
        }
        return createResultSearchWithNameAndDate(searchRes, null);
    }

    public List<NameConnectionDto> findByIdsAndSymmetrically(Integer itemId) {  // use it for articles

        Article connectedArticle, article;
        String dtoName = "", connection = "", comment = "";
        // Set<Article> searchRes = new TreeSet<>();
        List<NameConnectionDto> finalList = new ArrayList<>();
        Optional<ConnectionType> ct = Optional.empty();

        List<ArticleMaterialConnection> searchResSymm;// = new ArrayList<>();

        Optional<Article> articleOpt = articleRepository.findById(itemId);
        if (articleOpt.isPresent()) {
            article = articleOpt.get();             //   searchRes.add(l.get());

            searchResSymm = articleMaterialRepository.findByIdSymm(itemId); //searching symm connections for this itemId
//            for (ArticleMaterialConnection articleMaterialConnection : searchResSymm) {
//                Article symmArticle = articleMaterialConnection.getArticle();
//                Article unproxiedPlayer = new Article();
//                if (symmArticle instanceof HibernateProxy) {
//                    HibernateProxy hibernateProxy = (HibernateProxy) symmArticle;
//                    LazyInitializer initializer =
//                            hibernateProxy.getHibernateLazyInitializer();
//                    unproxiedPlayer = (Article) initializer.getImplementation();
//                }
//
//                if (unproxiedPlayer != null)
//                    searchRes.add(unproxiedPlayer);
//            }//for

            for (ArticleMaterialConnection el : article.getMaterialConnections()) { //simple connections
                Optional<Article> connArticleOpt = articleRepository.findById(el.getMaterial().getId());

                if (connArticleOpt.isPresent()) {
                    connectedArticle = connArticleOpt.get();             //   searchRes.add(l.get());
                    if (connectedArticle.getTitleRus() != null) {
                        if (connectedArticle.getTitleRus().length() > 0) {
                            dtoName += connectedArticle.getTitleRus();
                            if (connectedArticle.getTitle() != null) {
                                if (connectedArticle.getTitle().length() > 0) {
                                    dtoName += "/ " + connectedArticle.getTitle();
                                }
                            }
                        }
                    }
                    LocalDate dateWithZeroTime = connectedArticle.getDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    if (dateWithZeroTime != null) dtoName += ", " + dateWithZeroTime; //article.getDate().;
                    else {
                        dtoName += "," + connectedArticle.getLinkList().get(0);  //as the first help in fail with date
                    }

                    for (ArticleMaterialConnection articleMaterialConnection : article.getMaterialConnections()) { //is it necessary?
                        if (articleMaterialConnection.getMaterial().getId().equals(connectedArticle.getId())) {    //is it necessary?
                            if (articleMaterialConnection.getConnection() != null) {  //if connection is empty no need to find connection type
                                ct = (ctypeRepository.findById(articleMaterialConnection.getConnection()));
                            }
                            if (ct.isPresent()) {
                                connection = ct.get().getType();
                            }
                            if (articleMaterialConnection.getComment() != null) {
                                if (articleMaterialConnection.getComment().length() != 0) {
                                    comment = articleMaterialConnection.getComment();
                                }
                            }
                        }
                    }
                    NameConnectionDto articleDto = new NameConnectionDto(connectedArticle.getId(), dtoName, connection, comment);
                    dtoName = "";
                    connection = "";
                    comment = "";
                    finalList.add(articleDto);
                    ct = Optional.empty();
                }
            }//for

            for (ArticleMaterialConnection articleMaterialConnection : searchResSymm) {
                if (articleMaterialConnection.getMaterial().getId().equals(article.getId())) {

                    dtoName = articleMaterialConnection.getArticle().getTitleRus();
                    LocalDate dateWithZeroTime = articleMaterialConnection.getArticle().getDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    if (dateWithZeroTime != null) {
                        dtoName += ", " + dateWithZeroTime;
                    }

                    if (articleMaterialConnection.getConnection() != null) {
                        ct = (ctypeRepository.findById(articleMaterialConnection.getConnection()));
                    }

                    if (ct.isPresent()) {
                        connection = ct.get().getType();
                    }
                    if (articleMaterialConnection.getComment() != null) {
                        if (articleMaterialConnection.getComment().length() != 0) {
                            comment = articleMaterialConnection.getComment();
                        }
                    }
                    NameConnectionDto articleDto = new NameConnectionDto(articleMaterialConnection.getArticle().getId(), dtoName, connection, comment);
                    finalList.add(articleDto);
                    dtoName = "";
                    connection = "";
                    comment = "";
                    ct = Optional.empty();
                }
            }
        }
        return finalList;
    }


    @Override
    public List<IdContentDto> searchMaterial(String q) {
        List<Article> rrr = articleRepository.findMaterialByTitle("%" + q.toLowerCase() + "%");
        return createResultSearchWithNameAndDate(rrr, null);
    }

    @Override
    public List<IdContentDto> searchMaterialById(Integer id) {
        Optional<Article> rrr = articleRepository.findById(id);
        if (rrr.isPresent()) {
            List<Article> finalList = new ArrayList<>();
            finalList.add(rrr.get());
            return createResultSearchWithNameAndDate(finalList, null);
        }
        return null;
    }

    public List<IdContentDto> createResultSearchWithNameAndDate
            (List<Article> resultSearch, List<ArticleMaterialConnection> resultSearchConnection) {
        Set<IdContentDto> fooSet = new TreeSet<>();
        String dtoName = "";

        for (Article article : resultSearch) {
            if (article.getTitleRus() != null) {
                if (article.getTitleRus().length() > 0) {
                    dtoName += article.getTitleRus();

//                    if (article.getTitle() != null) {
//                        if (article.getTitle().length() > 0) {
//                            dtoName += "/ " + article.getTitle();
//                        }
//                    }
                }
            } else if (article.getTitle() != null) {
                if (article.getTitle().length() > 0) {
                    dtoName += article.getTitle();
                }
            } else {
                dtoName = "error name";
            }

            LocalDate dateWithZeroTime = article.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (dateWithZeroTime != null) {
                dtoName += ", " + dateWithZeroTime; //article.getDate().;
            } else {
                dtoName += "," + article.getLinkList().get(0);  //as the first help in fail with date
            }

            if (resultSearchConnection != null) {
                for (ArticleMaterialConnection articleMaterialConnection : resultSearchConnection) {
                    if (articleMaterialConnection.getArticle().getId() == article.getId()) {
                        dtoName += "/ " + articleMaterialConnection.getConnection();
                        if (articleMaterialConnection.getComment() != null) {
                            if (articleMaterialConnection.getComment().length() != 0) {
                                dtoName += "/ " + articleMaterialConnection.getComment();
                            }
                        }
                    }
                }
            }

            IdContentDto articleDto = new IdContentDto(article.getId(), dtoName);
            dtoName = "";
            fooSet.add(articleDto);
        }
        List<IdContentDto> finalList = new ArrayList<>(fooSet);
        return finalList;
    }

    public List<ArticleDtoForMainList> search(String description, String text, List<Integer> status, String
            startDate, String endDate, List<Integer> movement) {

        List<ArticleDtoForMainList> dtoSearchList = new ArrayList<>();
        Set<Article> searchList = new HashSet<Article>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date frmtStartDate = null;
        try {
            frmtStartDate = format.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date frmtEndDate = null;
        try {
            frmtEndDate = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (description != null) {
            if (status.get(0) == -1) {
                searchList = articleRepository.findByDescriptionAndDateAndMovement("%" + description.toLowerCase() + "%", frmtStartDate, frmtEndDate, movement);
            } else {
                searchList = articleRepository.findByDescriptionAndStatusAndDateAndMovement("%" + description.toLowerCase() + "%", status, frmtStartDate, frmtEndDate, movement);
            }
        } else if (text != null) {
            if (status.get(0) == -1) {
                searchList = articleRepository.findByTextAndDateAndMovement("%" + text.toLowerCase() + "%", frmtStartDate, frmtEndDate, movement);
            } else {
                searchList = articleRepository.findByTextAndStatusAndDateAndMovement("%" + text.toLowerCase() + "%", status, frmtStartDate, frmtEndDate, movement);
            }
        }

        ArticleDtoForMainList dtoArticle;
        for (Article article : searchList) {
            dtoArticle = new ArticleDtoForMainList();
            ArticleConverter.convertToArticleDtoForMainList(article, dtoArticle);
            dtoSearchList.add(dtoArticle);
        }
        return dtoSearchList;
    }


    //    public List<ArticleDto> search(List<String> title, String hash, String author, String language, String description, String text, List<Integer> status, String startDate, String endDate) throws ParseException {
    public List<ArticleDtoForMainList> filter
    (List<String> title, List<String> hash, List<String> author, List<String> org,
     List<String> location, List<String> language, String description, String
             text, List<String> misc,
     List<Integer> status, String startDate, String endDate, List<Integer> movement) {

        boolean isSingleFilter = false;
        int hashCurrentSize = 0;
        int orgCurrentSize = 0;
        int locationCurrentSize = 0;
        int authorCurrentSize = 0;
        int titleCurrentSize = 0;
        int languageCurrentSize = 0;
        int miscCurrentSize = 0;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date frmtStartDate = null;
        try {
            frmtStartDate = format.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date frmtEndDate = null;
        try {
            frmtEndDate = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        List<Article> searchList = new ArrayList<>();
        Set<Article> searchList = new HashSet<Article>();
        List<ArticleDtoForMainList> dtoSearchList = new ArrayList<>();

        List<String> titleList = new ArrayList<>();
        List<String> hashList = new ArrayList<>();
        List<String> authorList = new ArrayList<>();
        List<String> orgList = new ArrayList<>();
        List<String> locationList = new ArrayList<>();
        List<String> langList = new ArrayList<>();
        List<String> miscList = new ArrayList<>();

        if (status != null && status.size() > 0 && (title != null || hash != null || author != null || language != null || misc != null || org != null || location != null)) {

            if (text != null || description != null) {
                isSingleFilter = false;
            } else {
                if (title != null) {
                    titleCurrentSize = title.size();
                }
                if (hash != null) {
                    hashCurrentSize = hash.size();
                }
                if (author != null) {
                    authorCurrentSize = author.size();
                }
                if (misc != null) {
                    miscCurrentSize = misc.size();
                }
                if (location != null) {
                    locationCurrentSize = location.size();
                }
                if (language != null) {
                    languageCurrentSize = language.size();
                }
                if (org != null) {
                    orgCurrentSize = org.size();
                }
            }

            if (hashCurrentSize >= 1) {
                isSingleFilter = true;

                if (authorCurrentSize >= 1) {
                    isSingleFilter = false;

                } else if (titleCurrentSize >= 1) {
                    isSingleFilter = false;

                } else if (orgCurrentSize >= 1) {
                    isSingleFilter = false;

                } else if (locationCurrentSize >= 1) {
                    isSingleFilter = false;

                } else if (miscCurrentSize >= 1) {
                    isSingleFilter = false;

                } else if (languageCurrentSize >= 1) {
                    isSingleFilter = false;
                }
            } else {
                if (authorCurrentSize >= 1) {
                    isSingleFilter = true;

                    if (orgCurrentSize >= 1) {
                        isSingleFilter = false;

                    } else if (miscCurrentSize >= 1) {
                        isSingleFilter = false;

                    } else if (languageCurrentSize >= 1) {
                        isSingleFilter = false;

                    } else if (titleCurrentSize >= 1) {
                        isSingleFilter = false;

                    } else if (locationCurrentSize >= 1) {
                        isSingleFilter = false;
                    }
                } else {
                    if (titleCurrentSize >= 1) {
                        isSingleFilter = true;

                        if (orgCurrentSize >= 1) {
                            isSingleFilter = false;

                        } else if (locationCurrentSize >= 1) {
                            isSingleFilter = false;

                        } else if (miscCurrentSize >= 1) {
                            isSingleFilter = false;

                        } else if (languageCurrentSize >= 1) {
                            isSingleFilter = false;
                        }
                    } else {
                        if (orgCurrentSize >= 1) {
                            isSingleFilter = true;

                            if (locationCurrentSize >= 1) {
                                isSingleFilter = false;

                            } else if (miscCurrentSize >= 1) {
                                isSingleFilter = false;

                            } else if (languageCurrentSize >= 1) {
                                isSingleFilter = false;
                            }
                        } else {
                            if (locationCurrentSize >= 1) {
                                isSingleFilter = true;

                                if (miscCurrentSize >= 1) {
                                    isSingleFilter = false;

                                } else if (languageCurrentSize >= 1) {
                                    isSingleFilter = false;
                                }
                            } else {
                                if (languageCurrentSize >= 1) {
                                    isSingleFilter = true;

                                    if (miscCurrentSize >= 1) {
                                        isSingleFilter = false;
                                    }
                                } else {
                                    if (miscCurrentSize >= 1) {
                                        isSingleFilter = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (title != null && !title.isEmpty()) { //todo isEmpty - is it neccesary?
                int i;
                for (String t : title) {
                    titleList.add("%" + t + "%");
                }

                if (isSingleFilter) {
                    if (status.get(0) == -1) {
                        for (i = 0; i < titleList.size(); i++)
                            searchList.addAll(articleRepository.findByTitleAndDateAndMovement(titleList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

//                    } else if (status.get(0) == 3) {
//                        for (i = 0; i < titleList.size(); i++)
//                            searchList.addAll(articleRepository.findByTitleAndStatus(titleList.get(i), status));

                    } else {
                        for (i = 0; i < titleList.size(); i++)
                            searchList.addAll(articleRepository.findByTitleAndStatusAndDateAndMovement(titleList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                    }
                }
            }
            if (hash != null && !hash.isEmpty()) {
                int i;
                for (String h : hash) {
                    hashList.add(h + "%");
                }

                if (isSingleFilter) {
                    if (status.get(0) == -1) {
                        for (i = 0; i < hashList.size(); i++)
                            searchList.addAll(articleRepository.findByHashAndDateAndMovement(hashList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

                    } else {
                        for (i = 0; i < hashList.size(); i++)
                            searchList.addAll(articleRepository.findByHashAndStatusAndDateAndMovement(hashList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                    }
                }
            }
            if (author != null && !author.isEmpty()) {
                int i;
                for (String a : author) {
                    authorList.add("%" + a + "%");
                }

                if (isSingleFilter) {

                    if (status.get(0) == -1) {
                        for (i = 0; i < authorList.size(); i++)
                            searchList.addAll(articleRepository.findByAuthorAndDateAndMovement(authorList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

                    } else {
                        for (i = 0; i < authorList.size(); i++)
                            searchList.addAll(articleRepository.findByAuthorAndStatusAndDateAndMovement(authorList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                    }
                }
            }
            if (org != null && !org.isEmpty()) {
                int i;
                for (String o : org) {
                    orgList.add("%" + o + "%");
                }

                if (isSingleFilter) {
                    if (status.get(0) == -1) {
                        for (i = 0; i < orgList.size(); i++)
                            searchList.addAll(articleRepository.findByOrgAndDateAndMovement(orgList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

                    } else {
                        for (i = 0; i < orgList.size(); i++)
                            searchList.addAll(articleRepository.findByOrgAndStatusAndDateAndMovement(orgList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                    }
                }
            }
            if (location != null && !location.isEmpty()) {
                int i;
                for (String l : location) {
                    locationList.add("%" + l + "%");
                }

                if (isSingleFilter) {
                    if (status.get(0) == -1) {
                        for (i = 0; i < locationList.size(); i++)
                            searchList.addAll(articleRepository.findByLocationAndDateAndMovement(locationList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

                    } else {
                        for (i = 0; i < locationList.size(); i++)
                            searchList.addAll(articleRepository.findByLocationAndStatusAndDateandMovement(locationList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                    }
                }
            }
            if (language != null && !language.isEmpty()) {
                int i;
                for (String l : language) {
                    langList.add(l + "%");
                }

                if (isSingleFilter) {
                    if (status.get(0) == -1) {
                        for (i = 0; i < langList.size(); i++)
                            searchList.addAll(articleRepository.findByLangAndDateAndMovement(langList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

                    } else {
                        for (i = 0; i < langList.size(); i++)
                            searchList.addAll(articleRepository.findByLangAndStatusAndDateAndMovement(langList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                    }
                }
            }
            if (misc != null && !misc.isEmpty()) {

                int i;
                for (String m : misc) {
                    miscList.add("%" + m + "%");

                    if (isSingleFilter) {
                        if (status.get(0) == -1) {
                            for (i = 0; i < miscList.size(); i++)
                                searchList.addAll(articleRepository.findByMiscellanyAndDateAndMovement(miscList.get(i).toLowerCase(), frmtStartDate, frmtEndDate, movement));

//                        } else if (status.get(0) == 3) {
//                            for (i = 0; i < miscList.size(); i++)
//                                searchList.addAll(articleRepository.findByMiscellanyAndStatus(miscList.get(i), status));

                        } else {
                            for (i = 0; i < miscList.size(); i++)
                                searchList.addAll(articleRepository.findByMiscellanyAndStatusAndDateAndMovement(miscList.get(i).toLowerCase(), status, frmtStartDate, frmtEndDate, movement));
                        }
                    }
                }
            }
            if (text != null) {
                text = "%" + text + "%";
            }
            if (description != null) {
                description = "%" + description + "%";
            }

            if (!isSingleFilter) {
                if (status.get(0) == -1) {
                    searchList = articleRepository.findByFiltersAndDateAndMovement(
                            titleList.size() == 0 ? null : titleList.get(0).toLowerCase(),
                            hashList.size() == 0 ? null : hashList.get(0).toLowerCase(),
                            authorList.size() == 0 ? null : authorList.get(0).toLowerCase(),
                            orgList.size() == 0 ? null : orgList.get(0).toLowerCase(),
                            locationList.size() == 0 ? null : locationList.get(0).toLowerCase(),
                            langList.size() == 0 ? null : langList.get(0).toLowerCase(),
                            miscList.size() == 0 ? null : miscList.get(0).toLowerCase(),
                            text == null ? null : text.toLowerCase(),
                            description == null ? null : description.toLowerCase(),
                            frmtStartDate, frmtEndDate, movement);
                } else searchList = articleRepository.findByFiltersAndDateAndStatusAndMovement(
                        titleList.size() == 0 ? null : titleList.get(0).toLowerCase(),
                        hashList.size() == 0 ? null : hashList.get(0).toLowerCase(),
                        authorList.size() == 0 ? null : authorList.get(0).toLowerCase(),
                        orgList.size() == 0 ? null : orgList.get(0).toLowerCase(),
                        locationList.size() == 0 ? null : locationList.get(0).toLowerCase(),
                        langList.size() == 0 ? null : langList.get(0).toLowerCase(),
                        miscList.size() == 0 ? null : miscList.get(0).toLowerCase(),
                        text == null ? null : text.toLowerCase(),
                        description == null ? null : description.toLowerCase(),
                        status, frmtStartDate, frmtEndDate, movement);
            }

        } else {
            if (status.get(0) == -1) {
//                if (movement != null){  //to delete
                searchList = articleRepository.findAllByDateBetweenAndMovement(frmtStartDate, frmtEndDate, movement);
//                } else {
//                    searchList = articleRepository.findAllByDateBetween(frmtStartDate, frmtEndDate);
//                }
//        } else if (status.get(0) == 3) {  //remove special status for 'done' (now search for period as for other one)
//            searchList = articleRepository.findAllByStatus(status);
            } else {
//                if (movement != null) {  //
                searchList = articleRepository.findByDateAndStatusAndMovement(status, frmtStartDate, frmtEndDate, movement);
//                } else {
//                    searchList = articleRepository.findByDateAndStatus(status, frmtStartDate, frmtEndDate);
//                }
            }
        }


        ArticleDtoForMainList dtoArticle;
        for (Article article : searchList) {
            dtoArticle = new ArticleDtoForMainList();
            ArticleConverter.convertToArticleDtoForMainList(article, dtoArticle);
            dtoSearchList.add(dtoArticle);
        }
        return dtoSearchList;
    }


    @Override
    public void deleteById(Integer id) {
        articleRepository.deleteById(id);
    }
}
