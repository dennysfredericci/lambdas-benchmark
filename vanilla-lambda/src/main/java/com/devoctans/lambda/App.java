package com.devoctans.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Lambda function entry point. You can change to use other pojo type or implement
 * a different RequestHandler.
 *
 * @see <a href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda Java Handler</a> for more information
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamoDbClient dynamoDbClient;
    private final ObjectMapper objectMapper;
    
    public App() {
        // Initialize the SDK client outside of the handler method so that it can be reused for subsequent invocations.
        // It is initialized when the class is loaded.
        dynamoDbClient = DependencyFactory.dynamoDbClient();
        objectMapper =  new ObjectMapper();
        // Consider invoking a simple api here to pre-warm up the application, eg: dynamodb#listTables
    }
    
    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent request, final Context context) {
        
        Person person = getPerson(request);
        
        PutItemRequest requestItem = PutItemRequest.builder()
                .tableName("people")
                .item(map(person))
                .build();
        
        dynamoDbClient.putItem(requestItem);
        
        
        context.getLogger().log("Person saved to DynamoDB: " + person);
        
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody("{ \"status\": \"OK\" \"message\": \"Person saved to DynamoDB\" }");
        response.setStatusCode(200);
        return response;
    }
    
    
    private static Map<String, AttributeValue> map(Person person) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(person.getId()).build());
        item.put("name", AttributeValue.builder().s(person.getName()).build());
        item.put("age", AttributeValue.builder().n(Integer.toString(person.getAge())).build());
        return item;
    }
    
    private Person getPerson(APIGatewayProxyRequestEvent request) {
        Person person = null;
        try {
            person = objectMapper.readValue(request.getBody(), Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
