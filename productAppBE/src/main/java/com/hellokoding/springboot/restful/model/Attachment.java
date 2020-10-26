package com.hellokoding.springboot.restful.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_attachment")
@Data
@ToString
public class Attachment {
    @Id
    @Column(name = "attachment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
