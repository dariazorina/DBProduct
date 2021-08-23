package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_person_snp")
@Data
public class SurnameNamePatr {

    @Id
    @Column(name = "snp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String surname;
    private String name;
    private String patronymic;
    private Integer priority;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SurnameNamePatr))
            return false;

        return
                id != null &&
                        id.equals(((SurnameNamePatr) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
