package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.UrlLinkRepository;
import com.hellokoding.springboot.restful.model.UrlLink;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LinkListIDCreation {

    private final UrlLinkRepository linkRepository;

    void getLinkListID(List<UrlLink> linkList, List<UrlLink> linkListWithID) {

        UrlLink linkByContent;
        UrlLink linkWithID;

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
    }
}
