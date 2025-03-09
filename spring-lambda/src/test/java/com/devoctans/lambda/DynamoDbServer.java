//package com.devoctans.lambda;
//
//import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
//import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import software.amazon.awssdk.core.waiters.WaiterResponse;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
//import software.amazon.awssdk.services.dynamodb.model.*;
//import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;
//
//@Component
//public class DynamoDbServer {
//
//    private DynamoDBProxyServer server;
//    @Autowired
//    private DynamoDbClient dynamoDbClient;
//
//    public void start() throws Exception {
//        final String[] localArgs = {"-inMemory", "-port", "8000"};
//        server = ServerRunner.createServerFromCommandLineArgs(localArgs);
//        server.start();
//        this.createTable();
//
//    }
//
//    public void stop() throws Exception {
//        server.stop();
//    }
//
//    private void createTable() {
//
//        DynamoDbWaiter dbWaiter = dynamoDbClient.waiter();
//
//        CreateTableRequest createTableRequest = CreateTableRequest.builder()
//                .attributeDefinitions(
//                        AttributeDefinition.builder()
//                                .attributeName("id")
//                                .attributeType(ScalarAttributeType.S)
//                                .build())
//                .keySchema(KeySchemaElement.builder()
//                        .attributeName("id")
//                        .keyType(KeyType.HASH)
//                        .build())
//                .provisionedThroughput(ProvisionedThroughput.builder()
//                        .readCapacityUnits(Long.valueOf(5))
//                        .writeCapacityUnits(Long.valueOf(5))
//                        .build())
//                .tableName("people")
//                .build();
//
//        dynamoDbClient.createTable(createTableRequest);
//        DescribeTableRequest tableRequest = DescribeTableRequest.builder()
//                .tableName("people")
//                .build();
//
//        // Wait until the Amazon DynamoDB table is created
//        WaiterResponse<DescribeTableResponse> waiterResponse = dbWaiter.waitUntilTableExists(tableRequest);
//        waiterResponse.matched().response().orElseThrow(() -> new RuntimeException("Table not created"));
//    }
//
//}
