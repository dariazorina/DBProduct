package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.Annex;
import com.hellokoding.springboot.restful.service.AnnexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AnnexServiceImpl implements AnnexService {

    private final AnnexRepository annexRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Annex> findAll() {
        return annexRepository.findAll();
    }

    @Override
    public Optional<Annex> findById(Integer id) {
        return annexRepository.findById(id);
    }

    @Override
    public Annex save(Annex stock) {
        return annexRepository.save(stock);
    }

    @Override
    public void deleteById(Integer id) {
        annexRepository.deleteById(id);
    }


    @Override
    public void fillAnnexTableFromEvent() {                 //// fill table annex
//        List<Event> all = eventRepository.findAll();
//
//        for (Event event : all) {
//            String annex = event.getAnnex();
//            if (annex != null) {
//                annex = annex.substring(1, annex.length() - 1); //убираем { }
//                String[] split = annex.split(","); //разделяем по "," на массив строк
//
//                if (split.length > 1){                  //////{title, path}
//                    Annex annexByName = annexRepository.getAnnexByFilename(split[1]);
//                    if (annexByName == null) {
//                        Annex s1 = new Annex();
//                        s1.setFilename(split[1]);
//                    }
//                }
//            }
//        }
    }

    @Override
    public void initializeReferenceBetweenAnnexAndEvent(){

    }
}
