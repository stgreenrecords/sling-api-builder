package com.sling.api.builder.samples.restfields;

import com.sling.api.builder.core.annotations.RestField;
import com.sling.api.builder.core.restfields.AbstractResFieldCore;
import com.sling.api.builder.samples.models.SecondTestModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Component(immediate = true, label = "Second Test Rest Field Core", metatype = true)
@Service(SecondRestField.class)
@RestField(servletExtension = "test2", modelClass = SecondTestModel.class, pathToResources = "/content/api-builder-demo2", customPropertyName = "testProperty2", customPropertyValue = "testValue2")
public class SecondRestField extends AbstractResFieldCore {
}
