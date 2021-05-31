package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Authority findByName(String name);
}
