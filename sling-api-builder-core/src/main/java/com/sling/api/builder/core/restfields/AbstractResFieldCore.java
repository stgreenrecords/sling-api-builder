package com.sling.api.builder.core.restfields;

import com.sling.api.builder.core.annotations.RestField;
import com.sling.api.builder.core.beans.ServletProperties;
import com.sling.api.builder.core.utils.RestResourceUtil;
import com.sling.api.builder.core.utils.ServletMappingStorage;
import com.sling.api.builder.core.utils.SlingModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.ComponentContext;

import javax.jcr.query.Query;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(immediate = true, label = "Abstract Res Field Core", metatype = true)
@Service(RestFieldCore.class)
@RestField(servletExtension = "", modelClass = Object.class, pathToResources = "/content")
public abstract class AbstractResFieldCore implements RestFieldCore {

    private static final String DEFAULT_GET_QUERY = "SELECT * FROM [%s] AS resource WHERE ISDESCENDANTNODE([%s]) AND resource.[%s] = '%s'";

    @Modified
    @Activate
    protected void activate(ComponentContext componentContext) {
        Optional.ofNullable(this.getClass().getAnnotation(RestField.class))
                .filter(restField -> StringUtils.isNotEmpty(restField.servletExtension()))
                .ifPresent(restField -> ServletMappingStorage.getServletsStorage().
                        put(restField.servletExtension(),
                                new ServletProperties(
                                        restField.modelClass(),
                                        componentContext.getServiceReference(),
                                        restField.pathToResources(),
                                        restField.jcrPrimaryType(),
                                        restField.customPropertyName(),
                                        restField.customPropertyValue(),
                                        componentContext.getBundleContext()))
                );
    }

    @Override
    public Object getObject(SlingHttpServletRequest request) {
        ServletProperties servletProperties = ServletMappingStorage.getPropertiesFromRequest(request);
        Class<?> modelClass = Optional.ofNullable(servletProperties)
                .map(ServletProperties::getModelClass)
                .orElse(null);
        return Optional.of(request.getResourceResolver())
                .map(resolver -> resolver.findResources(formatDefaultQuery(servletProperties), Query.SQL))
                .map(RestResourceUtil::iteratorToOrderedStream)
                .orElse(Stream.empty())
                .map(resource -> resource.adaptTo(modelClass))
                .filter(Objects::nonNull)
                .limit(getLimit(request))
                .collect(Collectors.toList());
    }

    @Override
    public Object updateObject(SlingHttpServletRequest request) {
        return createOrUpdateModel(request, Optional.ofNullable(ServletMappingStorage.getPropertiesFromRequest(request))
                .map(ServletProperties::getModelClass)
                .orElse(null));
    }

    @Override
    public Object createObject(SlingHttpServletRequest request) {
        return createOrUpdateModel(request, Optional.ofNullable(ServletMappingStorage.getPropertiesFromRequest(request))
                .map(ServletProperties::getModelClass)
                .orElse(null));
    }

    @Override
    public Object deleteObject(SlingHttpServletRequest request) {
        return null;
    }

    private String formatDefaultQuery(ServletProperties servletProperties) {
        return String.format(DEFAULT_GET_QUERY,
                servletProperties.getJcrPrimaryType(),
                servletProperties.getPathToResources(),
                servletProperties.getCustomPropertyName(),
                servletProperties.getCustomPropertyValue());
    }

    public Object createOrUpdateModel(SlingHttpServletRequest request, Class modelClass) {
        final Object model = request.adaptTo(modelClass);
        if (model == null) {
            return null;
        }
        SlingModelUtil.updateModel(model);
        return model;
    }


}
