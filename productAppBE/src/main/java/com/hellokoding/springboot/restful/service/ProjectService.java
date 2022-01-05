package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Project;
import com.hellokoding.springboot.restful.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();

    Integer getQuantityAllProjectsWithMovement(List<Integer> mov);

    List<ProjectDtoForMainList> findAll(List<Integer> mov, Integer page, Integer size);

    Optional<ProjectDto> findById(Integer id);
//    ProjectDto findById(Integer id);

    Project save(ProjectDto stock);

    Project saveColor(ProjectDtoForMainList stock);

    void deleteById(Integer id);

    List<IdContentDto> search(String q);

    List<IdContentDto> findByIds(List<Integer> idList);

    List<ProjectDtoForMainList> filter(List<String> hash, List<String> name, List<String> location, List<String> org, List<Integer> mov);

    List<OneTypeConnectionDto> findByIdsAndSymmetrically(Integer id);
}
