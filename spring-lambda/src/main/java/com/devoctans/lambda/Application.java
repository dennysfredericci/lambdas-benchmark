package com.devoctans.lambda;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    
    /*
     * You need this main method (empty) or explicit <start-class>com.devoctans.lambda.Application</start-class>
     * in the POM to ensure boot plug-in makes the correct entry
     *
     * Main method must be empty unless using Custom runtime at which point it should include.
     *
     * In this case is not empty to make it easier to run the application from the IDE and test the function
     */
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
    }
    
}
