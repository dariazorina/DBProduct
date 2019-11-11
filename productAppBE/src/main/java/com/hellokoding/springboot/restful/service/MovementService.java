package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.dao.MovementRepository;
import com.hellokoding.springboot.restful.dao.OrgRepository;
import com.hellokoding.springboot.restful.model.Movement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovementService {
    private final IsourceRepository isourceRepository;
    private final OrgRepository orgRepository;
    private final MovementRepository movementRepository;


    public List<Movement> findAll() {
        List<Movement> all = movementRepository.findAll();
        return all;
    }

    public Optional<Movement> findById(Long id) {
        Optional<Movement> byId = movementRepository.findById(Math.toIntExact(id));
        return byId;
    }

    public Movement save(Movement stock) {
        return movementRepository.save(stock);
    }

    public void deleteById(Long id) {
        movementRepository.deleteById(Math.toIntExact(id));
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
