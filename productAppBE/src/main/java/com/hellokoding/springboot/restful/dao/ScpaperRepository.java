package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Scpaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScpaperRepository extends JpaRepository<Scpaper, Integer> {
}
