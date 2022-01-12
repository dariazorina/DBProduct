package com.hellokoding.springboot.restful.model.dto;

import java.util.List;


public class PagedDataDto {

    private Long entitiesQuantity;
//    private List<Object> data;
    private Object data;


    public PagedDataDto() {
    }

    public PagedDataDto(Long entitiesQuantity, Object data) {
        this.entitiesQuantity = entitiesQuantity;
        this.data = data;
    }

    public Long getEntitiesQuantity() {
        return entitiesQuantity;
    }

    public void setEntitiesQuantity(Long entitiesQuantity) {
        this.entitiesQuantity = entitiesQuantity;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //    public List<Object> getData() {
//        return data;
//    }

//    public void setData(List<Object> data) {
//        this.data = data;
//    }

}
