package com.sling.api.builder.core.resolver;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@Component(
        label = "Resolver Provider",
        immediate = true,
        metatype = true
)
@Service(ResolverProvider.class)
public final class ResolverProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ResolverProvider.class);
    @Property
    private static final String SERVICE = "provider.resolver.SERVICE";
    private static ResourceResolver resolver;
    @Reference
    private ResourceResolverFactory rrf;

    public static ResourceResolver getResolver() {
        return resolver;
    }

    @Activate
    @Modified
    public void init(Map<String, Object> properties) {
        final String serviceUser = PropertiesUtil.toString(properties.get(SERVICE), StringUtils.EMPTY);
        try {
            resolver = rrf.getServiceResourceResolver(Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, serviceUser));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
