package com.sling.api.builder.samples.restfields;

import com.sling.api.builder.core.annotations.RestField;
import com.sling.api.builder.core.restfields.AbstractResFieldCore;
import com.sling.api.builder.samples.models.ProductModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Component(immediate = true, label = "Second Test Rest Field Core", metatype = true)
@Service(ProductRestField.class)
@RestField(servletExtension = "products", modelClass = ProductModel.class, pathToResources = "/content/api-builder-demo-store/products", searchPropertyValue = "product")
public class ProductRestField extends AbstractResFieldCore {
}
