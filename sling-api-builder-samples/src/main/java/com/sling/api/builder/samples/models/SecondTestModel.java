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
    private String color;

    public String getPathToProduct() {
        return pathToProduct;
    }

    public void setPathToProduct(String pathToProduct) {
        this.pathToProduct = pathToProduct;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
