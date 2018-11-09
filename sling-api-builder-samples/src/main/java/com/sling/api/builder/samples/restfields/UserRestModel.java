package com.sling.api.builder.samples.restfields;


import com.sling.api.builder.core.annotations.RestField;
import com.sling.api.builder.core.restfields.AbstractResFieldCore;
import com.sling.api.builder.samples.models.UserModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;


@Component(immediate = true, label = "Users Rest Field", metatype = true)
@Service(UserRestModel.class)
@RestField(servletExtension = "users", modelClass = UserModel.class, pathToResources = "/content/api-builder-demo-store/users", searchPropertyValue = "user")
public class UserRestModel extends AbstractResFieldCore{


}
