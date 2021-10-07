package com.hellokoding.springboot.restful.model.dto;

public class OrgNameDto implements Comparable<OrgNameDto> {

    private Integer id;
    private String name;
    private String abbr;
    private Integer priority;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        return ((OrgNameDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(OrgNameDto obj) {
        return name.compareToIgnoreCase(obj.getName());
    }


}
