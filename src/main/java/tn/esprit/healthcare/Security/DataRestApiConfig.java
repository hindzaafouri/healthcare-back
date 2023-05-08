package tn.esprit.healthcare.Security;

import org.springframework.context.annotation.Configuration;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import tn.esprit.healthcare.Entities.Role;
import tn.esprit.healthcare.Entities.User;

@Configuration
public class DataRestApiConfig implements RepositoryRestConfigurer {

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        HttpMethod[] preventMethod = {HttpMethod.GET,HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE};

        disableHttpMethod(User.class,config,preventMethod);
        disableHttpMethod(Role.class,config,preventMethod);

    }
    private void disableHttpMethod(Class theClass, RepositoryRestConfiguration config,HttpMethod[] unsupportedMethod){
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedMethod)))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethod));
    }
}