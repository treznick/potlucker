package com.potlucker.properties;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by treznick on 6/25/14.
 */
public class PropertiesLookup {
    Properties prop = new Properties();
    String filename = "config.properties";
    InputStream input = null;

    public PropertiesLookup() {
        try {
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            prop.load(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
