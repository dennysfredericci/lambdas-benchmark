//package com.devoctans.lambda;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.function.context.FunctionCatalog;
//
//import java.util.function.Function;
//
//@SpringBootTest
//public class WarmupTest {
//
//    @Autowired
//    private FunctionCatalog functionCatalog;
//
//    @Test
//    void test() {
//
//        // Find a function by name
//        Function<Person, String> function = functionCatalog.lookup(Function.class, "CreatePersonFunction");
//        function.apply(new Person("123", "John Doe", 30));
//
//
//    }
//
//}
