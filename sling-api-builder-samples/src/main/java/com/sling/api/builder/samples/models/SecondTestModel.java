package com.sling.api.builder.samples.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SecondTestModel {

    @ValueMapValue
    private String pathToProduct;

    @ValueMapValue
    private Long amountOfProducts;

    public String getPathToProduct() {
        return pathToProduct;
    }

    public void setPathToProduct(String pathToProduct) {
        this.pathToProduct = pathToProduct;
    }

    public Long getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(Long amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }
}
