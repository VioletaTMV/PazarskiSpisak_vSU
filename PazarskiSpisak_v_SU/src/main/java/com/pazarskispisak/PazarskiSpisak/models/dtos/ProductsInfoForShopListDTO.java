package com.pazarskispisak.PazarskiSpisak.models.dtos;

import org.springframework.format.annotation.NumberFormat;

public class ProductsInfoForShopListDTO {

    private Long ingredientId;
    private String ingredientName;
    private String totalQty;
    private String measurementUnitForShopList;
    private boolean checked;

    public ProductsInfoForShopListDTO(){    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getMeasurementUnitForShopList() {
        return measurementUnitForShopList;
    }
    public void setMeasurementUnitForShopList(String measurementUnitForShopList) {
        this.measurementUnitForShopList = measurementUnitForShopList;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "ProductsInfoForShopListDTO{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", totalQty='" + totalQty + '\'' +
                ", measurementUnitForShopList='" + measurementUnitForShopList + '\'' +
                ", checked=" + checked +
                '}';
    }
}
