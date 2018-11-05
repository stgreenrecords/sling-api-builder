package com.sling.api.builder.samples.restfields;


import com.sling.api.builder.samples.annotations.RestField;
import com.sling.api.builder.samples.models.TestModel;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;


@Component(immediate = true, label = "Test Rest Field Core", metatype = true)
@Service(TestRestField.class)
@RestField(servletExtension = "test", modelClass = TestModel.class, pathToResources = "/api-builder-demo", customPropertyName = "testProperty", customPropertyValue = "testValue")
public class TestRestField extends AbstractResFieldCore {

    @Override
    @Modified
    @Activate
    protected void activate(ComponentContext componentContext) {
        super.activate(componentContext);
    }

}
