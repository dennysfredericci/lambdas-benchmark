package com.devoctans.lambda;

import jakarta.annotation.PostConstruct;
import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class SnapStartHandler implements Resource {
    
    private final FunctionCatalog functionCatalog;
    
    public SnapStartHandler(FunctionCatalog functionCatalog) {
        this.functionCatalog = functionCatalog;
    }
    
    @Override
    public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
        System.out.println("Saving application context");
        
        System.out.println("CATALOG: " + functionCatalog);
        Function<Person, String> function = functionCatalog.lookup(Function.class, "CreatePersonFunction");
        function.apply(new Person("123", "John Doe", 30));
        
        System.out.println("Saving application context");
    }
    
    @Override
    public void afterRestore(Context<? extends Resource> context) throws Exception {
        System.out.println("Restoring application context");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Registering SnapStartHandler with CRaC");
        Core.getGlobalContext().register(this);
        System.out.println("Registered SnapStartHandler with CRaC");
    }
}
