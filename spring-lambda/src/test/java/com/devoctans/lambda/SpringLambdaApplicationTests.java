package com.devoctans.lambda;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringLambdaApplicationTests {

    @Autowired
    private CreatePersonFunction createPersonFunction;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbServer server;


    @Test
    void shouldCreatePerson() {
        String result = createPersonFunction.apply(new Person("1", "John", 30));

        Map<String, AttributeValue> keyCondition = new HashMap<>();
        keyCondition.put(":id", AttributeValue.builder().s("1").build());

        QueryRequest queryRequest = QueryRequest.builder()
                .tableName("people")
                .keyConditionExpression("id = :id")
                .expressionAttributeValues(keyCondition)
                .build();

        QueryResponse queryResponse = dynamoDbClient.query(queryRequest);

        assertThat(queryResponse.items()).hasSize(1);
        assertThat(queryResponse.items().getFirst().get("name").s()).isEqualTo("John");
        assertThat(queryResponse.items().getFirst().get("age").n()).isEqualTo("30");
        assertThat(result).isEqualTo("OK");
    }

    @BeforeEach
    void setUp() throws Exception {
        server.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
    }

}
