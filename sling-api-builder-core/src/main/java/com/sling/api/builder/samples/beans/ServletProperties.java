package com.sling.api.builder.samples.beans;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ServletProperties {

    private static final String PROPERTY_MODEL = "Model";
    private static final String PROPERTY_SERVICE = "Service";
    private static final String PROPERTY_PATH_TO_RESOURCES = "Path to resource";
    private static final String PROPERTY_JCR_PRIMARY_TYPE = "Jcr primary type";
    private static final String PROPERTY_CUSTOM_PROPERTY_NAME = "Custom property name";
    private static final String PROPERTY_CUSTOM_PROPERTY_Value = "Custom property value";

    private Class<?> modelClass;
    private ServiceReference serviceClass;
    private String pathToResources;
    private String jcrPrimaryType;
    private String customPropertyName;
    private String customPropertyValue;
    private BundleContext bundleContext;

    public ServletProperties(Class<?> modelClass, ServiceReference serviceClass, String pathToResources, String jcrPrimaryType, String customPropertyName, String customPropertyValue, BundleContext bundleContext) {
        this.modelClass = modelClass;
        this.serviceClass = serviceClass;
        this.pathToResources = pathToResources;
        this.jcrPrimaryType = jcrPrimaryType;
        this.customPropertyName = customPropertyName;
        this.customPropertyValue = customPropertyValue;
        this.bundleContext = bundleContext;
    }

    public Class<?> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    public ServiceReference getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceReference serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getPathToResources() {
        return pathToResources;
    }

    public void setPathToResources(String pathToResources) {
        this.pathToResources = pathToResources;
    }

    public String getJcrPrimaryType() {
        return jcrPrimaryType;
    }

    public void setJcrPrimaryType(String jcrPrimaryType) {
        this.jcrPrimaryType = jcrPrimaryType;
    }

    public String getCustomPropertyName() {
        return customPropertyName;
    }

    public void setCustomPropertyName(String customPropertyName) {
        this.customPropertyName = customPropertyName;
    }

    public String getCustomPropertyValue() {
        return customPropertyValue;
    }

    public void setCustomPropertyValue(String customPropertyValue) {
        this.customPropertyValue = customPropertyValue;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
}
