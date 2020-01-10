package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.dao.MovementRepository;
import com.hellokoding.springboot.restful.dao.OrgRepository;
import com.hellokoding.springboot.restful.model.Movement;
import com.hellokoding.springboot.restful.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {
    private final IsourceRepository isourceRepository;
    private final OrgRepository orgRepository;
    private final MovementRepository movementRepository;


    public List<Movement> findAll() {
        List<Movement> all = movementRepository.findAll();
        return all;
    }

    public Optional<Movement> findById(Integer id) {
        Optional<Movement> byId = movementRepository.findById(id);
        return byId;
    }

    @Override
    @Transactional(noRollbackFor = UniqueConstraintViolationException.class)
    public Movement save(Movement stock) throws UniqueConstraintViolationException {
        try {
            return movementRepository.save(stock);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueConstraintViolationException();
        }
    }

    public void deleteById(Integer id) {
        movementRepository.deleteById(id);
    }

/*    public void initializeReferenceBetweenMovementAndIsource() {
        List<Isource> all = isourceRepository.findAll();

        for (Isource isource : all) {
            String movement = isource.getMovement_id();
            if (movement != null) {
                movement = movement.substring(1, movement.length() - 1); //убираем { }
                String[] split = movement.split(","); //разделяем по "," на массив строк

                List<Movement> movList = new LinkedList<>();
                for (String mov_id : split) {
                    Movement movById = movementRepository.getOne(Integer.valueOf(mov_id));
                    movList.add(movById);
                }
                isource.setMovementList(movList);
                isourceRepository.save(isource);
            }
        }
    }

    public void initializeReferenceBetweenMovementAndOrg() {
        List<Org> all = orgRepository.findAll();

        for (Org org : all) {
            String movement = org.getMovement_id();
            if (movement != null) {
                movement = movement.substring(1, movement.length() - 1); //убираем { }
                String[] split = movement.split(","); //разделяем по "," на массив строк

                List<Movement> movList = new LinkedList<>();
                for (String mov_id : split) {
                    Movement movById = movementRepository.getOne(Integer.valueOf(mov_id));
                    movList.add(movById);
                }
                org.setMovementList(movList);
                orgRepository.save(org);
            }
        }
    }*/

}
