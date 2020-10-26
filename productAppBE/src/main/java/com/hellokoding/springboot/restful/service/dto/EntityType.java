package com.hellokoding.springboot.restful.service.dto;

public enum EntityType {

    ARTICLE("article"),
    EVENT("event"),
    PERSON("person");

    private String name;

    EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
