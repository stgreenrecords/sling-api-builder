package com.sling.api.builder.core.adapters;

import com.sling.api.builder.core.beans.ServletProperties;
import com.sling.api.builder.core.utils.ReflectionUtil;
import com.sling.api.builder.core.utils.RestResourceUtil;
import com.sling.api.builder.core.utils.ServletMappingStorage;
import com.sling.api.builder.core.utils.SlingModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.io.IOException;
import java.util.*;

@Component(immediate = true, metatype = true)
@Service
public class ModelAdapter implements AdapterFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ModelAdapter.class);

    @Reference
    private ConfigurationAdmin configAdmin;

    @Activate
    public void activate(ComponentContext componentContext) {
        componentContext.getBundleContext().addBundleListener(addBundleListener(componentContext));

    }

    private BundleListener addBundleListener(ComponentContext componentContext) {
        return bundleEvent -> {
            Bundle bundle = bundleEvent.getBundle();
            String modelPackage = (String) bundle.getHeaders().get("Sling-Model-Packages");
            if (StringUtils.isNotEmpty(modelPackage) && (
                    bundleEvent.getType() == BundleEvent.INSTALLED ||
                            bundleEvent.getType() == BundleEvent.UPDATED ||
                            bundleEvent.getType() == BundleEvent.STARTED ||
                            bundleEvent.getType() == BundleEvent.RESOLVED)
            ) {
                String propertyAdaptables = "adaptables";
                String propertyAdapters = "adapters";
                try {
                    Configuration config = configAdmin.getConfiguration(
                            this.getClass().getName());
                    final Dictionary props = Optional.ofNullable(config.getProperties()).orElse(new Hashtable());

                    final String adaptablesOld = (String) props.get(propertyAdaptables);
                    final String[] adaptersOld = (String[]) props.get(propertyAdapters);
                    final String adaptables = SlingHttpServletRequest.class.getName();
                    final String[] adapters = ReflectionUtil.getAllClassesFromPackage(bundle.getBundleContext().getBundle(), modelPackage);
                    if (adaptablesOld != null && adaptersOld != null && adaptables.equals(adaptablesOld) && Arrays.asList(adapters).containsAll(Arrays.asList(adaptersOld))) {
                        return;
                    }
                    props.put(propertyAdaptables, adaptables);
                    props.put(propertyAdapters, adapters);
                    config.update(props);
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                }
            }
        };
    }

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> aClass) {
        if (adaptable instanceof SlingHttpServletRequest) {
            SlingHttpServletRequest request = (SlingHttpServletRequest) adaptable;
            return adaptRequestToModel(request, aClass);
        }
        return null;
    }

    private <AdapterType> AdapterType adaptRequestToModel(SlingHttpServletRequest request, Class<AdapterType> aClass) {
        final String id = RestResourceUtil.getId(request);
        final String path = request.getParameter("path");
        Resource modelResource = null;
        String pathToResource = Optional.ofNullable(ServletMappingStorage.getPropertiesFromRequest(request))
                .map(ServletProperties::getPathToResources)
                .filter(pathToResources -> StringUtils.isEmpty(path))
                .orElse(path);
        if ("POST".equals(request.getMethod())) {
            modelResource = SlingModelUtil.createModelResource(request, pathToResource, aClass);
            updateNewResource(modelResource);
        } else if (StringUtils.isNotEmpty(id)) {
            modelResource = RestResourceUtil.getResourceByID(request.getResourceResolver()).apply(id);
        }
        return (AdapterType) Optional.ofNullable(modelResource)
                .map(resource -> resource.adaptTo(aClass))
                .map(model -> setPropertyToModel(request, model))
                .orElse(null);
    }

    private void updateNewResource(Resource modelResource) {
        if (modelResource == null) {
            LOG.warn("Model resource is null");
            return;
        }
        final Node resourceNode = modelResource.adaptTo(Node.class);
        final ModifiableValueMap properties = modelResource.adaptTo(ModifiableValueMap.class);
        if (resourceNode == null || properties == null) {
            LOG.warn("Model node [] or properties [] is null", resourceNode, properties);
            return;
        }
        final String id = RestResourceUtil.generateId();
        properties.put(RestResourceUtil.REQUEST_PARAMETER_RESOURCE_ID, id);
        try {
            modelResource.getResourceResolver().commit();
        } catch (PersistenceException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private Object setPropertyToModel(SlingHttpServletRequest request, Object model) {
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            Optional.of((String) parameterNames.nextElement())
                    .ifPresent(parameter -> ReflectionUtil.setFieldValueDeep(parameter, request.getParameterValues(parameter), model));
        }
        return model;
    }

}
