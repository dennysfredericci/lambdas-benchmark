package com.devoctans.lambda;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class CreatePersonFunction implements Function<Person, String> {
    
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CreatePersonFunction.class);
    
    private final DynamoDbClient dynamoDbClient;
    
    public CreatePersonFunction(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }
    
    @Override
    public String apply(Person person) {
        
        PutItemRequest request = PutItemRequest.builder()
                .tableName("people")
                .item(map(person))
                .build();
        
        dynamoDbClient.putItem(request);
        
        LOGGER.info("Person saved to DynamoDB {}", person);
        
        return "OK";
    }
    
    private static Map<String, AttributeValue> map(Person person) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(person.id()).build());
        item.put("name", AttributeValue.builder().s(person.name()).build());
        item.put("age", AttributeValue.builder().n(Integer.toString(person.age())).build());
        return item;
    }
}

