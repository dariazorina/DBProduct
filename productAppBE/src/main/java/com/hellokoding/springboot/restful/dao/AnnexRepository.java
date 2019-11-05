package com.hellokoding.springboot.restful.dao;
import com.hellokoding.springboot.restful.model.Annex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnexRepository extends JpaRepository <Annex, Integer> {
    Annex getAnnexByFilename(String filename);
}
