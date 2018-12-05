package com.sling.api.builder.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sling.api.builder.core.beans.ServletProperties;
import com.sling.api.builder.core.utils.RestResourceUtil;
import com.sling.api.builder.core.utils.ServletMappingStorage;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BaseRestModel {

    @Self
    @JsonIgnore
    private Resource resource;

    @ValueMapValue(name = RestResourceUtil.REQUEST_PARAMETER_RESOURCE_ID)
    private String id;

    private String restFieldType;

    @PostConstruct
    public void init() {
        restFieldType = Optional.of(ServletMappingStorage.getServletsStorage())
                .map(Map::entrySet)
                .map(Collection::stream)
                .orElse(Stream.empty())
                .map(Map.Entry::getValue)
                .filter(servletProperties -> servletProperties.getModelClass().equals(this.getClass()))
                .map(ServletProperties::getSearchPropertyValue)
                .findFirst().orElse(StringUtils.EMPTY);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getRestFieldType() {
        return restFieldType;
    }

    public void setRestFieldType(String restFieldType) {
        this.restFieldType = restFieldType;
    }
}
