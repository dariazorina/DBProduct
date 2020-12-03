package com.hellokoding.springboot.restful.model.dto;

//dto for all location connections
public class ItemConnectionDto implements Comparable<ItemConnectionDto> {


    private Integer itemId;
    private String connection;
    private String comment;


    public ItemConnectionDto() {
    }

    public ItemConnectionDto(Integer itemId, String connection, String comment) {
        this.itemId = itemId;
        this.connection = connection;
        this.comment = comment;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String position) {
        this.connection = position;
    }

    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ItemConnectionDto) obj).itemId.equals(itemId)&&((ItemConnectionDto) obj).connection.equals(connection);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    @Override
    public int compareTo(ItemConnectionDto obj) {
        return connection.compareToIgnoreCase(obj.getConnection());
    }
}
