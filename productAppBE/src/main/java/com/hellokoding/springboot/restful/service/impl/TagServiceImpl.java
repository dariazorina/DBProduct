package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.StatusRepository;
import com.hellokoding.springboot.restful.dao.TagRepository;
import com.hellokoding.springboot.restful.model.Status;
import com.hellokoding.springboot.restful.model.Tag;
import com.hellokoding.springboot.restful.service.StatusService;
import com.hellokoding.springboot.restful.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

//    @Override
//    public Optional<Status> findById(Integer id) {
//        return statusRepository.findById(id);
//    }

    @Override
    public List<Tag> search(String q) {
        return tagRepository.getTagByContent(q);
    }
}
