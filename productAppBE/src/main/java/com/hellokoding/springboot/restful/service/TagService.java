package com.hellokoding.springboot.restful.service;
import com.hellokoding.springboot.restful.model.Status;
import com.hellokoding.springboot.restful.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> findAll();
    List<Tag> search(String q);
}
