package com.mycompany.rest_api_v2;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author kingl
 */
@javax.ws.rs.ApplicationPath("medical")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.rest_api_v2.healthAPI.class);
    }

}
