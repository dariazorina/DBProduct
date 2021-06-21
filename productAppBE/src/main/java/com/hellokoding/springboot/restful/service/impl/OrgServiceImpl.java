package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.EventRepository;
import com.hellokoding.springboot.restful.dao.OrgRepository;
import com.hellokoding.springboot.restful.model.Org;
import com.hellokoding.springboot.restful.model.dto.OrgDto;
import com.hellokoding.springboot.restful.service.OrgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor

public class OrgServiceImpl implements OrgService {

    private final OrgRepository orgRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Org> findAll() {
        List<Org> all = orgRepository.findAll();
        return all;
    }

    @Override
    public List<OrgDto> search(String q, Integer mov) {

        Set<OrgDto> fooSet = new TreeSet<>();
        String dtoName;
        List<Org> orgSearchList = new ArrayList<>();

        orgSearchList = orgRepository.findByOrgName("%" + q + "%", mov); //findByNameRusStartsWithIgnoreCase(q);

        System.out.println("=====================================");
        System.out.println(orgSearchList);

        if (orgSearchList.size() > 0) {
            for (Org org : orgSearchList) {

                dtoName = org.getNameRus();  //nameRus - mandatory field

                if (org.getAbbrRus() != null) {
                    dtoName += ", " + org.getAbbrRus();
                }
                if (org.getName() != null) {
                    dtoName += ", " + org.getName();
                }
                if (org.getAbbr() != null) {
                    dtoName += ", " + org.getAbbr();
                }
                if (org.getNameEng() != null) {
                    dtoName += ", " + org.getNameEng();
                }
                if (org.getAbbrEng() != null) {
                    dtoName += ", " + org.getAbbrEng();
                }
                if (org.getFounded() != null) {
                    dtoName += ", " + org.getFounded();
                }

                //todo add another fields

                OrgDto orgDto = new OrgDto(org.getId(), dtoName);
                fooSet.add(orgDto);
            }

            List<OrgDto> finalList = new ArrayList<>(fooSet);
            return finalList;
        }
        return null;
    }

    public List<Org> findByIds(List<Integer> idList) {

        List<Org> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Org> l = orgRepository.findById(id);

            if (l != null) {
                searchRes.add(l.get());
            }
        }
        return searchRes;
    }

    @Override
    public Optional<Org> findById(Integer id) {
        return orgRepository.findById(id);
    }

    @Override
    public Org save(Org stock) {
        return orgRepository.save(stock);
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
