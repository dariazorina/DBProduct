package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="t_position")
@Data
public class Position {

    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "person_id")
    //////////@JsonIgnore  //was active (without NewPersonDto)
    private Person person;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "org_id")
  //  @JsonIgnore
    private Org org;
    private String position; //todo?


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Position))
            return false;

        return
                id != null &&
                        id.equals(((Position) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
