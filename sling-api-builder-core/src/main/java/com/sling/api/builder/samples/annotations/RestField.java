package com.sling.api.builder.samples.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RestField {

    String servletExtension();
    Class modelClass();
    String pathToResources();
    String jcrPrimaryType() default "nt:unstructured";
    String customPropertyName() default "";
    String customPropertyValue() default "";

}
