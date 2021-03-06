package com.hellokoding.springboot.restful.model.dto;

public class PositionDto implements Comparable<PositionDto> {


    private Integer orgId;
    private String position;
    private String comment;


    public PositionDto() {
    }

    public PositionDto(Integer orgId, String position, String comment) {
        this.orgId = orgId;
        this.position = position;
        this.comment = comment;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        return ((PositionDto) obj).orgId.equals(orgId)&&((PositionDto) obj).position.equals(position);
    }

    @Override
    public int hashCode() {
        return orgId.hashCode();
    }

    @Override
    public int compareTo(PositionDto obj) {
        return position.compareToIgnoreCase(obj.getPosition());
    }
}
