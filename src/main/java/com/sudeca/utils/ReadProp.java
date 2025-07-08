package com.sudeca.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * * Author: Francisco Hernandez
 **/
@Component
public class ReadProp {
    public static Properties getProperties(){
        Resource resource = new ClassPathResource("/application.properties");
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
