package com.hellokoding.springboot.restful.model.dto;

/////////////////////////////////////////NB! for autocomplete in location search//////////////////////////////
public class LocationDto implements Comparable<LocationDto> {

    private Integer id;
    private String country;

    public LocationDto(Integer id, String s) {
        this.id = id;
        this.country = s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((LocationDto) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(LocationDto obj) {
        return country.compareToIgnoreCase(obj.getCountry());
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}