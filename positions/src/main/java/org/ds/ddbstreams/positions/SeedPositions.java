package org.ds.ddbstreams.positions;


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Map;

public class SeedPositions
{
    static String[] symbols = {
            "DWAC",
            "SNDL",
            "CEI",
            "FAMI",
            "KMDN"
    };

    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );

        Region region = Region.US_WEST_2;
        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .region(region)
                .build();

        dynamoDBClient.listTables().tableNames().forEach(t -> System.out.println(t));

        //Add 1000000 accounts with positions
        for(int i=0; i < 1000000;i++) {

            String account = String.format("x%d",i);
            int holdings = (int) (Math.random() * 5) + 1;
            for(int j=0; j< holdings;j++) {


                String shares = String.format("%d", (int) (Math.random() * 500) + 1);

                PutItemResponse pir = dynamoDBClient.putItem(
                        PutItemRequest.builder()
                                .item(
                                        Map.of("symbol", AttributeValue.builder().s(symbols[j]).build(),
                                                "account-id", AttributeValue.builder().s(account).build(),
                                                "quantity", AttributeValue.builder().n(shares).build()
                                        )
                                )
                                .tableName("positions")
                                .build()
                );

                Thread.sleep(50);
            }
            System.out.println(String.format("create %d holdings for %s account", holdings, account));
        }

    }
}
