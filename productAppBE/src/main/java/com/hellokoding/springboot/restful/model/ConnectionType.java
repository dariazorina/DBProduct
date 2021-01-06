package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_connection_type")
@Entity
@Data

public class ConnectionType {
    @Id
    @Column(name = "connection_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
}
