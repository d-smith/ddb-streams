package com.example.myapp;


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Map;

public class App
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        Region region = Region.US_WEST_2;
        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .region(region)
                .build();

        dynamoDBClient.listTables().tableNames().forEach(t -> System.out.println(t));

        PutItemResponse pir = dynamoDBClient.putItem(
                PutItemRequest.builder()
                        .item(
                                Map.of("symbol", AttributeValue.builder().s("AAPL").build(),
                                "account-id", AttributeValue.builder().s("x0001").build(),
                                        "quantity", AttributeValue.builder().n("10").build()
                                )
                        )
                        .tableName("positions")
                        .build()
        );

        System.out.println(pir.toString());
    }
}
