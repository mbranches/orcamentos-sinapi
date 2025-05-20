package com.branches.cpu.model;

public class Supply {
    private Long id;
    private Long code;
    private String description;
    private String unitMeasurement;
    private String originPrice;
    private Double price;

    public Supply(Long id, Long code, String description, String unitMeasurement, String originPrice, Double price) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.unitMeasurement = unitMeasurement;
        this.originPrice = originPrice;
        this.price = price;
    }
    @Override
    public String toString() {
        return description;
    }

    public Long getId() {
        return this.id;
    }

    public Long getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUnitMeasurement() {
        return this.unitMeasurement;
    }

    public String getOriginPrice() {
        return this.originPrice;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
