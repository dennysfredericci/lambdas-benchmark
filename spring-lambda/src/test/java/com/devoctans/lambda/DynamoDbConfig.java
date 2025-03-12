//package com.devoctans.lambda;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
//
//import java.net.URI;
//
//@Configuration
//public class DynamoDbConfig {
//
//    @Bean
//    @Primary
//    public DynamoDbClient testDynamoDbClient() {
//        return DynamoDbClient.builder()
//                .endpointOverride(URI.create("http://localhost:8000"))
//                .region(Region.EU_WEST_1)
//                .build();
//    }
//
//    @Bean
//    public DynamoDbServer dynamoDbServer() {
//        return new DynamoDbServer();
//    }
//
//}
