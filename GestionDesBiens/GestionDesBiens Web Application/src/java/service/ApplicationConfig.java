/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author guitta
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.CenterFacadeREST.class);
        resources.add(service.GroupeFacadeREST.class);
        resources.add(service.ItemFacadeREST.class);
        resources.add(service.LocationFacadeREST.class);
        resources.add(service.PersonnelFacadeREST.class);
        resources.add(service.SalleFacadeREST.class);
        resources.add(service.TransactionFacadeREST.class);
        resources.add(service.TransportFacadeREST.class);
        resources.add(service.TypeFacadeREST.class);
        resources.add(service.UsersFacadeREST.class);
    }
    
}
