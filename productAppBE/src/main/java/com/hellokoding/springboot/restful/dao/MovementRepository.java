package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository  extends JpaRepository <Movement, Integer> {
    Movement getMovementById(Integer id);
}
