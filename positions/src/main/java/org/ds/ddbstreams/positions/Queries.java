package org.ds.ddbstreams.positions;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;


public class Queries {

    static String[] symbols = {
            "DWAC",
            "SNDL",
            "CEI",
            "FAMI",
            "KMDN"
    };

    public static void main(String... args) {

        Region region = Region.US_WEST_2;
        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .region(region)
                .build();

        for(int i = 0; i < symbols.length; i++) {

            QueryRequest queryRequest = QueryRequest.builder()
                    .limit(1000000)
                    .indexName("position-by-symbol")
                    .tableName("positions")
                    .keyConditions(Map.of("symbol", Condition.builder().comparisonOperator(
                            ComparisonOperator.EQ
                    )
                            .attributeValueList(AttributeValue.builder().s(symbols[i]).build())
                            .build()))
                    .select(Select.SPECIFIC_ATTRIBUTES).attributesToGet("account-id","quantity")
                    .build();
            QueryResponse qr = dynamoDBClient.query(queryRequest);
            System.out.println(String.format("counted %d positions for %s", qr.count(), symbols[i]));
            qr.items().forEach(item -> {
                System.out.println(String.format("account %s has %s shares",item.get("account-id").s(),item.get("quantity").n()));
            });
        }
    }
}
