package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.*;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.OrgService;
import com.hellokoding.springboot.restful.service.UrlLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrgServiceImpl implements OrgService {

    private final OrgRepository orgRepository;
    private final OrgOrgRepository orgOrgRepository;
    private final OrgNameRepository orgNameRepository;
    private final StatusRepository statusRepository;
    private final HashTagRepository hashTagRepository;
    private final LocationRepository locationRepository;
    private final IsourceRepository isourceRepository;
    private final PersonRepository personRepository;
    private final OrgTypeRepository orgTypeRepository;
    private final ArticleRepository articleRepository;

    private final UrlLinkService urlLinkService;

    @Override
    public List<Org> findAll() {
        List<Org> all = orgRepository.findAll();
        return all;
    }

    public List<OrgDtoForMainList> findAll(List<Integer> mov) {

        List<OrgDtoForMainList> dtoAllOrgList = new ArrayList<>();
        List<Org> allOrg = orgRepository.findAllWithMovements(mov);

        OrgDtoForMainList currentOrgDto;
        for (Org o : allOrg) {
            currentOrgDto = new OrgDtoForMainList(o);
            //currentOrgDto.setOrgList(findByIdsAndSymmetrically(o.getId())); //now hide orgs in orgs list
            dtoAllOrgList.add(currentOrgDto);
        }
        return dtoAllOrgList;
    }

    @Override
    public List<IdContentDto> search(String q) {

        List<Org> orgSearchList = new ArrayList<>();
        String query = "%" + q.toLowerCase() + "%";

        orgSearchList = orgRepository.findByName(query);
//        System.out.println(orgSearchList);

        if (orgSearchList.size() > 0) {
            return transformOriginToDto(orgSearchList);
        }
        return null;
    }

    public List<IdContentDto> findByIds(List<Integer> idList) {

        List<Org> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Org> l = orgRepository.findById(id);

            if (l != null) {
                searchRes.add(l.get());
            }
        }
        return transformOriginToDto(searchRes);
    }

    public List<NameConnectionDto> findByIdsAndSymmetrically(Integer itemId) {

        Org connectedOrg, org;
        String dtoName = "", connection = "", comment = "";
        List<NameConnectionDto> finalList = new ArrayList<>();

        List<OrgOrgConnection> searchResSymm;// = new ArrayList<>();

        Optional<Org> orgOpt = orgRepository.findById(itemId);
        if (orgOpt.isPresent()) {
            org = orgOpt.get();             //   searchRes.add(l.get());

            searchResSymm = orgOrgRepository.findByIdSymm(itemId); //searching symm connections for this itemId

            for (OrgOrgConnection el : org.getOrgConnections()) { //simple connections
                Optional<Org> connOrgOpt = orgRepository.findById(el.getConnectedOrg().getId());

                if (connOrgOpt.isPresent()) {
                    connectedOrg = connOrgOpt.get();             //   searchRes.add(l.get());
                    if (connectedOrg.getNameList() != null) {
                        for (OrgName name : connectedOrg.getNameList()) {
                            if (name.getPriority() == 1) {
                                dtoName += name.getName();

                                if (name.getAbbr() != null && name.getAbbr().length() != 0) {
                                    dtoName += "/ " + name.getAbbr();
                                }
                            }
                        }
                    }
                    for (OrgOrgConnection orgOrgConnection : org.getOrgConnections()) {
                        if (orgOrgConnection.getConnectedOrg().getId().equals(connectedOrg.getId())) {
                            connection = orgOrgConnection.getConnection();

                            if (orgOrgConnection.getComment() != null) {
                                if (orgOrgConnection.getComment().length() != 0) {
                                    comment = orgOrgConnection.getComment();
                                }
                            }
                        }
                    }
                    NameConnectionDto orgDto = new NameConnectionDto(connectedOrg.getId(), dtoName, connection, comment);
                    dtoName = "";
                    connection = "";
                    comment = "";
                    finalList.add(orgDto);
                }
            }//for

            for (OrgOrgConnection orgOrgConnection : searchResSymm) {
                if (orgOrgConnection.getConnectedOrg().getId().equals(org.getId())) {

                    if (orgOrgConnection.getOrg().getNameList() != null) {
                        for (OrgName name : orgOrgConnection.getOrg().getNameList()) {
                            if (name.getPriority() == 1) {
                                dtoName += name.getName();
                                if (name.getAbbr() != null && name.getAbbr().length() != 0) {
                                    dtoName += "/ " + name.getAbbr();
                                }
                            }
                        }
                    }

                    connection = orgOrgConnection.getConnection();
                    if (orgOrgConnection.getComment() != null) {
                        if (orgOrgConnection.getComment().length() != 0) {
                            comment = orgOrgConnection.getComment();
                        }
                    }
                    NameConnectionDto orgDto = new NameConnectionDto(orgOrgConnection.getOrg().getId(), dtoName, connection, comment);
                    finalList.add(orgDto);
                    dtoName = "";
                    comment = "";
                }
            }
        }
        return finalList;
    }

    public List<IdContentDto> transformOriginToDto(List<Org> orgList) {
        Set<IdContentDto> fooSet = new TreeSet<>();
        String dtoName = "";

        for (Org org : orgList) {
            if (org.getNameList() != null) {
//// Generic method to check if an object array contains a particular value
//                public static<T> boolean isPresent(T[] a, T target)
//                {
//                    return Arrays.stream(a)
//                            .anyMatch(x -> target.equals(x));    // or use `target::equals`
//                }
//
//                Or using filters:
//
//// Generic method to check if an object array contains a particular value
//                public static<T> boolean isPresent(T[] a, T target)
//                {
//                    return Arrays.stream(a)
//                            .filter(x -> target.equals(x))        // or use `target::equals`
//                            .count() > 0;
//                }

///////////      name with prioirity = 1 set the first /  ////////////////
                Integer prStatus = 1;
                List<OrgName> currentOrgList = org.getNameList();

                OrgName orgNamePr1 = currentOrgList.stream()
                        .filter(orgName -> prStatus.equals(orgName.getPriority()))
                        .findAny()
                        .orElse(null);

                if (orgNamePr1 != null) {
                    dtoName += orgNamePr1.getName();

                    if (orgNamePr1.getAbbr() != null && orgNamePr1.getAbbr().length() != 0) {
                        dtoName += "/ " + orgNamePr1.getAbbr();
                    }

                    for (OrgName name : org.getNameList()) {
                        if (name.getPriority() == 0) {
                            dtoName += "/ " + name.getName();
                        }

                        if (name.getAbbr() != null && name.getAbbr().length() != 0) {
                            dtoName += "/ " + name.getAbbr();
                        }
                    }//for
                } else {
                    dtoName += "error";
                }
            }
            IdContentDto orgDto = new IdContentDto(org.getId(), dtoName);
            dtoName = "";
            fooSet.add(orgDto);
        }
        List<IdContentDto> finalList = new ArrayList<>(fooSet);
        return finalList;
    }

    @Override
    public Optional<OrgDto> findById(Integer id) {

        Optional<OrgDto> orgDto;
        Optional<Org> org = orgRepository.findById(id);

        orgDto = Optional.of(new OrgDto(org.get()));

        return orgDto;
    }

    @Override
    public Org saveColor(OrgDtoForMainList orgDto) {

        Org org;
        if (orgRepository.findById(orgDto.getId()).isPresent()) {
            org = orgRepository.findById(orgDto.getId()).get();

            org.setRgbSelection(orgDto.getRowColor());
            return orgRepository.save(org);

        } else
            return null;
    }

    @Override
    public Org save(OrgDto orgDto) {

        List<UrlLink> linkList = orgDto.getLinkList();
        List<UrlLink> linkListWithID = urlLinkService.getLinkListID(linkList);
        Org org;

        if (orgDto.getId() == null) {
            org = new Org();
        } else if (orgRepository.findById(orgDto.getId()).isPresent()) {
            org = orgRepository.findById(orgDto.getId()).get();
        } else
            return null;


        if (org.getLinkList() != null) {
            org.getLinkList().clear();
            orgRepository.flush();
        } else {
            org.setLinkList(new ArrayList<>());
        }

        if (org.getLinkList() == null) {
            org.setLinkList(linkListWithID);
        } else {
            org.getLinkList().addAll(linkListWithID);
        }

        if (org.getMovementList() != null) {
            org.getMovementList().clear();
            orgRepository.flush();
        } else {
            org.setMovementList(new ArrayList<>());
        }
        if (org.getMovementList() == null) {
            org.setMovementList(orgDto.getMovementList());
        } else {
            org.getMovementList().addAll(orgDto.getMovementList());
            //personRepository.flush();
        }

        List<OrgName> nameList = null;
        if (org.getNameList() != null) {
            nameList = new ArrayList<>(org.getNameList());
        }
        if (nameList != null) {
            org.getNameList().clear();
            orgRepository.flush();

            for (OrgName el : nameList) {
                orgNameRepository.deleteById(el.getId());
            }
            orgNameRepository.flush();
            nameList.clear();
        } else {
            nameList = new ArrayList<>();
        }

        List<OrgNameDto> nameDtoList = orgDto.getNameList();
        OrgName orgName;
        for (OrgNameDto nameDto : nameDtoList) {
            orgName = new OrgName();
            orgName.setName(nameDto.getName());
            orgName.setAbbr(nameDto.getAbbr());
            orgName.setPriority(nameDto.getPriority());
            nameList.add(orgName);
            orgNameRepository.save(orgName);
        }
        orgNameRepository.flush();

        if (org.getNameList() == null) {
            org.setNameList(nameList);
        } else {
            org.getNameList().addAll(nameList);
        }

        org.setFoundedYear(orgDto.getFoundationYear());
        org.setClosedYear(orgDto.getClosureYear());
        org.setDescription(orgDto.getDescription());
        org.setMiscellany(orgDto.getMiscellany());
        org.setRgbSelection(orgDto.getRowColor());

        Optional<Status> byName = statusRepository.getByName(orgDto.getStatus());
        if (byName.isPresent()) {
            org.setStatus(byName.get());
        }

        /////////////////org
        if (org.getOrgConnections() != null) {
            org.getOrgConnections().clear();
            orgRepository.flush();
        }

        Integer connectedOrgId;
        boolean isSymmConnection = false;

        Org connectedOrg = null;
        OrgOrgConnection orgOrgConnection;
        OrgOrgConnection connectedOrgConnection;

        List<OrgOrgConnection> orgOrgList = new ArrayList<>();
        List<OrgOrgConnection> connectedOrgList = new ArrayList<>();
        List<OrgOrgConnection> symmConnectionList;


        for (NameConnectionDto posDto : orgDto.getOrgList()) {
            connectedOrgId = posDto.getItemId();
            if (orgRepository.findById(connectedOrgId).isPresent()) {

                symmConnectionList = orgOrgRepository.findByIdSimple(connectedOrgId);
                for (OrgOrgConnection orgOrgConn : symmConnectionList) {
                    if (orgOrgConn.getConnectedOrg().getId().equals(org.getId())) {
                        isSymmConnection = true;
                        break;
                    }
                }
                if (!isSymmConnection) {
                    orgOrgConnection = new OrgOrgConnection();
                    orgOrgConnection.setConnectedOrg(orgRepository.findById(connectedOrgId).get());
                    orgOrgConnection.setOrg(org);
                    orgOrgConnection.setConnection(posDto.getConnection());
                    orgOrgConnection.setComment(posDto.getComment());

                    orgOrgList.add(orgOrgConnection);
                } else {//save connection for material
                    connectedOrg = orgRepository.findById(connectedOrgId).get();

                    connectedOrgConnection = new OrgOrgConnection();
                    connectedOrgConnection.setOrg(orgRepository.findById(connectedOrgId).get());
                    connectedOrgConnection.setConnectedOrg(org);
                    connectedOrgConnection.setConnection(posDto.getConnection());
                    connectedOrgConnection.setComment(posDto.getComment());

                    connectedOrgList.add(connectedOrgConnection);

                    if (connectedOrg.getOrgConnections() == null) {
                        connectedOrg.setOrgConnections(connectedOrgList);
                    } else {
                        //remove already existed entities
                        List<OrgOrgConnection> elList = new ArrayList<>();
                        for (OrgOrgConnection orgOrgConn : connectedOrg.getOrgConnections()) {
                            if (orgOrgConn.getConnectedOrg().getId().equals(org.getId())) {
                                elList.add(orgOrgConn);
                            }
                        }
                        connectedOrg.getOrgConnections().removeAll(elList);
                        orgRepository.flush();
                        connectedOrg.getOrgConnections().addAll(connectedOrgList);
                    }
                }
                isSymmConnection = false;
            }
        }//for (NameConnectionDto posDto : orgDto.getOrgList())

        if (org.getId() != null) {
            //delete symm connections
            List<NameConnectionDto> startListConnectedOrgsForOrg = findByIdsAndSymmetrically(org.getId());
            List<NameConnectionDto> resultListConnectedOrgsForOrg = orgDto.getOrgList();

            List<NameConnectionDto> differences = startListConnectedOrgsForOrg.stream()
                    .filter(element -> !resultListConnectedOrgsForOrg.contains(element))
                    .collect(Collectors.toList());

            for (NameConnectionDto nmdto : differences) {
                connectedOrg = orgRepository.findById(nmdto.getItemId()).get();

                //remove already existed entities
                List<OrgOrgConnection> elList = new ArrayList<>();
                for (OrgOrgConnection orgOrgConn : connectedOrg.getOrgConnections()) {
                    if (orgOrgConn.getConnectedOrg().getId().equals(org.getId())) {
                        elList.add(orgOrgConn);
                    }
                }
                connectedOrg.getOrgConnections().removeAll(elList);
                orgRepository.flush();
            }
        }

        if (org.getOrgConnections() == null) {
            org.setOrgConnections(orgOrgList);
        } else {
            org.getOrgConnections().addAll(orgOrgList);
        }

        ///////////
        ///location
        if (org.getLocationConnections() != null) {
            org.getLocationConnections().clear();
            orgRepository.flush();
        }

        Integer locatonId;
        OrgLocationConnection locationConnection;
        List<OrgLocationConnection> locationConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : orgDto.getLocationList()) {

            locatonId = connectionDto.getItemId();
            if (locationRepository.findById(locatonId).isPresent()) {
                locationConnection = new OrgLocationConnection();
                locationConnection.setLocation(locationRepository.findById(locatonId).get());
                locationConnection.setOrg(org);
                locationConnection.setConnection(connectionDto.getConnection());
                locationConnection.setComment(connectionDto.getComment());

                locationConnectionList.add(locationConnection);
            }
        }

        if (org.getLocationConnections() == null) {
            org.setLocationConnections(locationConnectionList);
        } else {
            org.getLocationConnections().addAll(locationConnectionList);
        }


        ///article
        if (org.getArticleConnections() != null) {
            org.getArticleConnections().clear();
            orgRepository.flush();
        }

        Integer articleId;
        ArticleOrgConnection articleConnection;
        List<ArticleOrgConnection> articleConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : orgDto.getArticleList()) {

            articleId = connectionDto.getItemId();
            if (articleRepository.findById(articleId).isPresent()) {
                articleConnection = new ArticleOrgConnection();
                articleConnection.setArticle(articleRepository.findById(articleId).get());
                articleConnection.setOrg(org);
                articleConnection.setConnection(connectionDto.getConnection());
                articleConnection.setComment(connectionDto.getComment());

                articleConnectionList.add(articleConnection);
            }
        }

        if (org.getArticleConnections() == null) {
            org.setArticleConnections(articleConnectionList);
        } else {
            org.getArticleConnections().addAll(articleConnectionList);
        }


        //isource
        if (org.getIsourceConnections() != null) {
            org.getIsourceConnections().clear();
            orgRepository.flush();
        }

        Integer isourceId;
        OrgIsourceConnection isourceConnection;
        List<OrgIsourceConnection> isourceConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : orgDto.getIsourceList()) {

            isourceId = connectionDto.getItemId();
            if (isourceRepository.findById(isourceId).isPresent()) {
                isourceConnection = new OrgIsourceConnection();
                isourceConnection.setIsource(isourceRepository.findById(isourceId).get());
                isourceConnection.setOrg(org);
                isourceConnection.setConnection(connectionDto.getConnection());
                isourceConnection.setComment(connectionDto.getComment());

                isourceConnectionList.add(isourceConnection);
            }
        }

        if (org.getIsourceConnections() == null) {
            org.setIsourceConnections(isourceConnectionList);
        } else {
            org.getIsourceConnections().addAll(isourceConnectionList);
        }

        ///persons
        if (org.getPersonConnections() != null) {
            org.getPersonConnections().clear();
            orgRepository.flush();
        }

        Integer personId;
        OrgPersonConnection personConnection;
        List<OrgPersonConnection> personConnectionList = new ArrayList<>();
        for (ItemConnectionDto connectionDto : orgDto.getPersonList()) {

            personId = connectionDto.getItemId();
            if (personRepository.findById(personId).isPresent()) {
                personConnection = new OrgPersonConnection();
                personConnection.setPerson(personRepository.findById(personId).get());
                personConnection.setOrg(org);
                personConnection.setConnection(connectionDto.getConnection());
                personConnection.setComment(connectionDto.getComment());

                personConnectionList.add(personConnection);
            }
        }

        if (org.getPersonConnections() == null) {
            org.setPersonConnections(personConnectionList);
        } else {
            org.getPersonConnections().addAll(personConnectionList);
        }


        /////////////////////org-hashtag////////////////////////
        OrgHashtag orgHashtag, previousOrgHashtag, previousPreviousOrgHashtag;
        HashTag hashTag, hashTagPrevious, hashTagPreviousPrevious;
        List<OrgHashtag> hashtagList = new ArrayList<>();
        Integer id;

        if (org.getHashtagList() != null) {
            for (OrgHashtag orgH : org.getHashtagList()) {
                orgH.setOrg(null);
            }
            org.getHashtagList().clear();
            orgRepository.flush();
        } else {
            org.setHashtagList(new ArrayList<>());
        }

        for (String hashtag_content : orgDto.getHashtagList()) {

//            id = hashTagRepository.getHashTagByContent(hashtag_content).getId();
            hashTag = hashTagRepository.getHashTagByContent(hashtag_content);
            orgHashtag = new OrgHashtag();
//            hashTag = hashTagRepository.findById(id).get();

            if (hashTag.getParentId() == 0) {    ///////////////////////hashtag level 1

                orgHashtag.setHashtag(hashTag);
                orgHashtag.setLevel(1);
                orgHashtag.setAssigned_hashtag(hashTag);
                orgHashtag.setOrg(org);

                hashtagList.add(orgHashtag);

            } else {  ///////////////////////hashtag level 2/3

                hashTagPrevious = hashTagRepository.findById(hashTag.getParentId()).get();
                previousOrgHashtag = new OrgHashtag();

                if (hashTagPrevious.getParentId() == 0) {   ///////////////////////hashtag level 2

                    previousOrgHashtag.setHashtag(hashTagPrevious);
                    previousOrgHashtag.setLevel(1);
                    previousOrgHashtag.setAssigned_hashtag(hashTag);
                    previousOrgHashtag.setOrg(org);

                    orgHashtag.setHashtag(hashTag);
                    orgHashtag.setLevel(2);
                    orgHashtag.setAssigned_hashtag(hashTag);
                    orgHashtag.setOrg(org);

                    hashtagList.add(orgHashtag);
                    hashtagList.add(previousOrgHashtag);

                } else {   ///////////////////////hashtag level 3

                    hashTagPreviousPrevious = hashTagRepository.findById(hashTagPrevious.getParentId()).get();
                    previousPreviousOrgHashtag = new OrgHashtag();

                    if (hashTagPreviousPrevious.getParentId() == 0) {

                        previousPreviousOrgHashtag.setHashtag(hashTagPreviousPrevious);
                        previousPreviousOrgHashtag.setLevel(1);
                        previousPreviousOrgHashtag.setAssigned_hashtag(hashTag);
                        previousPreviousOrgHashtag.setOrg(org);

                        previousOrgHashtag.setHashtag(hashTagPrevious);
                        previousOrgHashtag.setLevel(2);
                        previousOrgHashtag.setAssigned_hashtag(hashTag);
                        previousOrgHashtag.setOrg(org);

                        orgHashtag.setHashtag(hashTag);
                        orgHashtag.setLevel(3);
                        orgHashtag.setAssigned_hashtag(hashTag);
                        orgHashtag.setOrg(org);

                        hashtagList.add(orgHashtag);
                        hashtagList.add(previousOrgHashtag);
                        hashtagList.add(previousPreviousOrgHashtag);
                    }
                }//level 3
            } //level 2/3
        }//for

        if (org.getHashtagList() == null) {
            org.setHashtagList(hashtagList);
        } else {
            org.getHashtagList().addAll(hashtagList);
        }


//        ////*/*/*/*/*/*/*///////////org-type////*/*/*/*/*/*/*/*/*/*/*//////////

        OrgType orgType = orgDto.getType();
        if (orgType != null) {
            if (orgType.getId() != null) {
                Optional<OrgType> byId = orgTypeRepository.findById(orgType.getId());
                byId.ifPresent(org::setOrgType);
            }
        }
//        if (connectedOrg != null)
//            orgRepository.save(connectedOrg);
        return orgRepository.save(org);
    }

    @Override
    public void deleteById(Integer id) {
        orgRepository.deleteById(id);
    }

//    public void addOrgFromEventTableToOrgTable() {  //todo Прояснить. Добавила в табл.Оганизаций названия организаций из табл.Событий
//        List<Event> all = eventRepository.findAll();

       /* for (Event event : all) {
            String orgs = event.getOrgs();
            if (orgs != null) {
                orgs = orgs.substring(1, orgs.length() - 1); //убираем { }
                String[] split = orgs.split(","); //разделяем по "," на массив строк

                for (String orgWithQuotesOrNot : split) { ////todo звбила на кавычки в назв организ.,т.к.они то есть, то их нет
                    // String actorName = orgWithQuotesOrNot.substring(1, orgWithQuotesOrNot.length() - 1);//del "
                    Org orgByName = orgRepository.getOrgByNameRus(orgWithQuotesOrNot); //ищем автора в БД  //"Фонд Черкесы"); //
                    //  orgRepository.save(orgByName);

                    if (orgByName == null) {
                        Org s1 = new Org();
                        s1.setNameRus(orgWithQuotesOrNot);
                        s1.setCountry_id(1);
//                        String act="{abc, def}";
//                        s1.setActors(act);
////                        s1.setMovement_id("1");
//                        s1.setClosed(1789);
//                        s1.setFounded(1589);

                    orgRepository.save(s1);
                    }
                }
            }
        }*/
//    }

    //todo перезаписать вместо названий организаций их айдишки (добавленые только что в табл.Организаций)
/*    public void changeOrgTitleToOrgId() {
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String organizations = event.getOrgs();
            if (organizations != null) {
                organizations = organizations.substring(1, organizations.length() - 1); //убираем { }
                String[] split = organizations.split(","); //разделяем по "," на массив строк

                String orgs = "{";
                for (String orgWithQuotesOrNot : split) { ////todo забила на кавычки в назв организ.,т.к.они то есть, то их нет
                    Org orgByName = orgRepository.getOrgByNameRus(orgWithQuotesOrNot);
                    if (orgByName != null) {
                        orgs += String.valueOf(orgByName.getId());
                        orgs += ", ";
                    }
                    orgs = orgs.substring(0, orgs.length() - 2); //убираем ", "
                    orgs += "}";
                    event.setOrgs(orgs);
                    eventRepository.save(event);
                }
            }
        }
    }*/


/*    public void initializeReferenceBetweenOrgAndEvent() {
        List<Event> all = eventRepository.findAll();

        for (Event event : all) {
            String orgs = event.getOrgs();
            if (orgs != null) {
                orgs = orgs.substring(1, orgs.length() - 1); //убираем { }
                String[] split = orgs.split(","); //разделяем по "," на массив строк

                List<Org> orgList = new LinkedList<>();
                for (String org_id : split) {
                    Org orgById = orgRepository.getOne(Integer.valueOf(org_id));
                    orgList.add(orgById);
                }
                event.setOrgList(orgList);
                eventRepository.save(event);
            }
        }
    }*/

}
