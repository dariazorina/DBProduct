package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.MaterialType;
import com.hellokoding.springboot.restful.service.MTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MTypeServiceImpl implements MTypeService {

    private final MTypeRepository mtypeRepository;
    private final ArticleRepository articleRepository;
    private final EventRepository eventRepository;
    private final ScpaperRepository scpaperRepository;
    private final IsourceRepository isourceRepository;


    @Override
    public List<MaterialType> findAll() {
        return mtypeRepository.findAll();
    }

    @Override
    public Optional<MaterialType> findById(Integer id) {
        return mtypeRepository.findById(id);
    }

    @Override
    public List<MaterialType> findLeafs(Integer id) {
        return mtypeRepository.findByParentId(id);
    }

    @Override
    public MaterialType save(MaterialType stock) { //enable to save equal values for different parents
        boolean isMTDuplicate = false;
        stock.setContent(stock.getContent().toLowerCase());
        List<MaterialType> materialTypeByContentList = mtypeRepository.getMaterialTypeByContent(stock.getContent()); //ищем мт в БД
        if (materialTypeByContentList == null) {
            return mtypeRepository.save(stock);
        } else {
            for (MaterialType mt: materialTypeByContentList) {
                if (mt.getParentId().equals(stock.getParentId())){
                    isMTDuplicate = true;
                    break;
                }
            }
            if (isMTDuplicate) {
                return null;
            }
            return mtypeRepository.save(stock);
        }
    }

    @Override
    public void deleteById(Integer id) {

        List<MaterialType> allMType = mtypeRepository.findAll();
        List<Integer> topLevelIdList = new ArrayList<>();
        List<Integer> bottomLevelIdList = new ArrayList<>();

        for (MaterialType mt : allMType) {
            if (mt.getParentId().equals(id)) {
                topLevelIdList.add(mt.getId());
            }
        }

        for (Integer branchId : topLevelIdList) {
            for (MaterialType mt : allMType) {
                if (mt.getParentId().equals(branchId)) {
                    bottomLevelIdList.add(mt.getId());
                }
            }
        }

        for (Integer leafId : bottomLevelIdList) {
            mtypeRepository.deleteById(leafId);
        }

        for (Integer branchId : topLevelIdList) {
            mtypeRepository.deleteById(branchId);
        }

        mtypeRepository.deleteById(id);
    }

    @Override
    public List<MaterialType> search(String q) {
        return mtypeRepository.findByContentStartsWithIgnoreCaseOrderByContentDesc(q);
    }
}
