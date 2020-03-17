package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository <Position, Integer> {
}
