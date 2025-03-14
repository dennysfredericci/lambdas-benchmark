package com.devoctans.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

public class CreatePersonLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    private final DynamoDbClient dynamoDbClient;
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Inject
    public CreatePersonLambda(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }
    
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        
        Person person = getPerson(request);
        
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("people")
                .item(map(person))
                .build();

        dynamoDbClient.putItem(putItemRequest);
        
        context.getLogger().log("Person saved to DynamoDB " + request.getBody());
        
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody("{ \"status\": \"OK\" \"message\": \"Person saved to DynamoDB\" }");
        response.setStatusCode(200);
        return response;
    }
    
    private static Map<String, AttributeValue> map(Person person) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(person.id()).build());
        item.put("name", AttributeValue.builder().s(person.name()).build());
        item.put("age", AttributeValue.builder().n(Integer.toString(person.age())).build());
        return item;
    }
    
    private static Person getPerson(APIGatewayProxyRequestEvent request) {
        Person person = null;
        try {
            person = objectMapper.readValue(request.getBody(), Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
