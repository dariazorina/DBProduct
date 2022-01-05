package com.hellokoding.springboot.restful.model.dto;

//dto for add/edit, details page for article, person, org, pro all connections except one-type connections
public class NameConnectionDto implements Comparable<NameConnectionDto> {
    private Integer itemId;
    private String name;
    private String connection;
    private String comment;

    public NameConnectionDto() {
    }

    public NameConnectionDto(Integer itemId, String name, String connection, String comment) {
        this.itemId = itemId;
        this.name = name;
        this.connection = connection;
        this.comment = comment;
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

    @Override
    public boolean equals(Object obj) {
        return ((NameConnectionDto) obj).itemId.equals(itemId)&&((NameConnectionDto) obj).name.equals(name);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    @Override
    public int compareTo(NameConnectionDto obj) {
        return connection.compareToIgnoreCase(obj.getConnection());
    }
}
