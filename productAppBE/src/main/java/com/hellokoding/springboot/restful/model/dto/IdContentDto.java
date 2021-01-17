package com.hellokoding.springboot.restful.model.dto;

public class IdContentDto implements Comparable<IdContentDto> {
    private Integer id;
    private String content;

    public IdContentDto(Integer id, String s) {
        this.id = id;
        this.content = s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((IdContentDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(IdContentDto obj) {
        return content.compareToIgnoreCase(obj.getContent());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}