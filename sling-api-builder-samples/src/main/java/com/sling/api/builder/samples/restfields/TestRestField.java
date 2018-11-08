package com.sling.api.builder.samples.restfields;


import com.sling.api.builder.core.annotations.RestField;
import com.sling.api.builder.core.restfields.AbstractResFieldCore;
import com.sling.api.builder.samples.models.TestModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;


@Component(immediate = true, label = "Test Rest Field Core", metatype = true)
@Service(TestRestField.class)
@RestField(servletExtension = "test", modelClass = TestModel.class, pathToResources = "/content/api-builder-demo", customPropertyName = "testProperty", customPropertyValue = "testValue")
public class TestRestField extends AbstractResFieldCore{


}
