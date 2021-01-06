package com.hellokoding.springboot.restful.model.dto;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class OrgDto implements Comparable<OrgDto> {

    private Integer id;
    private String name;

    public OrgDto(Integer id, String s) {
        this.id = id;
        this.name = s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((OrgDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(OrgDto obj) {
        return name.compareToIgnoreCase(obj.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}