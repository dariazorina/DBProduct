package com.hellokoding.springboot.restful.model.dto;

//dto for add/edit, details page for article-article, person-person, org-org, pro-pro connections
public class OneTypeConnectionDto implements Comparable<OneTypeConnectionDto> {
    private Integer itemId;
    private String name;
    private String connection;
    private String comment;
    private Boolean isParent;

    public OneTypeConnectionDto() {
    }

    public OneTypeConnectionDto(Integer itemId, String name, String connection, String comment, Boolean isParent) {
        this.itemId = itemId;
        this.name = name;
        this.connection = connection;
        this.comment = comment;
        this.isParent = isParent;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        return ((OneTypeConnectionDto) obj).itemId.equals(itemId)&&((OneTypeConnectionDto) obj).name.equals(name);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    @Override
    public int compareTo(OneTypeConnectionDto obj) {
        return connection.compareToIgnoreCase(obj.getConnection());
    }
}
